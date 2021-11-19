package octogato.issues

import cats.syntax.option.*
import octogato.common.GithubHeaders
import octogato.common.NonBlankString
import octogato.common.refineU

case class ListRepositoryLabelsRequest(
  accept: NonBlankString,
  labelPath: LabelPath,
  per_page: Option[PerPage],
  page: Option[Page]
)
object ListRepositoryLabelsRequest:
  def make(labelPath: LabelPath): ListRepositoryLabelsRequest =
    ListRepositoryLabelsRequest(
      accept = GithubHeaders.Accept,
      per_page = refineU[PerPageP](30).some,
      page = refineU[PageP](1).some,
      labelPath = labelPath
    )
