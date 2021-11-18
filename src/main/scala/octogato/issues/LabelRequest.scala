package octogato.issues

import eu.timepit.refined.refineV
import cats.syntax.option.*

final case class LabelRequest(
  accept: String,
  owner: String,
  repo: String,
  per_page: Option[PerPage],
  page: Option[Page]
)
object LabelRequest:
  def make(owner: String, repo: String): LabelRequest =
    LabelRequest(
      accept = "application/vnd.github.v3+json",
      per_page = refineV[PerPageP].unsafeFrom(30).some,
      page = refineV[PageP].unsafeFrom(1).some,
      owner = owner,
      repo = repo
    )
