package scalaTestSamples.sharingFixtures

import org.scalatest.fixture
import java.io._

//全てのテストで複数のFixtureを共有する場合に有効
class WithFixtureOneArgSpec extends fixture.FlatSpec {

  case class FixtureParam(file: File, writer: FileWriter)

  def withFixture(test: OneArgTest): Any = {
    val file = File.createTempFile("hello", "world") // create the fixture
    val writer = new FileWriter(file)
    val theFixture = FixtureParam(file, writer)

    try {
      writer.write("ScalaTest is ") // set up the fixture
      withFixture(test.toNoArgTest(theFixture)) // "loan" the fixture to the test
    }
    finally writer.close() // clean up the fixture
  }

  "Testing" should "be easy" in { f =>
    f.writer.write("easy!")
    f.writer.flush()
    assert(f.file.length === 18)
  }

  it should "be fun" in { f =>
    f.writer.write("fun!")
    f.writer.flush()
    assert(f.file.length === 17)
  }
}