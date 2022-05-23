package io.porcinity.piggcrapp.Api

import io.porcinity.piggcrapp.Domain.User.UserId
import io.porcinity.piggcrapp.Domain.Workout.WorkoutId
import io.porcinity.piggcrapp.Domain.Exercise.ExerciseId

object UserIdVar {
    def unapply(str: String): Option[UserId] = Some(UserId.unsafeFrom(str))
}

object WorkoutIdVar {
  def unapply(str: String): Option[WorkoutId] = Some(WorkoutId.unsafeFrom(str))
}

object ExerciseIdVar {
  def unapply(str: String): Option[ExerciseId] = Some(ExerciseId.unsafeFrom(str))
}

object 

