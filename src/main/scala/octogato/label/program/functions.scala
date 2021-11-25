package octogato.label
package program

import cats.Applicative
import cats.Monad
import cats.Parallel
import cats.data.ValidatedNec
import cats.syntax.apply.*
import cats.syntax.flatMap.*
import cats.syntax.parallel.*
import cats.syntax.show.*
import cats.syntax.traverse.*
import cats.syntax.validated.*
import octogato.common.RefinementError
import octogato.common.Token
import octogato.common.syntax.*
import octogato.common.given
import octogato.log.Log

def copyIssueLabels[F[_]: LabelService: Monad: Parallel: Log](
  token: Token,
  source: LabelPath,
  target: LabelPath
): F[List[ValidatedNec[RefinementError, LabelResponse]]] =
  val labelService = LabelService[F]
  val log = Log[F]

  val validated = [A] => (_: A).validNec[RefinementError]

  val listLabels = (path: LabelPath) =>
    log.info(show"Listing labels from $path") *>
      labelService.listRepositoryLabels(ListRepositoryLabelsRequest.make(token, path))

  val deleteLabelFromTarget = (label: LabelResponse) =>
    log.info(show"Deleting label '${label.name}' from '$target'") *>
      labelService.deleteLabel(DeleteLabelRequest.make(token, target, label.name))

  val copyLabelToTarget = (label: LabelResponse) =>
    log.info(show"Creating label '${label.name}' in '$target'") *>
      (validated(label.name), validated(label.color), validated(label.description))
        .mapN(CreateLabelRequest.withLabelPath(token, target))
        .traverse(labelService.createLabel)

  val deleteLabelsFromTarget = (_: List[LabelResponse]).parTraverse(deleteLabelFromTarget)
  val copyLabelsFromSourceToTarget = listLabels(source).flatMap(_.parTraverse(copyLabelToTarget))

  listLabels(target).flatMap(deleteLabelsFromTarget(_) &> copyLabelsFromSourceToTarget)
