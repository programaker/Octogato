package octogato.common.httpclient

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

type Backend[F[_]] = SttpBackend[F, Any]
object Backend:
  inline def apply[F[_]: Backend]: Backend[F] = summon

type BackendResource[F[_]] = Resource[F, Backend[F]]

def makeBackendResource[F[_]: Async]: BackendResource[F] =
  AsyncHttpClientCatsBackend.resource[F]()
