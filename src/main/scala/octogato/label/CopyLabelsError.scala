package octogato.label

import cats.Show

case class CopyLabelsError(error: Throwable, from: LabelPath, to: LabelPath) extends Exception(error)
object CopyLabelsError:
  given Show[CopyLabelsError] = Show.show { e =>
    s"Error copying labels from ${e.from} to ${e.to}: ${e.getMessage}"
  }
