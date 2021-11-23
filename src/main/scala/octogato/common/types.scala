package octogato.common

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.boolean.Not
import eu.timepit.refined.collection.MaxSize
import eu.timepit.refined.collection.MinSize
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.string.Trimmed
import eu.timepit.refined.string.Uri
import octogato.common.syntax.*

// Size[N] refinement does not work for Strings,
// but MinSize[N] and MaxSize[N] do somehow =S
type StringLength[N] = MinSize[N] And MaxSize[N]

//type NonBlankStringP = MinSize[1] And Not[MatchesRegex["""^\s+$"""]] And Trimmed
type NonBlankStringP = Not[MatchesRegex["""^\s*$"""]] And Trimmed
type NonBlankString = String Refined NonBlankStringP

type UriStringP = Uri
type UriString = String Refined UriStringP

type TokenP = NonBlankStringP
type Token = NonBlankString

type AcceptP = NonBlankStringP
type Accept = NonBlankString
object Accept:
  /** Accept header recommended in Github Api docs */
  val Recommended: Accept = "application/vnd.github.v3+json".refineU[AcceptP]
