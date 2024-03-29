package octogato.common.http

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import sttp.client3.SttpBackend
import sttp.client3.armeria.cats.ArmeriaCatsBackend

type HttpClientBackend[F[_]] = SttpBackend[F, Any]

object HttpClientBackend:
  inline def apply[F[_]: HttpClientBackend]: HttpClientBackend[F] = summon

  def resource[F[_]: Async]: Resource[F, HttpClientBackend[F]] =
    ArmeriaCatsBackend.resource[F]()
