package octogato.label
package httpclient

import cats.Monad
import cats.MonadThrow
import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.show.*
import octogato.common.given_Show_Refined
import octogato.common.httpclient.BackendResource
import octogato.label.LabelService
import octogato.label.json.given_Decoder_LabelResponse
import sttp.client3.*
import sttp.client3.circe.*
import sttp.model.Header

def makeLabelService[F[_]: Async: MonadThrow: BackendResource]: LabelService[F] = new:
  val baseUri = "https://api.github.com/repos"

  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]] =
    val getUri =
      uri"""${show"$baseUri/${req.owner}/${req.repo}/labels"}"""
        .addParam("per_page", req.per_page.map(_.show))
        .addParam("page", req.page.map(_.show))

    val getReq =
      basicRequest
        .auth
        .bearer(req.token.value)
        .header(Header.accept(req.accept.value))
        .get(getUri)
        .response(asJson[List[LabelResponse]])

    BackendResource[F]
      .use(getReq.send)
      .map(_.body)
      .flatMap {
        _ match
          case Left(err)  => MonadThrow[F].raiseError(err)
          case Right(res) => MonadThrow[F].pure(res)
      }

  def createLabel(req: CreateLabelRequest): F[LabelResponse] =
    ???
