package octogato.label

import cats.Monad
import cats.MonadThrow
import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.show.*
import octogato.common.given
import octogato.common.httpclient.Backend
import octogato.label.LabelService
import octogato.label.json.given
import sttp.client3.*
import sttp.client3.circe.*
import sttp.model.Header
import octogato.config.ApiConfig
import sttp.model.Uri
import io.circe.syntax.*
import octogato.label.json.given

trait LabelService[F[_]]:
  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]]
  def createLabel(req: CreateLabelRequest): F[LabelResponse]

object LabelService:
  inline def apply[F[_]: LabelService]: LabelService[F] = summon

  def make[F[_]: Async: MonadThrow: Backend](apiConfig: ApiConfig): LabelService[F] = new:
    val baseUri = show"${apiConfig.apiHost}/repos"

    override def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]] =
      val getUri =
        labelsUri(req.owner, req.repo)
          .addParam("per_page", req.per_page.map(_.show))
          .addParam("page", req.page.map(_.show))

      basicRequest
        .get(getUri)
        .auth
        .bearer(req.token.value)
        .header(Header.accept(req.accept.value))
        .response(asJson[List[LabelResponse]])
        .send(Backend[F])
        .map(_.body)
        .flatMap {
          case Left(err)  => MonadThrow[F].raiseError(err)
          case Right(res) => MonadThrow[F].pure(res)
        }

    override def createLabel(req: CreateLabelRequest): F[LabelResponse] =
      val postUri =
        labelsUri(req.owner, req.repo)

      basicRequest
        .post(postUri)
        .body(req.body.asJson)
        .auth
        .bearer(req.token.value)
        .header(Header.accept(req.accept.value))
        .response(asJson[LabelResponse])
        .send(Backend[F])
        .map(_.body)
        .flatMap {
          case Left(err)  => MonadThrow[F].raiseError(err)
          case Right(res) => MonadThrow[F].pure(res)
        }

    private def labelsUri(owner: Owner, repo: Repo): Uri =
      uri"""${show"$baseUri/$owner/$repo/labels"}"""
