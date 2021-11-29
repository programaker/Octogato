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
import octogato.common.ApiHost
import octogato.common.http.syntax.*
import octogato.label.LabelService
import sttp.client3.*
import sttp.client3.circe.*
import sttp.model.Header
import octogato.config.ApiConfig
import sttp.model.Uri
import io.circe.syntax.*
import eu.timepit.refined.auto.autoUnwrap

trait LabelService[F[_]]:
  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]]
  def createLabel(req: CreateLabelRequest): F[LabelResponse]
  def deleteLabel(req: DeleteLabelRequest): F[Unit]

object LabelService:
  inline def apply[F[_]: LabelService]: LabelService[F] = summon

  def make[F[_]: MonadThrow: HttpClientBackend](apiHost: ApiHost): LabelService[F] = new:
    override def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]] =
      val getUri = labelsUri(req.labelPath)
        .addParam("per_page", req.per_page.map(_.show))
        .addParam("page", req.page.map(_.show))

      basicRequest
        .get(getUri)
        .send(req.token, req.accept)

    override def createLabel(req: CreateLabelRequest): F[LabelResponse] =
      val postUri = labelsUri(req.labelPath)

      basicRequest
        .post(postUri)
        .body(req.body.asJson)
        .send(req.token, req.accept)

    override def deleteLabel(req: DeleteLabelRequest): F[Unit] =
      val deleteUri = labelsUri(req.labelPath).addPath(show"${req.name}")

      basicRequest
        .delete(deleteUri)
        .auth
        .bearer(req.token.value)
        .header(Header.accept(req.accept.value))
        .send(HttpClientBackend[F])
        .as(())

    private def labelsUri(labelPath: LabelPath): Uri =
      uri"""${show"$apiHost/repos/$labelPath/labels"}"""
