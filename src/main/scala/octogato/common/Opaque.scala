package octogato.common

import cats.Show
import com.monovore.decline.Argument
import eu.timepit.refined.api.Refined
import io.circe.Decoder
import io.circe.Encoder
import pureconfig.ConfigReader

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
  final opaque type OpaqueType = T

  inline def apply(t: T): OpaqueType = t
  def unapply(ot: OpaqueType): Some[T] = Some(ot)
  inline def wrap[F[_]](ts: F[T]): F[OpaqueType] = ts

  extension (ot: OpaqueType) inline def value: T = ot
  extension [F[_]](ot: F[OpaqueType]) inline def unwrap: F[T] = ot

  given (using CanEqual[T, T]): CanEqual[OpaqueType, OpaqueType] = summon
  given (using Show[T]): Show[OpaqueType] = summon
  given (using ConfigReader[T]): ConfigReader[OpaqueType] = summon
  given (using Encoder[T]): Encoder[OpaqueType] = summon
  given (using Decoder[T]): Decoder[OpaqueType] = summon
  given (using Argument[T]): Argument[OpaqueType] = summon
