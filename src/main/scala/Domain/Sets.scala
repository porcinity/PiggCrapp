package Domain

import java.util.UUID

enum Sets:
  case Regular(regularSet: RegularSet)

case class RegularSet(regularSetId: RegularSetId,
                      weight: Weight,
                      reps: Reps,
                      exerciseId: ExerciseId)

opaque type RegularSetId = UUID

object RegularSetId:
  def apply(id: UUID): RegularSetId = id

opaque type Weight = Double

object Weight:
  def apply(weight: Double): Either[String, Weight] = weight match
    case TooHeavy() => Left("Weight must by less than or equal to 1,000 lbs.")
    case TooLight() => Left("Weight must be greater than or equal to 0 lbs.")
    case OkReps() => Right(weight)

opaque type Reps = Int

object Reps:
  def apply(reps: Int): Either[String, Reps] = reps match
    case TooManyReps() => Left("Reps must be fewer than 100.")
    case TooFewReps() => Left("Rest must be greater than or equal to 0.")
    case OkReps() => Right(reps)

object TooManyReps:
  def unapply(x: Int): Boolean = x > 100

object TooFewReps:
  def unapply(x: Int): Boolean = x < 0

object OkReps:
  def unapply(x: Int): Boolean = 100 > x && 0 < x

object TooHeavy:
  def unapply(x: Int): Boolean = x >= 1000

object TooLight:
  def unapply(x: Int): Boolean = x <= 0

object OkWeight:
  def unapply(x: Int): Boolean = x <= 1000 && x >= 0