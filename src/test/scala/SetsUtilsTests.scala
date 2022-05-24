import io.porcinity.piggcrapp.Domain.Sets.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues

import java.util.UUID

class SetsUtilsTests extends AnyWordSpec with EitherValues {

  "Weights" should {
    "not be created with invalid input" in {
      assert(Weight.from(1500.00).isLeft)
      assert(Weight.from(-10).isLeft)
    }
  }
    "be created with valid input" in {
      assert(Weight.from(1000).isRight)
      assert(Weight.from(400.55).isRight)
      assert(Weight.from(0).isRight)
    }
  
  "Reps" should {
    "not be created with invalid input" in {
      assert(Reps.from(155).isLeft)
      assert(Reps.from(-1).isLeft)
    }
    "should be created with valid input" in {
      assert(Reps.from(15).isRight)
      assert(Reps.from(0).isRight)
      assert(Reps.from(150).isRight)
    }
  }
  // "A RegularSet" should {
  //   "not be created with invalid weight" in {
  //     val invalidWeight = Weight(1500)
  //     val validReps = Reps(20)
  //     val setId = RegularSetId(3)
  //     val exerciseId = ExerciseId(UUID.randomUUID())

  //     val result = for
  //       w <- invalidWeight
  //       r <- validReps
  //     yield RegularSet(setId, w, r, exerciseId)

  //     assert(
  //       result.left.value === "Weight must by less than or equal to 1,000 lbs."
  //     )
  //   }
  //   "be created with valid reps" in {
  //     val validWeight = Weight(150)
  //     val validReps = Reps(20)
  //     val setId = RegularSetId(3)
  //     val exerciseId = ExerciseId(UUID.randomUUID())

  //     val result = for
  //       w <- validWeight
  //       r <- validReps
  //     yield RegularSet(setId, w, r, exerciseId)

  //     assert(result.isRight)
  //   }
  // }
  // "A RestPause set" should {
  //   "be created with valid input" in {
  //     val setId = RestPauseSetId(1)
  //     val range = RestPauseRange.Base
  //     val weight = Weight(300)
  //     val exerciseId = ExerciseId(UUID.randomUUID())

  //     val result =
  //       for w <- weight
  //       yield RestPauseSet(setId, range, w, exerciseId)

  //     assert(result.isRight)
  //   }
  //   "be able to add valid Rest Pause sets" in {
  //     val setId = RestPauseSetId(1)
  //     val range = RestPauseRange.Base
  //     val weight = Weight(300)
  //     val exerciseId = ExerciseId(UUID.randomUUID())
  //     val reps = Reps(15)

  //     val rpSet =
  //       for w <- weight
  //       yield RestPauseSet(setId, range, w, exerciseId)

  //     val result = for
  //       r <- reps
  //       s <- rpSet
  //     yield s.addReps(r).value

  //     assert(result.value.restPauseSets.length == 1)
  //   }
  //   "not be able to add more than 3 valid RestPause sets" in {
  //     val setId = RestPauseSetId(1)
  //     val range = RestPauseRange.Base
  //     val weight = Weight(300)
  //     val exerciseId = ExerciseId(UUID.randomUUID())
  //     val repsOne = Reps(15)
  //     val repsTwo = Reps(7)
  //     val repsThree = Reps(3)
  //     val repsFour = Reps(1)

  //     val rpSet =
  //       for w <- weight
  //       yield RestPauseSet(setId, range, w, exerciseId)

  //     val result = for
  //       r1 <- repsOne
  //       r2 <- repsTwo
  //       r3 <- repsThree
  //       r4 <- repsFour
  //       s <- rpSet
  //     yield s
  //       .addReps(r1)
  //       .value
  //       .addReps(r2)
  //       .value
  //       .addReps(r3)
  //       .value
  //       .addReps(r4)
  //       .left
  //       .value

  //     assert(result.value === "All Rest Pause sets are completed.")
  //   }
  // }
}
