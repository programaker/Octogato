package octogato.config

case class ConfigError(message: String) extends Exception(message)
