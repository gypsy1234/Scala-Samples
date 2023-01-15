import cats.data.Reader
import java.util.UUID

object IdGenMonad {
  trait UUIDGenerator {
    def gen(): UUID
  }

  case object UUIDGeneratorImpl extends UUIDGenerator {
    def gen(): UUID = UUID.randomUUID()
  }

  case object TestUUIDGenerator extends UUIDGenerator {
    def gen(): UUID = UUID.fromString("33bb5dc5-9591-47e7-8d49-6fb9df6c4c9a")
  }

  type IdGenM[T] = Reader[UUIDGenerator, T]

  object IdGenM {
    val gen: IdGenM[UUID] =
      Reader(_.gen())
  }

  case class UserId(id: UUID)

  object UserId {
    def apply(): IdGenM[UserId] =
      IdGenM.gen.map(UserId(_))
  }

  val program = for {
    userId <- UserId()
  } yield println(userId.id.toString)

//  program.run(UUIDGeneratorImpl)
}
