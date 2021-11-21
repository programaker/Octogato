package octogato.label

import cats.syntax.option.*
import octogato.common.GithubHeaders
import octogato.common.NonBlankString
import octogato.common.refineU

case class ListRepositoryLabelsRequest(
  token: NonBlankString,
  accept: NonBlankString,
  labelPath: LabelPath,
  per_page: Option[PerPage],
  page: Option[Page]
):
  export labelPath.*

object ListRepositoryLabelsRequest:
  def make(token: NonBlankString, labelPath: LabelPath): ListRepositoryLabelsRequest =
    ListRepositoryLabelsRequest(
      accept = GithubHeaders.Accept,
      per_page = refineU[PerPageP](30).some,
      page = refineU[PageP](1).some,
      token = token,
      labelPath = labelPath
    )
