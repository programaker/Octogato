package octogato.issues

case class LabelResponse(
  id: Long,
  node_id: String,
  url: String,
  name: String,
  description: String,
  color: String,
  default: Boolean
)
