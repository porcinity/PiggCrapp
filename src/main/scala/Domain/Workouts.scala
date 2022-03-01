package Domain

import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID

case class Workout(workoutId: WorkoutId,
                   date: LocalDate,
                   variation: WorkoutVariation,
                   exercises: List[Exercise],
                   owner: UserId)

object Workout:
  def apply(variation: WorkoutVariation, userId: UserId): Workout =
    val id = WorkoutId(randomUUID())
    val date = LocalDate.now()
    val exercises = List[Exercise]()

    new Workout(id, date, variation, exercises, userId)

opaque type WorkoutId = UUID

object WorkoutId:
  def apply(id: UUID): WorkoutId = id

enum WorkoutVariation:
  case UpperA, UpperB, UpperC, LowerA, LowerB, LowerC