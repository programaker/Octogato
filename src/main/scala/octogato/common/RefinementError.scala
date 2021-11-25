package octogato.common

import cats.data.NonEmptyChain
import cats.syntax.foldable.*
import cats.Foldable
import cats.Show

case class RefinementError(error: String) extends Exception(error)
object RefinementError:
  given Show[RefinementError] = Show.show(_.error)

case class RefinementErrors[C[_]: Foldable](errors: C[RefinementError])
    extends Exception(errors.mkString_("Refinement errors: \n - ", "\n - ", ""))
object RefinementErrors:
  def nec(errors: NonEmptyChain[RefinementError]): RefinementErrors[NonEmptyChain] =
    RefinementErrors(errors)
