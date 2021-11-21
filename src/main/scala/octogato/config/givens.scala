package octogato.config

import cats.syntax.either.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.api.Validate
import octogato.common.refineE
import pureconfig.ConfigReader
import pureconfig.error.ExceptionThrown
import pureconfig.error.FailureReason
import pureconfig.generic.derivation.default.*

given [T, P](using cr: ConfigReader[T], v: Validate[T, P]): ConfigReader[Refined[T, P]] =
  cr.emap(refineE[P](_).leftMap(ExceptionThrown.apply))

given ConfigReader[AppConfig] = ConfigReader.derived
