package octogato.issues

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Interval
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.collection.{MaxSize, MinSize, Size}
import eu.timepit.refined.boolean.And
import eu.timepit.refined.string.HexStringSpec
import octogato.common.StringLength

type PageP = Positive
type Page = Int Refined PageP

type PerPageP = Interval.Closed[1, 100]
type PerPage = Int Refined PerPageP

type ColorP = StringLength[6] And HexStringSpec
type Color = String Refined ColorP
