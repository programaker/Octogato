package octogato.issues

import octogato.common.GithubHeaders

final case class CreateLabelRequest(
  accept: String,
  labelPath: LabelPath,
  body: CreateLabelRequest.Body
)
object CreateLabelRequest:
  case class Body(
    name: String,
    color: Color,
    description: String
  )

  def make(owner: String, repo: String, name: String, color: Color, description: String): CreateLabelRequest =
    CreateLabelRequest(
      accept = GithubHeaders.Accept,
      labelPath = LabelPath(owner, repo),
      body = Body(name, color, description)
    )
