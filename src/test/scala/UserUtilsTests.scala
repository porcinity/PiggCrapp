import Domain.*

import java.time.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.wordspec.*
import java.util.UUID

class UserUtilsTests extends AnyWordSpec:

  "A UserName" should {
    "Not be created with invalid input." in {
      assert(UserName("Porcinity$") == Left("Name cannot contain numbers or special characters."))
      assert(UserName("Po") == Left("Name cannot be fewer than 3 characters."))
      assert(UserName("PorcinePorcinePorcinePorcinePorcine") == Left("Name cannot be over 30 characters."))
      assert(UserName("") == Left("Name cannot be empty."))
    }
    "Be created with valid input." in {
      assert(UserName("Porcinity").isRight)
      assert(UserName("Pigg").isRight)
    }
  }

  "An Age" should {
    "Not be created with invalid input." in {
      assert(Age(17) == Left("Age must be greater than 18."))
      assert(Age(101) == Left("Age cannot be greater than 100."))
    }
    "Be created with valid input." in {
      assert(Age(30).isRight)
      assert(Age(60).isRight)
    }
  }

  "A UserWeight" should {
    "Not be created with invalid input." in {
      assert(UserWeight(500) == Left("Weight must be less than 400lbs."))
      assert(UserWeight(50) == Left("Weight cannot be less than 70lbs."))
    }
    "Be created with valid input." in {
      assert(UserWeight(200).isRight)
      assert(UserWeight(150).isRight)
    }
  }

  "A User" should {
    "not be created with invalid input." in {
      val name = UserName("")
      val age = Age(10)
      val weight = UserWeight(500)

      val user = for
        n <- name
        a <- age
        w <- weight
      yield User(n, a, w)

      assert(user.isLeft)
    }

    "be created with valid input" in {
      val name = UserName("Porcinity")
      val age = Age(50)
      val weight = UserWeight(200)

      val user = for
        n <- name
        a <- age
        w <- weight
      yield User(n, a, w)

      assert(user.isRight)

    }
  }

