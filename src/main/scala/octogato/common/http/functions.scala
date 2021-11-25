package octogato.common.http

import cats.MonadThrow
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import eu.timepit.refined.auto.autoUnwrap
import io.circe.Decoder
import octogato.common.Accept
import octogato.common.Token
import sttp.client3.IsOption
import sttp.client3.*
import sttp.client3.circe.*
import sttp.model.Header

extension [A](req: Request[A, Any])
  def send[F[_]: MonadThrow: HttpClientBackend, B: Decoder: IsOption](
    token: Token,
    accept: Accept
  ): F[B] =
    val monadThrow = MonadThrow[F]

    req
      .auth
      .bearer(token.value)
      .header(Header.accept(accept.value))
      .response(asJson[B])
      .send(HttpClientBackend[F])
      .map(_.body)
      .flatMap {
        case Left(err)  => monadThrow.raiseError(err)
        case Right(res) => monadThrow.pure(res)
      }
