package octogato.label

import eu.timepit.refined.api.Refined
import io.circe.Codec
import io.circe.refined.*
import octogato.common.Accept
import octogato.common.Token
import octogato.common.NonBlankString
import octogato.common.NonBlankStringP

import scala.util.chaining.*

case class CreateLabelRequest(
  token: Token,
  accept: Accept,
  labelPath: LabelPath,
  body: CreateLabelRequest.Body
):
  export labelPath.*

object CreateLabelRequest:
  case class Body(
    name: LabelName,
    color: LabelColor,
    description: Option[LabelDescription]
  ) derives Codec.AsObject

  def make(
    token: Token,
    owner: Owner,
    repo: Repo,
    name: LabelName,
    color: LabelColor,
    description: Option[LabelDescription]
  ): CreateLabelRequest =
    CreateLabelRequest(
      accept = Accept.Recommended,
      token = token,
      labelPath = LabelPath(owner, repo),
      body = Body(name, color, description)
    )

  def withLabelPath(
    token: Token,
    labelPath: LabelPath
  ): (LabelName, LabelColor, Option[LabelDescription]) => CreateLabelRequest =
    make.curried(token)(labelPath.owner)(labelPath.repo).pipe(Function.uncurried)
