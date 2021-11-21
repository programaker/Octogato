package octogato.common

import cats.data.ValidatedNec
import cats.syntax.either.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.api.Validate
import eu.timepit.refined.refineV

/** Refinement function that models Predicate errors as [[octogato.common.RefinementError]] */
def refineE[P]: PartialRefineE[P] = PartialRefineE()
final class PartialRefineE[P]:
  def apply[T](t: T)(using v: Validate[T, P]): Either[RefinementError, Refined[T, P]] =
    refineV[P](t).leftMap(RefinementError.apply)

def refineNec[P]: PartialRefineNec[P] = PartialRefineNec()
final class PartialRefineNec[P]:
  def apply[T](t: T)(using v: Validate[T, P]): ValidatedNec[RefinementError, Refined[T, P]] =
    refineE[P](t).toValidatedNec

/** Unsafe refinement function; throws [[java.lang.IllegalArgumentException]] when Predicate fails */
def refineU[P]: PartialRefineU[P] = PartialRefineU()
final class PartialRefineU[P]:
  def apply[T](t: T)(using v: Validate[T, P]): Refined[T, P] =
    refineV[P].unsafeFrom(t)
