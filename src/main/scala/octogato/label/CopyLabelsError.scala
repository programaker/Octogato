package octogato.label

import cats.Show
import cats.syntax.show.*

case class CopyLabelsError(error: Throwable, from: LabelPath, to: LabelPath) extends Exception(error)
object CopyLabelsError:
  given (using Show[LabelPath]): Show[CopyLabelsError] = Show.show { e =>
    show"Error copying labels from ${e.from} to ${e.to}: ${e.getMessage}"
  }
