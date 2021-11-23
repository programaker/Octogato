package octogato.config

import cats.syntax.either.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.api.Validate
import octogato.common.syntax.*
import pureconfig.ConfigReader
import pureconfig.error.ExceptionThrown

given [T, P](using cr: ConfigReader[T], v: Validate[T, P]): ConfigReader[Refined[T, P]] =
  cr.emap(_.refineE[P].leftMap(ExceptionThrown.apply))
