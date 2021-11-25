package octogato.common

import cats.Show
import eu.timepit.refined.api.Refined

/** Generates an `opaque type` with `apply` and `value` to wrap/unwrap a value in it, in addition to some basic given
  * instances.
  *
  * ==Usage example:==
  * {{{
  * object Name extends Opaque[String]
  * type Name = Name.OpaqueType
  * }}}
  */
trait Opaque[T]:
  opaque type OpaqueType = T
  inline def apply(t: T): OpaqueType = t
  extension (ot: OpaqueType) inline def value: T = ot
  given (using CanEqual[T, T]): CanEqual[OpaqueType, OpaqueType] = CanEqual.derived
  given (using s: Show[T]): Show[OpaqueType] = s
