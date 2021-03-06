package io.porcinity.piggcrapp.Domain

import cats.data.*
import cats.syntax.all.*
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import eu.timepit.refined.api._
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import eu.timepit.refined.types.numeric.NonNegInt
import eu.timepit.refined.types.string.NonEmptyFiniteString
import io.circe.Codec
import io.circe.refined._
import io.porcinity.piggcrapp.Domain.Sets.Sets

import java.time.*
import Workout.WorkoutId

object Exercise {

  final case class Exercise(
      exerciseId: ExerciseId,
      exerciseName: ExerciseName,
      // sets: List[Sets],
      workoutId: Workout.WorkoutId
  ) derives Codec.AsObject

  object Exercise {

    def create(
        data: ExerciseData,
        workoutId: WorkoutId
    ): Either[NonEmptyChain[String], Exercise] = {
      val id = NanoIdUtils.randomNanoId

      (
        ExerciseId.from(id).toEitherNec,
        ExerciseName
          .from(data.name)
          .leftMap(_ => "Exercise name must be 30 characters or fewer.")
          .toEitherNec,
        // List[Sets]().asRight.toEitherNec,
        workoutId.asRight.toEitherNec
      ).parMapN(Exercise.apply)
    }

  //   extension (exercise: Exercise)
  //     def addSet(set: Sets): Exercise =
  //       exercise.copy(sets = exercise.sets :+ set)
  }

  type ExerciseId = NonEmptyFiniteString[21]

  object ExerciseId
      extends RefinedTypeOps[ExerciseId, String]
      with CatsRefinedTypeOpsSyntax

  type ExerciseName = NonEmptyFiniteString[30]

  object ExerciseName
      extends RefinedTypeOps[ExerciseName, String]
      with CatsRefinedTypeOpsSyntax

  final case class ExerciseData(name: String) derives Codec.AsObject

  // object ExerciseData {

  //   def toDomain(data: ExerciseData): Either[NonEmptyChain[String], ExerciseName] 
    
  // }

}
