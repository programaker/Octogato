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

//type NonBlankStringP = MinSize[1] And Not[MatchesRegex["""^\s+$"""]] And Trimmed
type NonBlankStringP = Not[MatchesRegex["""^\s*$"""]] And Trimmed
type NonBlankString = String Refined NonBlankStringP

type UriStringP = Uri
type UriString = String Refined UriStringP

type TokenP = NonBlankStringP
opaque type Token = NonBlankString
object Token:
  given ConfigReader[Token] = summon[ConfigReader[NonBlankString]]
  def apply(value: NonBlankString): Token = value
  extension (token: Token) def value: String = value_(token)
  private def value_(token: Token): String = token.value

type AcceptP = NonBlankStringP
opaque type Accept = NonBlankString
object Accept:
  /** Accept header recommended in Github Api docs */
  val Recommended: Accept = "application/vnd.github.v3+json".refineU[AcceptP]

  def apply(value: NonBlankString): Accept = value
  extension (accept: Accept) def value: String = value_(accept)
  private def value_(accept: Accept): String = accept.value
