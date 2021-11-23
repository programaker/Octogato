package octogato.label

import cats.syntax.option.*
import octogato.common.NonBlankString
import octogato.common.Token
import octogato.common.Accept
import octogato.common.syntax.refineU

case class ListRepositoryLabelsRequest(
  token: Token,
  accept: Accept,
  labelPath: LabelPath,
  per_page: Option[PerPage],
  page: Option[Page]
):
  export labelPath.*

object ListRepositoryLabelsRequest:
  def make(token: Token, labelPath: LabelPath): ListRepositoryLabelsRequest =
    ListRepositoryLabelsRequest(
      accept = Accept.Recommended,
      per_page = 30.refineU[PerPageP].some,
      page = 1.refineU[PageP].some,
      token = token,
      labelPath = labelPath
    )
