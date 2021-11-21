package octogato.label

trait LabelService[F[_]]:
  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]]
  def createLabel(req: CreateLabelRequest): F[LabelResponse]

object LabelService:
  inline def apply[F[_]: LabelService]: LabelService[F] = summon
