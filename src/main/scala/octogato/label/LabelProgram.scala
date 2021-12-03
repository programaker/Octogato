package octogato.label

import cats.Applicative
import cats.Monad
import cats.MonadThrow
import cats.Parallel
import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import cats.data.ValidatedNec
import cats.syntax.applicative.*
import cats.syntax.applicativeError.*
import cats.syntax.apply.*
import cats.syntax.either.*
import cats.syntax.flatMap.*
import cats.syntax.foldable.*
import cats.syntax.functor.*
import cats.syntax.parallel.*
import cats.syntax.show.*
import cats.syntax.traverse.*
import cats.syntax.validated.*
import eu.timepit.refined.cats.*
import octogato.common.RefinementError
import octogato.common.RefinementErrors
import octogato.common.Token
import octogato.common.syntax.*
import octogato.log.Log

object LabelProgram:
  def copyLabels[F[_]: LabelService: Log: MonadThrow: Parallel](
    token: Token,
    source: LabelPath,
    target: LabelPath
  ): F[Either[CopyLabelsError, CopyLabelsResult]] =
    val labelService = LabelService[F]
    val log = Log[F]
    val monadThrow = MonadThrow[F]
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
          .flatMap {
            case Valid(resp)     => resp.pure
            case Invalid(errors) => RefinementErrors.nec(errors).raiseError
          }

    val deleteLabelsFromTarget = (_: List[LabelResponse]).parTraverse_(deleteLabelFromTarget)
    val copyLabelsFromSourceToTarget = listLabels(source).flatMap(_.parTraverse(copyLabelToTarget))

    listLabels(target)
      .flatMap(deleteLabelsFromTarget(_) *> copyLabelsFromSourceToTarget)
      .map(_.map(_.name))
      .map(CopyLabelsResult(_, source, target))
      .attempt
      .flatTap {
        case Right(_) => ().pure
        case Left(e)  => log.error(e.getMessage, e)
      }
      .map(_.leftMap(CopyLabelsError(_, source, target)))
