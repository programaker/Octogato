package octogato.config

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

trait ConfigService[F[_]]:
  def getConfig: F[AppConfig]

object ConfigService:
  inline def apply[F[_]: ConfigService]: ConfigService[F] = summon

  def make[F[_]: Sync: MonadThrow]: ConfigService[F] = new:
    override def getConfig: F[AppConfig] =
      Sync[F]
        .delay(ConfigSource.default.load[AppConfig])
        .map(_.leftMap(_.prettyPrint()).leftMap(ConfigError.apply))
        .flatMap(MonadThrow[F].fromEither)
