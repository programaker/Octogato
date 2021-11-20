package octogato.issues
package httpclient

import cats.syntax.show.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import octogato.issues.IssuesService
import octogato.issues.json.given_Decoder_LabelResponse
import octogato.common.httpclient.BackendResource
import octogato.common.httpclient.given_BackendResource_F
import octogato.common.given_Show_Refined
import sttp.client3.*
import sttp.client3.circe.*
import sttp.model.Header
import cats.Monad
import cats.MonadThrow

given [F[_]: Async: MonadThrow]: IssuesService[F] with
  val backend = summon[BackendResource[F]]
  val baseUri = "https://api.github.com/repos"

  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]] =
    val getUri =
      uri"""${show"$baseUri/${req.owner}/${req.repo}/labels"}"""
        .addParam("per_page", req.per_page.map(_.show))
        .addParam("page", req.page.map(_.show))

    val getReq =
      basicRequest.auth
        .bearer(req.token.value)
        .header(Header.accept(req.accept.value))
        .get(getUri)
        .response(asJson[List[LabelResponse]])

    backend
      .use(getReq.send)
      .map(_.body)
      .flatMap {
        _ match
          case Left(err)  => MonadThrow[F].raiseError(err)
          case Right(res) => MonadThrow[F].pure(res)
      }

  def createLabel(req: CreateLabelRequest): F[LabelResponse] =
    ???
