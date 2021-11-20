package octogato.common.httpclient

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

given [F[_]: Async]: Resource[F, SttpBackend[F, Any]] = AsyncHttpClientCatsBackend.resource[F]()
