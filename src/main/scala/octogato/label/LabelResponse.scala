package octogato.label

import io.circe.Codec
import io.circe.refined.*
import octogato.common.UriString

case class LabelResponse(
  id: Long,
  node_id: String,
  url: UriString,
  name: LabelName,
  description: Option[LabelDescription],
  color: LabelColor,
  default: Boolean
) derives Codec.AsObject
