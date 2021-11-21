package octogato.config
package resource

import cats.effect.kernel.Sync
import cats.MonadThrow
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.either.*
import pureconfig.ConfigSource
import octogato.config.AppConfig
import octogato.config.given

import java.lang.Exception
import cats.syntax.flatMap

def makeConfigService[F[_]: Sync: MonadThrow]: ConfigService[F] = new:
  def getConfig: F[AppConfig] =
    Sync[F]
      .delay(ConfigSource.default.load[AppConfig])
      .map(_.leftMap(_.prettyPrint()).leftMap(ConfigError.apply))
      .flatMap(MonadThrow[F].fromEither)
