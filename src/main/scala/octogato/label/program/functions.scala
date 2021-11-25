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
  source: LabelPath,
  target: LabelPath
): F[List[ValidatedNec[RefinementError, LabelResponse]]] =
  val labelService = LabelService[F]
  val validated = [A] => (_: A).validNec[RefinementError]
  val listLabels = labelService.listRepositoryLabels.compose(ListRepositoryLabelsRequest.make(token, _))

  val deleteLabelFromTarget = (label: LabelResponse) =>
    labelService.deleteLabel(DeleteLabelRequest.make(token, target, label.name))

  val copyLabelToTarget = (label: LabelResponse) =>
    (validated(label.name), validated(label.color), validated(label.description))
      .mapN(CreateLabelRequest.withLabelPath(token, target))
      .traverse(labelService.createLabel)

  val deleteLabelsFromTarget = (_: List[LabelResponse]).parTraverse(deleteLabelFromTarget)
  val copyLabelsFromSourceToTarget = listLabels(source).flatMap(_.parTraverse(copyLabelToTarget))

  listLabels(target).flatMap(deleteLabelsFromTarget(_) &> copyLabelsFromSourceToTarget)
