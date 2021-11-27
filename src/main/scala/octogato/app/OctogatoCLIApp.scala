package octogato.app

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.std.Console
import cats.syntax.apply.*
import cats.syntax.applicative.*
import cats.syntax.show.*
import cats.syntax.foldable.*
import com.monovore.decline.Opts
import com.monovore.decline.refined.*
import octogato.common.Token
import octogato.common.given
import octogato.label.LabelPath
import octogato.label.LabelProgram
import octogato.log.Log
import com.monovore.decline.effect.CommandIOApp
import octogato.label.LabelService
import octogato.config.ConfigService
import octogato.common.http.HttpClientBackend
import octogato.effect.syntax.*
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

    HttpClientBackend
      .makeResource[IO]
      .both(Log.makeResource[IO])
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

// $ octogato copy-labels --from 'programaker/Joguin2' --to 'programaker/Spotification' --token <optional>
case class CopyLabelsCmd(from: LabelPath, to: LabelPath, token: Option[Token])
object CopyLabelsCmd:
  val opts: Opts[CopyLabelsCmd] =
    Opts.subcommand("copy-labels", "Copy issue labels from one repository to another") {
      (
        Opts.option[LabelPath](
          "from",
          "Origin repository in the form 'owner/repo'",
          metavar = "owner/repo"
        ),
        Opts.option[LabelPath](
          "to",
          "Target repository in the form 'owner/repo'",
          metavar = "owner/repo"
        ),
        Opts
          .option[Token](
            "token",
            "[optional] Github personal token. Can also be provided through a GITHUB_TOKEN env variable",
            metavar = "owner/repo"
          )
          .orNone
      ).mapN(CopyLabelsCmd.apply)
    }
