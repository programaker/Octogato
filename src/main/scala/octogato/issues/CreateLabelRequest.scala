package octogato.issues

import octogato.common.GithubHeaders
import octogato.common.NonBlankString

case class CreateLabelRequest(
  accept: NonBlankString,
  labelPath: LabelPath,
  body: CreateLabelRequest.Body
)
object CreateLabelRequest:
  case class Body(
    name: NonBlankString,
    color: Color,
    description: NonBlankString
  )

  def make(
    owner: NonBlankString,
    repo: NonBlankString,
    name: NonBlankString,
    color: Color,
    description: NonBlankString
  ): CreateLabelRequest =
    CreateLabelRequest(
      accept = GithubHeaders.Accept,
      labelPath = LabelPath(owner, repo),
      body = Body(name, color, description)
    )
