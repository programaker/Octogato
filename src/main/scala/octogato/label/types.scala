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

type PageP = Positive
type Page = Int Refined PageP

type PerPageP = Interval.Closed[1, 100]
type PerPage = Int Refined PerPageP

type LabelColorP = StringLength[6] And HexStringSpec
type LabelColor = String Refined LabelColorP

type LabelName = NonBlankString
type LabelDescription = NonBlankString

type OwnerP = NonBlankStringP
type Owner = NonBlankString

type RepoP = NonBlankStringP
type Repo = NonBlankString
