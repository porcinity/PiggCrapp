import Domain.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues

import java.util.UUID

class SetsUtilsTests extends AnyWordSpec with EitherValues:
  "A Weight" should {
    "not be created with invalid input" in {
      assert(Weight(1500) == Left("Weight must by less than or equal to 1,000 lbs."))
      assert(Weight(-10) == Left("Weight must be greater than or equal to 0 lbs."))
    }
    "be created with valid input" in {
      assert(Weight(1000).isRight)
      assert(Weight(400.55).isRight)
      assert(Weight(0).isRight)
    }
  }
  "Reps" should {
    "not be created with invalid input" in {
      assert(Reps(150) == Left("Reps must be fewer than 100."))
      assert(Reps(-1) == Left("Rest must be greater than or equal to 0."))
    }
    "should be created with valid input" in {
      assert(Reps(15).isRight)
      assert(Reps(0).isRight)
      assert(Reps(100).isRight)
    }
  }
  "A RegularSet" should {
    "not be created with invalid weight" in {
      val invalidWeight = Weight(1500)
      val validReps = Reps(20)
      val setId = RegularSetId(3)
      val exerciseId = ExerciseId(UUID.randomUUID())

      val result = for
        w <- invalidWeight
        r <- validReps
      yield RegularSet(setId, w, r, exerciseId)

      assert(result.left.value === "Weight must by less than or equal to 1,000 lbs.")
    }
    "be created with valid reps" in {
      val validWeight = Weight(150)
      val validReps = Reps(20)
      val setId = RegularSetId(3)
      val exerciseId = ExerciseId(UUID.randomUUID())

      val result = for
        w <- validWeight
        r <- validReps
      yield RegularSet(setId, w, r, exerciseId)

      assert(result.isRight)
    }
  }