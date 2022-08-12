package octogato.common

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*

class OpaqueSuite extends ScalaCheckSuite:
  import TestTypes.*

  property("apply / unapply") {
    forAll { (n: Int) =>
      val opInt = OpaqueInt(n)
      val OpaqueInt(n2) = opInt
      n2 == n
    }
  }

  property("value") {
    forAll { (n: Int) =>
      val opInt = OpaqueInt(n)
      val n2 = opInt.value
      n2 == n
    }
  }

  property("wrap / unwrap") {
    forAll { (list: List[Int], option: Option[Int]) =>
      val opList = OpaqueInt.wrap(list)
      opList == list.map(OpaqueInt.apply(_))
      opList.unwrap == list

      val opOption = OpaqueInt.wrap(option)
      opOption == option.map(OpaqueInt.apply(_))
      opOption.unwrap == option
    }
  }

object TestTypes:
  object OpaqueInt extends Opaque[Int]
  type OpaqueInt = OpaqueInt.OpaqueType
