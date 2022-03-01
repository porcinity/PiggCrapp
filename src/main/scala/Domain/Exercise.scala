package Domain

import java.util.UUID

case class Exercise(exerciseId: ExerciseId,
                    exerciseName: ExerciseName,
                    sets: List[Sets],
                    workoutId: WorkoutId)

object Exercise:
  def apply(exerciseName: ExerciseName, workoutId: WorkoutId): Exercise =
    val id = ExerciseId(UUID.randomUUID())
    val sets = List[Sets]()

    new Exercise(id, exerciseName, sets, workoutId)

  extension (exercise: Exercise)
    def addSet(set: Sets): Exercise =
      exercise.copy(sets = exercise.sets :+ set)


opaque type ExerciseId = UUID

object ExerciseId:
  def apply(id: UUID): ExerciseId= id

opaque type ExerciseName = String

object ExerciseName:
  def apply(name: String): Either[String, ExerciseName] = name match
    case Empty() => Left("Exercise name cannot be empty.")
    case TooLong() => Left("Exercise name cannot be over 50 characters.")
    case TooShort() => Left("Exercise name cannot be fewer than 5 characters.")
    case NumbersOrChars() => Left("Exercise name cannot contain numbers or special characters.")
    case _ => Right(name)

object TooShort:
  def unapply(x: String): Boolean = x.length < 5 && x.nonEmpty

object TooLong:
  def unapply(x: String): Boolean = x.length > 50

object NumbersOrChars:
  def unapply(x: String): Boolean = !x.matches("^[a-z A-Z]+$")

object Empty:
  def unapply(x: String): Boolean = x.isEmpty