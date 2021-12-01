package octogato.config

import octogato.common.ApiHost
import octogato.common.Token
import pureconfig.ConfigReader
import pureconfig.generic.derivation.default.*

case class AppConfig(
  api: ApiConfig,
  authorization: AuthorizationConfig
) derives ConfigReader

case class ApiConfig(
  apiHost: ApiHost
)

case class AuthorizationConfig(
  token: Option[Token]
)
