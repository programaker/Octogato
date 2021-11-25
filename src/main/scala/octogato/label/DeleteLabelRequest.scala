package octogato.label

import octogato.common.Token
import octogato.common.Accept

case class DeleteLabelRequest(
  token: Token,
  accept: Accept,
  labelPath: LabelPath,
  name: LabelName
):
  export labelPath.*

object DeleteLabelRequest:
  def make(token: Token, labelPath: LabelPath, name: LabelName): DeleteLabelRequest =
    DeleteLabelRequest(
      accept = Accept.Recommended,
      token = token,
      labelPath = labelPath,
      name = name
    )
