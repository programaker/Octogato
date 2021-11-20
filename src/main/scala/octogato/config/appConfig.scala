package octogato.config

import octogato.common.UriString
import octogato.common.Token

case class AppConfig(
  apiUri: UriString,
  authorization: AuthorizationConfig
)

case class AuthorizationConfig(
  token: Token
)
