package scalaTestSamples.sharingFixtures

import org.scalatest._
import collection.mutable.ListBuffer

//全てのテストで同じ事前、事後処理が必要な場合
//var immutableなオブジェクト　および　val mutableなオブジェクトで可能
//このパターンでは副作用が発生するため、並列実行することはできない（ParallelTestExecutionをミックスインすることで並列実行可能）
class BeforeAndAfterSpec extends FlatSpec with BeforeAndAfter {

  val builder = new StringBuilder
  val buffer = new ListBuffer[String]

  before {
    builder.append("ScalaTest is ")
  }

  after {
    builder.clear()
    buffer.clear()
  }

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }
}