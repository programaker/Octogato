package octogato.config

import octogato.common.UriString
import octogato.common.Token
import pureconfig.ConfigReader
import pureconfig.generic.derivation.default.*

case class AppConfig(
  api: ApiConfig,
  authorization: AuthorizationConfig
) derives ConfigReader

case class ApiConfig(
  apiHost: UriString
)

case class AuthorizationConfig(
  token: Token
)
