package octogato.issues

trait IssuesService[F[_]]:
  def listRepositoryLabels(req: ListRepositoryLabelsRequest): F[List[LabelResponse]]
  def createLabel(req: CreateLabelRequest): F[LabelResponse]

object IssuesService:
  inline def apply[F[_]: IssuesService]: IssuesService[F] = summon
