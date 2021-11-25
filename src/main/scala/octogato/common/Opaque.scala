package octogato.common

import cats.Show
import eu.timepit.refined.api.Refined
import pureconfig.ConfigReader
import io.circe.Encoder
import io.circe.Decoder

/** Generates an `opaque type` with `apply` and `value` to wrap/unwrap a value in it, in addition to some basic given
  * instances.
  *
  * Usage example:
  * {{{
  * object Name extends Opaque[String]
  * type Name = Name.OpaqueType
  * }}}
  */
transparent trait Opaque[T]:
  opaque type OpaqueType = T

  inline def apply(t: T): OpaqueType = t
  def unapply(ot: OpaqueType): Some[T] = Some(ot)
  extension (ot: OpaqueType) inline def value: T = ot

  given (using CanEqual[T, T]): CanEqual[OpaqueType, OpaqueType] = summon
  given (using Show[T]): Show[OpaqueType] = summon
  given (using ConfigReader[T]): ConfigReader[OpaqueType] = summon
  given (using Encoder[T]): Encoder[OpaqueType] = summon
  given (using Decoder[T]): Decoder[OpaqueType] = summon
