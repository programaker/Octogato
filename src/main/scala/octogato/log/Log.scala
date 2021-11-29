package octogato.log

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import io.odin.Level
import io.odin.Logger
import io.odin.consoleLogger
import io.odin.formatter.Formatter
import io.odin.syntax.*

type Log[F[_]] = Logger[F]
object Log:
  inline def apply[F[_]: Log]: Log[F] = summon

  def resource[F[_]: Async]: Resource[F, Log[F]] =
    consoleLogger[F](
      formatter = Formatter.colorful,
      minLevel = Level.Info
    ).withAsync()
