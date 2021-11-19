package octogato.common

import cats.syntax.either.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.api.Validate
import eu.timepit.refined.refineV

/** Refinement function that models Predicate errors as [[RefinementError]] */
def refineE[P]: PartialRefineE[P] = new PartialRefineE
final class PartialRefineE[P]:
  def apply[T](t: T)(using v: Validate[T, P]): Either[RefinementError, Refined[T, P]] =
    refineV[P](t).leftMap(RefinementError.apply)

/** Unsafe refinement function; throws [[java.lang.IllegalArgumentException]] when Predicate fails */
def refineU[P]: PartialRefineU[P] = new PartialRefineU
final class PartialRefineU[P]:
  def apply[T](t: T)(using v: Validate[T, P]): Refined[T, P] =
    refineV[P].unsafeFrom(t)
