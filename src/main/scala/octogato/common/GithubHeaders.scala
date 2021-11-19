package octogato.common

import octogato.common.NonBlankStringP
import octogato.common.NonBlankString
import octogato.common.refineU

object GithubHeaders:
  val Accept: NonBlankString = refineU[NonBlankStringP]("application/vnd.github.v3+json")
