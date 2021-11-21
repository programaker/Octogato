package octogato.common.httpclient

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

type BackendResource[F[_]] = Resource[F, SttpBackend[F, Any]]
object BackendResource:
  inline def apply[F[_]: BackendResource]: BackendResource[F] = summon

given [F[_]: Async]: BackendResource[F] = AsyncHttpClientCatsBackend.resource[F]()
