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
import octogato.config.given
import pureconfig.ConfigReader

// Size[N] refinement does not work for Strings,
// but MinSize[N] and MaxSize[N] do somehow =S
type StringLength[N] = MinSize[N] And MaxSize[N]

type NonBlankStringP = Not[MatchesRegex["""^\s*$"""]] And Trimmed
type NonBlankString = String Refined NonBlankStringP

type UriStringP = Uri
type UriString = String Refined UriStringP

type TokenP = NonBlankStringP
object Token extends Opaque[NonBlankString]:
  given ConfigReader[Token] = summon[ConfigReader[NonBlankString]].map(Token.apply(_))
type Token = Token.OpaqueType

type AcceptP = NonBlankStringP
object Accept extends Opaque[NonBlankString]:
  /** Accept header recommended in Github Api docs */
  val Recommended: Accept = Accept("application/vnd.github.v3+json".refineU[AcceptP])
type Accept = Accept.OpaqueType
