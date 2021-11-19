package octogato.issues

import eu.timepit.refined.refineV
import cats.syntax.option.*

final case class ListRepositoryLabelsRequest(
  accept: String,
  labelPath: LabelPath,
  per_page: Option[PerPage],
  page: Option[Page]
)
object ListRepositoryLabelsRequest:
  def make(owner: String, repo: String): ListRepositoryLabelsRequest =
    ListRepositoryLabelsRequest(
      accept = "application/vnd.github.v3+json",
      per_page = refineV[PerPageP].unsafeFrom(30).some,
      page = refineV[PageP].unsafeFrom(1).some,
      labelPath = LabelPath(owner, repo)
    )
