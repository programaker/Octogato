package octogato.issues

final case class CreateLabelRequest(
  accept: String,
  labelPath: LabelPath,
  body: CreateLabelRequest.Body
)
object CreateLabelRequest:
  case class Body(
    name: String,
    color: String,
    description: String
  )

  def make(owner: String, repo: String, name: String, color: String, description: String): CreateLabelRequest =
    CreateLabelRequest(
      accept = "application/vnd.github.v3+json",
      labelPath = LabelPath(owner, repo),
      body = Body(name, color, description)
    )
