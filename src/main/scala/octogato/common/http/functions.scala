package octogato.common.http

import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.MonadThrow
import io.circe.Decoder
import sttp.client3.IsOption
import sttp.client3.*
import sttp.client3.circe.*
import octogato.common.Token
import octogato.common.Accept
import sttp.model.Header

extension [A](req: Request[A, Any])
  def send[F[_]: MonadThrow: HttpClientBackend, B: Decoder: IsOption](
    token: Token,
    accept: Accept
  ): F[B] =
    req
      .auth
      .bearer(token.value)
      .header(Header.accept(accept.value))
      .response(asJson[B])
      .send(HttpClientBackend[F])
      .map(_.body)
      .flatMap {
        case Left(err)  => MonadThrow[F].raiseError(err)
        case Right(res) => MonadThrow[F].pure(res)
      }
