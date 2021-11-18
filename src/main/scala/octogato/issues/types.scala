package octogato.issues

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Interval
import eu.timepit.refined.numeric.Positive

type PageP = Positive
type Page = Int Refined PageP

type PerPageP = Interval.Closed[1, 100]
type PerPage = Int Refined PerPageP
