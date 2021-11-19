package octogato.issues

import eu.timepit.refined.api.Refined
import octogato.common.GithubHeaders
import octogato.common.NonBlankString
import octogato.common.NonBlankStringP

case class CreateLabelRequest(
  accept: NonBlankString,
  labelPath: LabelPath,
  body: CreateLabelRequest.Body
)
object CreateLabelRequest:
  case class Body(
    name: LabelName,
    color: LabelColor,
    description: LabelDescription
  )

  def make(
    owner: Owner,
    repo: Repo,
    name: LabelName,
    color: LabelColor,
    description: LabelDescription
  ): CreateLabelRequest =
    CreateLabelRequest(
      accept = GithubHeaders.Accept,
      labelPath = LabelPath(owner, repo),
      body = Body(name, color, description)
    )

  def withLabelPath(labelPath: LabelPath): (LabelName, LabelColor, LabelDescription) => CreateLabelRequest =
    make(labelPath.owner, labelPath.repo, _, _, _)
