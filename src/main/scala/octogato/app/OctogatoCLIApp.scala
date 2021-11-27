package octogato.app

import cats.effect.ExitCode
import cats.effect.IO
import cats.syntax.apply.*
import cats.syntax.applicative.*
import com.monovore.decline.Opts
import com.monovore.decline.refined.*
import octogato.common.Token
import octogato.label.LabelPath
import octogato.label.LabelProgram
import octogato.log.Log
import com.monovore.decline.effect.CommandIOApp
import octogato.label.LabelService
import octogato.config.ConfigService
import octogato.common.http.HttpClientBackend
import octogato.effect.syntax.*
import cats.effect.kernel.Resource

object OctogatoCLIApp extends CommandIOApp(name = "octogato", header = ""):
  override def main: Opts[IO[ExitCode]] =
    CopyLabelsCmd.opts.map { case CopyLabelsCmd(from, to, optionToken) => copyLabels(from, to, optionToken) }

  def copyLabels(from: LabelPath, to: LabelPath, optionToken: Option[Token]): IO[ExitCode] =
    val xxx = HttpClientBackend.makeResource[IO].both(Log.makeResource[IO]).use { (httpClient, log) =>
      given HttpClientBackend[IO] = httpClient
      given Log[IO] = log

      for
        appConfig <- ConfigService.make[IO].getConfig

        token = optionToken.getOrElse(appConfig.authorization.token)
        given LabelService[IO] = LabelService.make[IO](appConfig.api)

        res <- LabelProgram.copyLabels[IO](token, from, to)
      yield res
    }

    ???

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
