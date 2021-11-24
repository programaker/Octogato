package octogato.common

import cats.Show
import eu.timepit.refined.api.Refined

trait Opaque[T]:
  opaque type OpaqueType = T
  inline def apply(t: T): OpaqueType = t
  extension (ot: OpaqueType) inline def value: T = ot
  given (using s: Show[T]): Show[OpaqueType] = s
