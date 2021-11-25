package octogato.label

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Interval
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.collection.{MaxSize, MinSize, Size}
import eu.timepit.refined.boolean.And
import eu.timepit.refined.string.HexStringSpec
import octogato.common.StringLength
import octogato.common.NonBlankStringP
import octogato.common.NonBlankString
import octogato.common.Opaque

type PageP = Positive
type Page = Int Refined PageP

// 100 is the maximum number of items/page accepted by Github API
type PerPageP = Interval.Closed[1, 100]
type PerPage = Int Refined PerPageP

type LabelColorP = StringLength[6] And HexStringSpec
object LabelColor extends Opaque[String Refined LabelColorP]
type LabelColor = LabelColor.OpaqueType

type LabelNameP = NonBlankStringP
object LabelName extends Opaque[NonBlankString]
type LabelName = LabelName.OpaqueType

type LabelDescriptionP = NonBlankStringP
object LabelDescription extends Opaque[NonBlankString]
type LabelDescription = LabelDescription.OpaqueType

type OwnerP = NonBlankStringP
object Owner extends Opaque[NonBlankString]
type Owner = Owner.OpaqueType

type RepoP = NonBlankStringP
object Repo extends Opaque[NonBlankString]
type Repo = Repo.OpaqueType
