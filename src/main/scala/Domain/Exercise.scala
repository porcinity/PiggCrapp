package Domain

import cats.data.*
import cats.syntax.all.*
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import eu.timepit.refined.api._
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import eu.timepit.refined.types.numeric.NonNegInt
import eu.timepit.refined.types.string.NonEmptyFiniteString
import io.circe.Codec
import io.circe.refined._

import java.time.*

object Exercise {

  case class Exercise(
      exerciseId: ExerciseId,
      exerciseName: ExerciseName,
      sets: List[Sets],
      workoutId: Workout.WorkoutId
  )

  extension (exercise: Exercise)
    def addSet(set: Sets): Exercise =
      exercise.copy(sets = exercise.sets :+ set)

  type ExerciseId = NonEmptyFiniteString[21]

  object ExerciseId
      extends RefinedTypeOps[ExerciseId, String]
      with CatsRefinedTypeOpsSyntax

  type ExerciseName = NonEmptyFiniteString[30]

}
