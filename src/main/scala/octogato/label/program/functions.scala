package octogato.label
package program

import cats.Applicative
import cats.Monad
import cats.Parallel
import cats.data.ValidatedNec
import cats.syntax.apply.*
import cats.syntax.validated.*
import cats.syntax.flatMap.*
import cats.syntax.parallel.*
import cats.syntax.traverse.*
import octogato.common.RefinementError
import octogato.common.Token
import octogato.common.syntax.*

def copyIssueLabels[F[_]: LabelService: Monad: Parallel](
  token: Token,
  origin: LabelPath,
  target: LabelPath
): F[List[ValidatedNec[RefinementError, LabelResponse]]] =
  val copyIssueLabel = (label: LabelResponse) =>
    val name = label.name.validNec[RefinementError]
    val color = label.color.validNec[RefinementError]
    val description = label.description.validNec[RefinementError]
    val createReqFn = CreateLabelRequest.withLabelPath(token, target)
    (name, color, description).mapN(createReqFn).traverse(LabelService[F].createLabel)

  LabelService[F]
    .listRepositoryLabels(ListRepositoryLabelsRequest.make(token, origin))
    .flatMap(_.parTraverse(copyIssueLabel))
