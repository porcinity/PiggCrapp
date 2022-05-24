import io.porcinity.piggcrapp.Domain.Workout.*
import io.porcinity.piggcrapp.Domain.User.*
import org.scalatest.wordspec.*

import java.util.UUID
import java.time.LocalDate
import org.scalatest.Inside

class WorkoutUtilsTests extends AnyWordSpec with Inside {

  "A Workout" should {

    "not be created with invalid input." in {
      val variation = "UpperD"
      val workoutDto = WorkoutDto(LocalDate.now, variation)
      val result = WorkoutDto.toDomain(workoutDto, "")

      inside(result) { case Left(e) =>
        e.contains("Invalid variationss.") && e.length == 2
      }

    }

    "be created with valid input." in {
      val variation = "UpperC"
      val userId = UserId.unsafeFrom("1234")
      val workout = WorkoutDto(LocalDate.now, variation)
      val result = WorkoutDto.toDomain(workout, "1234")

      inside(result) { case Right(w) =>
        w.variation == WorkoutVariation.UpperC && w.user == userId
      }
    }

  }
}
