package octogato.common

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*
import eu.timepit.refined.refineV as $refineV
import eu.timepit.refined.numeric.Positive
import cats.syntax.either.*

final class RefinedSyntaxSuite extends ScalaCheckSuite:
  import octogato.common.syntax.*

  property("refineV syntax") {
    forAll { (n: Int) =>
      val ref = n.refineV[Positive]
      val ref2 = $refineV[Positive](n)

      (ref, ref2) match
        case (Left(a), Left(b))   => assertEquals(a, b)
        case (Right(a), Right(b)) => assertEquals(a.value, b.value)
        case (a, b)               => fail("Error in refineV syntax", clues(a, b))
    }
  }

  property("refineE syntax") {
    forAll { (n: Int) =>
      val ref = n.refineE[Positive]
      val ref2 = $refineV[Positive](n)

      (ref, ref2) match
        case (Left(a), Left(b))   => assertEquals(a, RefinementError(b))
        case (Right(a), Right(b)) => assertEquals(a.value, b.value)
        case (a, b)               => fail("Error in refineE syntax", clues(a, b))
    }
  }

  property("refineU syntax") {
    forAll { (n: Int) =>
      (n > 0) ==> {
        val ref = n.refineU[Positive]
        val ref2 = $refineV[Positive].unsafeFrom(n)
        assertEquals(ref.value, ref2.value)
        true
      }
    }

    forAll { (n: Int) =>
      (n <= 0) ==> {
        intercept[IllegalArgumentException](n.refineU[Positive])
        true
      }
    }
  }
