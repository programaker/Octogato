package octogato.common

import cats.Show

case class TokenError(message: String) extends Exception(message)
object TokenError:
  given Show[TokenError] = Show.show(_.message)
