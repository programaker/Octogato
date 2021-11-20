package octogato.common.httpclient

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

type BackendResource[F[_]] = Resource[F, SttpBackend[F, Any]]
given [F[_]: Async]: BackendResource[F] = AsyncHttpClientCatsBackend.resource[F]()
