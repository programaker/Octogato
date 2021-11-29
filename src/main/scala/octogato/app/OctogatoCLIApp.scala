package octogato.app

import cats.syntax.apply.*
import cats.syntax.functor.*
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.std.Console
import com.monovore.decline.Opts
import octogato.common.Token
import octogato.common.ApiHost
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
            apiHost = appConfig.api.apiHost
            tokenFn = (optionToken: Option[Token]) => optionToken.getOrElse(appConfig.authorization.token)

            res <- command match
              case CopyLabelsCommand(from, to, optionToken) => copyLabels(from, to, tokenFn(optionToken), apiHost)
          yield res
        }
        .flatMap(_.fold(report(ExitCode.Error), report(ExitCode.Success)))
    }

  def report[A: Show](exitCode: ExitCode)(a: A): IO[ExitCode] =
    Console[IO].println(a).as(exitCode)

  def copyLabels(from: LabelPath, to: LabelPath, token: Token, apiHost: ApiHost)(using
    HttpClientBackend[IO],
    Log[IO]
  ): IO[Either[CopyLabelsError, CopyLabelsResult]] =
    given LabelService[IO] = LabelService.make[IO](apiHost)
    LabelProgram.copyLabels[IO](token, from, to)
