package octogato.common

import octogato.common.AcceptP
import octogato.common.Accept
import octogato.common.syntax.refineU

object GithubHeaders:
  val Accept: Accept = "application/vnd.github.v3+json".refineU[AcceptP]
