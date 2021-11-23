package octogato.label

import cats.Monad
import cats.MonadThrow
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.show.*
import octogato.common.given
import octogato.common.http.HttpClientBackend
import octogato.common.Token
import octogato.common.Accept
import octogato.common.http.send
import octogato.label.LabelService
import sttp.client3.*
import sttp.client3.circe.*
import sttp.model.Header
import octogato.config.ApiConfig
import sttp.model.Uri
import io.circe.syntax.*

trait LabelService[F[_]]:
  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]]
  def createLabel(req: CreateLabelRequest): F[LabelResponse]

object LabelService:
  inline def apply[F[_]: LabelService]: LabelService[F] = summon

  def make[F[_]: MonadThrow: HttpClientBackend](apiConfig: ApiConfig): LabelService[F] = new:
    val baseUri = show"${apiConfig.apiHost}/repos"

    override def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]] =
      val getUri = labelsUri(req.owner, req.repo)
        .addParam("per_page", req.per_page.map(_.show))
        .addParam("page", req.page.map(_.show))

      basicRequest
        .get(getUri)
        .send(req.token, req.accept)

    override def createLabel(req: CreateLabelRequest): F[LabelResponse] =
      val postUri = labelsUri(req.owner, req.repo)

      basicRequest
        .post(postUri)
        .body(req.body.asJson)
        .send(req.token, req.accept)

    private def labelsUri(owner: Owner, repo: Repo): Uri =
      uri"""${show"$baseUri/$owner/$repo/labels"}"""
