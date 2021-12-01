package octogato.app

import cats.syntax.apply.*
import cats.syntax.functor.*
import cats.syntax.traverse.*
import cats.syntax.bifunctor.*
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.std.Console
import com.monovore.decline.Opts
import octogato.common.Token
import octogato.common.ApiHost
import octogato.common.TokenError
import octogato.common.given
import octogato.label.LabelPath
import octogato.label.LabelProgram
import octogato.log.Log
import com.monovore.decline.effect.CommandIOApp
import octogato.label.LabelService
import octogato.config.ConfigService
import octogato.common.http.HttpClientBackend
import cats.effect.kernel.Resource
import octogato.label.CopyLabelsResult
import octogato.label.CopyLabelsError
import cats.Show
import cats.Functor

object OctogatoCLIApp extends CommandIOApp(name = "octogato", header = ""):
  override def main: Opts[IO[ExitCode]] =
    CopyLabelsCommand.opts.map { command =>
      (HttpClientBackend.resource[IO], Log.resource[IO])
        .tupled
        .use { (httpClient, log) =>
          given HttpClientBackend[IO] = httpClient
          given Log[IO] = log

          for
            appConfig <- ConfigService.make[IO].getConfig

            (commandToken, commandFn) = command match
              case CopyLabelsCommand(from, to, optionToken) => (optionToken, copyLabels(from, to))

            result <- chooseToken(commandToken, appConfig.authorization.token)
              .leftMap(CommandError.make(_))
              .flatTraverse(commandFn(appConfig.api.apiHost, _))
              .flatMap(_.fold(showError, showResult))
          yield result
        }
    }

  def chooseToken(commandToken: Option[Token], configToken: Option[Token]): Either[TokenError, Token] =
    commandToken
      .orElse(configToken)
      .toRight(
        TokenError(
          "Github token is missing. Provide it either via --token argument or through the GITHUB_TOKEN env var"
        )
      )

  def showResult[A: Show](a: A): IO[ExitCode] =
    Console[IO].println(a).as(ExitCode.Success)

  def showError[E: Show](e: E): IO[ExitCode] =
    Console[IO].errorln(e).as(ExitCode.Error)

  def copyLabels(from: LabelPath, to: LabelPath)(using
    HttpClientBackend[IO],
    Log[IO]
  ): CommandFn = (apiHost, token) =>
    given LabelService[IO] = LabelService.make[IO](apiHost)
    LabelProgram.copyLabels[IO](token, from, to).map(_.bimap(CommandError.make(_), CommandResult.make(_)))
