package octogato.issues
package program

import cats.Applicative
import cats.Monad
import cats.Parallel
import cats.data.ValidatedNec
import cats.syntax.apply.*
import cats.syntax.either.*
import cats.syntax.flatMap.*
import cats.syntax.parallel.*
import cats.syntax.traverse.*
import octogato.common.NonBlankStringP
import octogato.common.RefinementError
import octogato.common.refineE
import octogato.issues.LabelColorP

def copyIssueLabels[F[_]: IssuesService: Monad: Parallel](
  origin: LabelPath,
  target: LabelPath
): F[List[ValidatedNec[RefinementError, LabelResponse]]] =
  val copyIssueLabel = (label: LabelResponse) =>
    val name = refineE[NonBlankStringP](label.name).toValidatedNec
    val color = refineE[LabelColorP](label.color).toValidatedNec
    val description = refineE[NonBlankStringP](label.description).toValidatedNec
    val createReqFn = CreateLabelRequest.withLabelPath(target)
    (name, color, description).mapN(createReqFn).traverse(IssuesService[F].createLabel)

  IssuesService[F]
    .listRepositoryLabels(ListRepositoryLabelsRequest.make(origin))
    .flatMap(_.parTraverse(copyIssueLabel))
