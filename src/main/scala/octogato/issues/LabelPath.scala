package octogato.issues

import octogato.common.NonBlankString

case class LabelPath(
  owner: NonBlankString,
  repo: NonBlankString
)
