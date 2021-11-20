package octogato.common

import cats.Show
import cats.syntax.show.*
import eu.timepit.refined.api.Refined

given [T: Show, P]: Show[Refined[T, P]] = Show.show(_.value.show)
