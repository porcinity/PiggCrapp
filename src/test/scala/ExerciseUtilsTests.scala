import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues
import io.porcinity.piggcrapp.Domain.Exercise.*
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.scalatest.Inside

class ExerciseUtilsTests extends AnyWordSpec with EitherValues with Inside {

  val workoutId = NanoIdUtils.randomNanoId

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
      val createExercise = CreateExercise("")

      val result = CreateExercise.toDomain(createExercise, workoutId)
      
      assert(result.isLeft)
    }
    "should be created with valid input" in {
      val createDto = CreateExercise("Squat")
      val result = CreateExercise.toDomain(createDto, workoutId)

      assert(result.isRight)
      inside(result) { case Right(exercise) => exercise.exerciseName.value == "Squat" }
    }
    // "should add a valid set to list of sets" in {
    //   val regularSetId = RegularSetId(5)
    //   val weight = Weight(300)
    //   val reps = Reps(13)
    //   val exerciseName = ExerciseName("Squat")
    //   val workoutId = WorkoutId(UUID.randomUUID())

    //   val exercise =
    //     for n <- exerciseName
    //     yield Exercise(n, workoutId)

    //   val regularSet = for
    //     e <- exercise
    //     w <- weight
    //     r <- reps
    //   yield RegularSet(regularSetId, w, r, e.exerciseId)

    //   val result = for
    //     e <- exercise
    //     rs <- regularSet
    //   yield e.addSet(Sets.Regular(rs))

    //   assert(result.value.sets.length == 1)
    // }
    // "should not add an invalid set to list of sets" in {
    //   val regularSetId = RegularSetId(5)
    //   val weight = Weight(0)
    //   val reps = Reps(-3)
    //   val exerciseName = ExerciseName("Squat")
    //   val workoutId = WorkoutId(UUID.randomUUID())

    //   val exercise =
    //     for n <- exerciseName
    //     yield Exercise(n, workoutId)

    //   val regularSet = for
    //     e <- exercise
    //     w <- weight
    //     r <- reps
    //   yield RegularSet(regularSetId, w, r, e.exerciseId)

    //   val result = for
    //     e <- exercise
    //     rs <- regularSet
    //   yield e.addSet(Sets.Regular(rs))

    //   assert(exercise.isRight && result.isLeft)
    // }
  }
}
