package octogato.common

import cats.data.ValidatedNec
import cats.syntax.either.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.api.Validate
import eu.timepit.refined.refineV as refineV_
import eu.timepit.refined.internal.RefinePartiallyApplied

object syntax:
  extension [T](t: T)
    /** Syntax for the original [[eu.timepit.refined.refineV]] */
    def refineV[P](using Validate[T, P]): Either[String, Refined[T, P]] =
      refineV_[P](t)

    /** Refinement function that models Predicate errors as [[octogato.common.RefinementError]] */
    def refineE[P](using Validate[T, P]): Either[RefinementError, Refined[T, P]] =
      refineV_[P](t).leftMap(RefinementError.apply)

    /** Refinement function that returns [[cats.data.ValidatedNec]] */
    def refineNec[P](using Validate[T, P]): ValidatedNec[RefinementError, Refined[T, P]] =
      t.refineE[P].toValidatedNec

    /** Unsafe refinement function; throws [[java.lang.IllegalArgumentException]] when Predicate fails */
    def refineU[P](using Validate[T, P]): Refined[T, P] =
      refineV_[P].unsafeFrom(t)
