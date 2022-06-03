package io.porcinity.piggcrapp.Persistence

import io.porcinity.piggcrapp.Domain.Exercise.Exercise
import io.porcinity.piggcrapp.Domain.Workout.WorkoutId
import io.porcinity.piggcrapp.Domain.Exercise.ExerciseId

trait Exercises[F[_]] {
  def findAllExercises(workoutId: WorkoutId): F[List[Exercise]]
  def findSingleExercise(exerciseId: ExerciseId): F[Option[Exercise]]
  def insertExercise(exercise: Exercise): F[Exercise]
  def updateExercise(workoutId: WorkoutId, exercise: Exercise): F[Option[Exercise]]
  def deleteExercise(exerciseId: ExerciseId): F[Option[ExerciseId]]
}
