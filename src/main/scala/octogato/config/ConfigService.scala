package octogato.config

trait ConfigService[F[_]]:
  def getConfig: F[AppConfig]
object ConfigService:
  inline def apply[F[_]: ConfigService]: ConfigService[F] = summon
