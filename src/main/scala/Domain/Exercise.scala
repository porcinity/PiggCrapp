package Domain

import java.util.UUID

case class Exercise(exerciseId: ExerciseId,
                    exerciseName: ExerciseName,
                    sets: List[Sets],
                    workoutId: WorkoutId)

opaque type ExerciseId = UUID

object ExerciseId:
  def apply(id: UUID): ExerciseId= id

opaque type ExerciseName = String

object ExerciseName:
  def apply(name: String): Either[String, ExerciseName] = name match
    case TooLong() => Left("Exercise name cannot be over 100 characters.")
    case TooShort() => Left("Exercise name cannot be fewer than 5 characters.")
    case NumbersOrChars() => Left("Exercise name cannot contain numbers or special characters.")
    case Empty() => Left("Exercise name cannot be empty.")
    case _ => Right(name)

object TooShort:
  def unapply(x: String): Boolean = x.length < 5 && x.nonEmpty

object TooLong:
  def unapply(x: String): Boolean = x.length > 100

object NumbersOrChars:
  def unapply(x: String): Boolean = !x.matches("^[a-zA-Z]+$")

object Empty:
  def unapply(x: String): Boolean = x.isEmpty