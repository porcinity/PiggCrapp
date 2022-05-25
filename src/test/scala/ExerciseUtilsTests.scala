import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues
import io.porcinity.piggcrapp.Domain.Exercise.*
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.scalatest.Inside
import io.porcinity.piggcrapp.Domain.Workout.WorkoutId
import io.porcinity.piggcrapp.Domain.Sets.RegularSet
import io.porcinity.piggcrapp.Domain.Sets.AddRegularSet
import io.porcinity.piggcrapp.Domain.Sets.Sets.*

class ExerciseUtilsTests extends UnitSpec {

  val workoutId = WorkoutId.unsafeFrom(NanoIdUtils.randomNanoId)
  val exerciseId = ExerciseId.unsafeFrom(NanoIdUtils.randomNanoId)

  "An ExerciseName" should {
    "not be created with invalid input" in {
      assert(ExerciseName.from("").isLeft)
    }
    "be created with valid input" in {
      assert(ExerciseName.from("Squat").isRight)
      assert(ExerciseName.from("Bench Press").isRight)
      assert(ExerciseName.from("Deadlift").isRight)
    }
  }

  "An Exercise" should {

    "not be created with invalid input" in {
      val createExercise = ExerciseData("")

      val result = Exercise.create(createExercise, workoutId)

      assert(result.isLeft)
    }

    "should be created with valid input" in {
      val createDto = ExerciseData("Squat")
      val result = Exercise.create(createDto, workoutId)

      assert(result.isRight)
      inside(result) { case Right(exercise) =>
        exercise.exerciseName.value == "Squat"
      }
    }

    "should add a valid set to list of sets" in {
      val createDto = ExerciseData("Squat")
      val exercise = Exercise.create(createDto, workoutId)
      val regularSet =
        AddRegularSet.toDomain(AddRegularSet(100.0, 10), exerciseId)

      val result = for {
        e <- exercise
        set <- regularSet
      } yield e.addSet(Regular(set))

      assert(result.isRight)

    }

    "should not add an invalid set to list of sets" in {
      val createDto = ExerciseData("Squat")
      val exercise = Exercise.create(createDto, workoutId)
      val regularSet =
        AddRegularSet.toDomain(AddRegularSet(100000.0, -10), exerciseId)

      val result = for {
        e <- exercise
        set <- regularSet
      } yield e.addSet(Regular(set))

      assert(result.isLeft)
    }
  }
}
