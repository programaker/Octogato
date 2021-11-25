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
  val validated = [A] => (_: A).validNec[RefinementError]

  val copyIssueLabel = (label: LabelResponse) =>
    (validated(label.name), validated(label.color), validated(label.description))
      .mapN(CreateLabelRequest.withLabelPath(token, target))
      .traverse(LabelService[F].createLabel)

  LabelService[F]
    .listRepositoryLabels(ListRepositoryLabelsRequest.make(token, origin))
    .flatMap(_.parTraverse(copyIssueLabel))
