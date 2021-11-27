package octogato.app

import cats.syntax.apply.*
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.std.Console
import com.monovore.decline.Opts
import octogato.common.Token
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

object OctogatoCLIApp extends CommandIOApp(name = "octogato", header = ""):
  override def main: Opts[IO[ExitCode]] =
    CopyLabelsCmd.opts.map { case CopyLabelsCmd(from, to, optionToken) => copyLabels(from, to, optionToken) }

  def copyLabels(from: LabelPath, to: LabelPath, optionToken: Option[Token]): IO[ExitCode] =
    val console = Console.make[IO]
    val reportSuccess = console.println(_: CopyLabelsResult).map(_ => ExitCode.Success)
    val reportError = console.errorln(_: CopyLabelsError).map(_ => ExitCode.Error)

    (HttpClientBackend.makeResource[IO], Log.makeResource[IO])
      .tupled
      .use { (httpClient, log) =>
        given HttpClientBackend[IO] = httpClient
        given Log[IO] = log

        for
          appConfig <- ConfigService.make[IO].getConfig

          token = optionToken.getOrElse(appConfig.authorization.token)
          given LabelService[IO] = LabelService.make[IO](appConfig.api)

          res <- LabelProgram.copyLabels[IO](token, from, to)
        yield res
      }
      .flatMap(_.fold(reportError, reportSuccess))
