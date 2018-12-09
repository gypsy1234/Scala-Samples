package scalaTestSamples.sharingFixtures

import java.io.File
import org.scalatest._

//テストの最後にテストの処理結果による分岐処理を行いたい場合
class WithFixtureNoArgSpec extends FlatSpec {

  //各テストがtestとして引数で渡され、その前後に必要な処理を記述する
  override def withFixture(test: NoArgTest): Outcome = {

    super.withFixture(test) match {
      case failed: Failed =>
        val currDir = new File(".")
        val fileNames = currDir.list()
        info("Dir snapshot: " + fileNames.mkString(", "))
        failed
      case other => other
    }
  }

  "This test" should "succeed" in {
    assert(1 + 1 === 2)
  }

  it should "fail" in {
    assert(1 + 1 === 3)
  }
}