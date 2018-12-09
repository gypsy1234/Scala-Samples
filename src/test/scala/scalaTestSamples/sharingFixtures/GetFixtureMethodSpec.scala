package scalaTestSamples.sharingFixtures

import org.scalatest.FlatSpec
import scala.collection.mutable.ListBuffer

//各テストで同一のfixtureを共有する必要がある場合
//Fixtureをクリーンナップしない場合のみ有効
class GetFixtureMethodSpec extends FlatSpec {

  //テストクラスのコンストラクタにfixture取得メソッドを宣言
  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }

  //必要な各テストケースで呼び出す
  "Testing" should "be easy" in {
    val f = fixture
    f.builder.append("easy!")
    assert(f.builder.toString === "ScalaTest is easy!")
    assert(f.buffer.isEmpty)
    f.buffer += "sweet"
  }

  it should "be fun" in {
    val f = fixture
    f.builder.append("fun!")
    assert(f.builder.toString === "ScalaTest is fun!")
    assert(f.buffer.isEmpty)
  }
}