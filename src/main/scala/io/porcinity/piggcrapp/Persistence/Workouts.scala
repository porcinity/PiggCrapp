package io.porcinity.piggcrapp.Persistence

import io.porcinity.piggcrapp.Domain.User.UserId
import io.porcinity.piggcrapp.Domain.Workout.WorkoutId
import io.porcinity.piggcrapp.Domain.Workout.Workout

trait Workouts[F[_]] {
  def findAllWorkouts(userId: UserId): F[List[Workout]]
  def findSingleWorkout(workoutId: WorkoutId): F[Option[Workout]]
  def insertWorkout(userId: UserId, workout: Workout): F[WorkoutId]
  def updateWorkout(userId: UserId, workout: Workout): F[Option[Workout]]
  def deleteWorkout(workoutId: WorkoutId): F[Option[WorkoutId]]
}

