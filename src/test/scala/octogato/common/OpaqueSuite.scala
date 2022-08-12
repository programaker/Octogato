package octogato.common

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*

final class OpaqueSuite extends ScalaCheckSuite:
  import OpaqueSuite.*

  property("apply / unapply") {
    forAll { (n: Int) =>
      val opInt = OpaqueInt(n)
      val OpaqueInt(n2) = opInt
      assertEquals(n2, n)
    }
  }

  property("value") {
    forAll { (n: Int) =>
      val opInt = OpaqueInt(n)
      val n2 = opInt.value
      assertEquals(n2, n)
    }
  }

  property("wrap / unwrap") {
    forAll { (list: List[Int], option: Option[Int]) =>
      val opList = OpaqueInt.wrap(list)
      assertEquals(opList, list.map(OpaqueInt.apply(_)))
      assertEquals(opList.unwrap, list)

      val opOption = OpaqueInt.wrap(option)
      assertEquals(opOption, option.map(OpaqueInt.apply(_)))
      assertEquals(opOption.unwrap, option)
    }
  }

object OpaqueSuite:
  object OpaqueInt extends Opaque[Int]
  type OpaqueInt = OpaqueInt.OpaqueType
