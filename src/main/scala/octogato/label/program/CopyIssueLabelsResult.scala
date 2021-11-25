package octogato.label.program

import cats.Show
import cats.syntax.foldable.*
import cats.syntax.show.*
import octogato.label.LabelName
import octogato.label.LabelPath

case class CopyIssueLabelsResult(
  copiedLabels: List[LabelName],
  from: LabelPath,
  to: LabelPath
)
object CopyIssueLabelsResult:
  given (using Show[LabelPath], Show[LabelName]): Show[CopyIssueLabelsResult] =
    Show.show { clr =>
      show"""${clr.copiedLabels.size} labels copied from '${clr.from}' to '${clr.to}':
            |${clr.copiedLabels.mkString_(" - ", "\n - ", "")}""".stripMargin
    }
