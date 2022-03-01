import Domain.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues

import java.util.UUID

class ExerciseUtilsTests extends AnyWordSpec with EitherValues:
  "An ExerciseName" should {
    "not be created with invalid input" in {
      assert(ExerciseName("") == Left("Exercise name cannot be empty."))
      assert(ExerciseName("ASuperDuperVeryExtraLongExerciseNameThatShouldNotBeAccepted") == Left("Exercise name cannot be over 50 characters."))
      assert(ExerciseName("arms") == Left("Exercise name cannot be fewer than 5 characters."))
      assert(ExerciseName("B@dExerc!seName") == Left("Exercise name cannot contain numbers or special characters."))
      assert(ExerciseName("BadEx3rciseName") == Left("Exercise name cannot contain numbers or special characters."))
    }
    "be created with valid input" in {
      assert(ExerciseName("Squat").isRight)
      assert(ExerciseName("Bench Press").isRight)
      assert(ExerciseName("Deadlift").isRight)
    }
  }

  "An Exercise" should {
    "not be created with invalid input" in {
      val name = ExerciseName("arms")
      val workoutId = WorkoutId(UUID.randomUUID())

      val exercise = for
        n <- name
      yield Exercise(n, workoutId)

      assert(exercise.isLeft)
    }
    "should be created with valid input" in {
      val name = ExerciseName("Squat")
      val workoutId = WorkoutId(UUID.randomUUID())

      val exercise = for
        n <- name
      yield Exercise(n, workoutId)

      assert(exercise.isRight)
    }
    "should add a valid set to list of sets" in {
      val regularSetId = RegularSetId(UUID.randomUUID())
      val weight = Weight(300)
      val reps = Reps(13)
      val exerciseName = ExerciseName("Squat")
      val workoutId = WorkoutId(UUID.randomUUID())

      val exercise = for
        n <- exerciseName
      yield Exercise(n, workoutId)

      val regularSet = for
        e <- exercise
        w <- weight
        r <- reps
      yield RegularSet(regularSetId, w, r, e.exerciseId)

      val result = for
        e <- exercise
        rs <- regularSet
      yield e.addSet(Sets.Regular(rs))

      assert(result.value.sets.length == 1)
    }
    "should not add an invalid set to list of sets" in {
      val regularSetId = RegularSetId(UUID.randomUUID())
      val weight = Weight(0)
      val reps = Reps(-3)
      val exerciseName = ExerciseName("Squat")
      val workoutId = WorkoutId(UUID.randomUUID())

      val exercise = for
        n <- exerciseName
      yield Exercise(n, workoutId)

      val regularSet = for
        e <- exercise
        w <- weight
        r <- reps
      yield RegularSet(regularSetId, w, r, e.exerciseId)

      val result = for
        e <- exercise
        rs <- regularSet
      yield e.addSet(Sets.Regular(rs))

      assert(exercise.isRight && result.isLeft)
    }
  }