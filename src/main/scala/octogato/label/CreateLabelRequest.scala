package octogato.label

import eu.timepit.refined.api.Refined
import octogato.common.GithubHeaders
import octogato.common.NonBlankString
import octogato.common.NonBlankStringP

case class CreateLabelRequest(
  token: NonBlankString,
  accept: NonBlankString,
  labelPath: LabelPath,
  body: CreateLabelRequest.Body
):
  export labelPath.*

object CreateLabelRequest:
  case class Body(
    name: LabelName,
    color: LabelColor,
    description: LabelDescription
  )

  def make(
    token: NonBlankString,
    owner: Owner,
    repo: Repo,
    name: LabelName,
    color: LabelColor,
    description: LabelDescription
  ): CreateLabelRequest =
    CreateLabelRequest(
      accept = GithubHeaders.Accept,
      token = token,
      labelPath = LabelPath(owner, repo),
      body = Body(name, color, description)
    )

  def withLabelPath(
    token: NonBlankString,
    labelPath: LabelPath
  ): (LabelName, LabelColor, LabelDescription) => CreateLabelRequest =
    make(token, labelPath.owner, labelPath.repo, _, _, _)
