package octogato.label

import octogato.common.NonBlankString
import cats.Show
import cats.syntax.show.*

case class LabelPath(
  owner: Owner,
  repo: Repo
)

object LabelPath:
  given (using Show[Owner], Show[Repo]): Show[LabelPath] =
    Show.show(path => show"${path.owner}/${path.repo}")
