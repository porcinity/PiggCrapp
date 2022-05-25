import java.time.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.wordspec.*
import io.porcinity.piggcrapp.Domain.User.*
import java.util.UUID
import cats.data.NonEmptyList
import org.scalatest.Inside

class UserUtilsTests extends UnitSpec {

  "A UserName" should {
    "Not be created with invalid input." in {
      assert(UserName.from("").isLeft)
    }
    "Be created with valid input." in {
      assert(UserName.from("Porcinity").isRight)
      assert(UserName.from("Pigg").isRight)
    }
  }

  "An Age" should {
    "Not be created with invalid input." in {
      assert(Age.from(-17).isLeft)
      assert(Age.from(-101).isLeft)
    }
    "Be created with valid input." in {
      assert(Age.from(30).isRight)
      assert(Age.from(60).isRight)
    }
  }

  "A UserWeight" should {
    "Not be created with invalid input." in {
      assert(UserWeight.from(-500).isLeft)
      assert(UserWeight.from(-50).isLeft)
    }
    "Be created with valid input." in {
      assert(UserWeight.from(200).isRight)
      assert(UserWeight.from(150).isRight)
    }
  }

  "A User" should {
    "not be created with invalid input." in {
      val badUser = UserDto("", -3, -10)
      val result = UserDto.toDomain(badUser)
      assert(result.isLeft)
      assert(result.left.map(x => x.head) == Left("Username must be 30 characters or fewer."))
    }

    "be created with valid input" in {
      val goodUser = UserDto("Pigg", 28, 55)
      val result = UserDto.toDomain(goodUser)
      assert(result.isRight)
      inside(result) { case Right(x) => x.age.value == 28 }
      inside(result) { case Right(u) => u.weight.value == 55 }
    }
  }
}
