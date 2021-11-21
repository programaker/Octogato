package octogato.label
package json

import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.semiauto.*
import io.circe.refined.*

import scala.language.unsafeNulls

given Decoder[LabelResponse] = deriveDecoder
given Encoder[CreateLabelRequest.Body] = deriveEncoder
