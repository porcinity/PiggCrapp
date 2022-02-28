package Domain

import java.util.UUID
import java.util.UUID.randomUUID
import com.github.nscala_time.time.Imports._

case class Workout(workoutId: WorkoutId,
                   date: DateTime,
                   variation: WorkoutVariation,
                   exercises: List[Exercise],
                   owner: UserId)

opaque type WorkoutId = UUID

object WorkoutId:
  def apply(id: UUID): WorkoutId = id

enum WorkoutVariation:
  case UpperA, UpperB, UpperC, LowerA, LowerB, LowerC