import Domain.*
import org.scalatest.wordspec.*

import java.util.UUID

class WorkoutUtilsTests extends AnyWordSpec:
  "A Workout" should {
    "be created with valid input." in {
      val variation = WorkoutVariation.UpperC
      val userId = UserId(UUID.randomUUID())

      val workout = Workout(variation, userId)
      assert(workout.variation == WorkoutVariation.UpperC)
      assert(workout.variation != WorkoutVariation.LowerA)
    }
  }
