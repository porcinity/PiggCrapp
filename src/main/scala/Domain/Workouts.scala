package Domain

import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID

case class Workout(workoutId: WorkoutId,
                   date: LocalDate,
                   variation: WorkoutVariation,
                   exercises: List[Exercise],
                   owner: UserId)

opaque type WorkoutId = UUID

object WorkoutId:
  def apply(id: UUID): WorkoutId = id

enum WorkoutVariation:
  case UpperA, UpperB, UpperC, LowerA, LowerB, LowerC