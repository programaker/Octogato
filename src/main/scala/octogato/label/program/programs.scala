package octogato.label
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
import octogato.common.refineNec
import octogato.common.Token
import octogato.label.LabelColorP
import octogato.common.refineU

def copyIssueLabels[F[_]: LabelService: Monad: Parallel](
  token: Token,
  origin: LabelPath,
  target: LabelPath
): F[List[ValidatedNec[RefinementError, LabelResponse]]] =
  val copyIssueLabel = (label: LabelResponse) =>
    val name = refineNec[NonBlankStringP](label.name)
    val color = refineNec[LabelColorP](label.color)
    val description = refineNec[NonBlankStringP](label.description)
    val createReqFn = CreateLabelRequest.withLabelPath(token, target)
    (name, color, description).mapN(createReqFn).traverse(LabelService[F].createLabel)

  LabelService[F]
    .listRepositoryLabels(ListRepositoryLabelsRequest.make(token, origin))
    .flatMap(_.parTraverse(copyIssueLabel))
