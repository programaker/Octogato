package octogato.app

import cats.syntax.apply.*
import octogato.common.Token
import octogato.label.LabelPath
import com.monovore.decline.Opts
import com.monovore.decline.refined.*

// $ octogato copy-labels --from 'programaker/Joguin2' --to 'programaker/Spotification' --token <optional>
case class CopyLabelsCommand(from: LabelPath, to: LabelPath, token: Option[Token])
object CopyLabelsCommand:
  val opts: Opts[CopyLabelsCommand] =
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
      ).mapN(CopyLabelsCommand.apply)
    }
