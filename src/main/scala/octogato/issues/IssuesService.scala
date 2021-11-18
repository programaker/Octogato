package octogato.issues

trait IssuesService[F[_]]:
  def listRepositoryLabels(req: LabelRequest): F[List[LabelResponse]]

object IssuesService:
  inline def apply[F[_]](using is: IssuesService[F]): IssuesService[F] = is
