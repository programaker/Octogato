package octogato.issues
package httpclient

import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import octogato.issues.IssuesService
import octogato.common.httpclient.given
import sttp.client3.SttpBackend
import sttp.client3.basicRequest

given [F[_]: Async, T]: IssuesService[F] with
  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]] = ???

  def createLabel(req: CreateLabelRequest): F[LabelResponse] = ???
