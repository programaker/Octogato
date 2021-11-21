package octogato.config

import octogato.common.UriString
import octogato.common.Token

case class AppConfig(
  api: ApiConfig,
  authorization: AuthorizationConfig
)

case class ApiConfig(
  apiHost: UriString
)

case class AuthorizationConfig(
  token: Token
)
