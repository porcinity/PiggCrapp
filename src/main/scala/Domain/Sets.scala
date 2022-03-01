package Domain

import java.util.UUID

enum Sets:
  case Regular(regularSet: RegularSet)
  case RestPause(restPauseSet: RestPauseSet)
  case WidowMaker(widowMakerSet: WidowMakerSet)
  case ExtremeStretch(extremeStretch: ExtremeStretchSet)

case class RegularSet(
                       regularSetId: RegularSetId,
                       weight: Weight,
                       reps: Reps,
                       exercise: ExerciseId
                     )

case class RestPauseSet(
                       restPauseSetId: RestPauseSetId,
                       range: RestPauseRange,
                       weight: Weight,
                       restPauseSets: List[Reps],
                       exercise: ExerciseId
                       )

case class WidowMakerSet(
                        widowMakerSetId: WidowMakerSetId,
                        weight: Weight,
                        targetReps: Reps,
                        actualReps: Reps,
                        completionTime: Time
                        )

case class ExtremeStretchSet(
                         extremeStretchId: ExtremeStretchId,
                         weight: Weight,
                         time: Time
                         )

opaque type RegularSetId = Int

object RegularSetId:
  def apply(id: Int): RegularSetId = id

opaque type RestPauseSetId = Int

object RestPauseSetId:
  def apply(id: Int): RestPauseSetId = id

opaque type WidowMakerSetId = Int

object WidowMakerSetId:
  def apply(id: Int): WidowMakerSetId = id

opaque type Time = Double

opaque type ExtremeStretchId = Int

object ExtremeStretchId:
  def apply(id: Int): ExtremeStretchId = id

object Time:
  def apply(time: Double): Time = time

enum RestPauseRange:
  case Base, Medium, High

opaque type Weight = Double

object Weight:
  def apply(weight: Double): Either[String, Weight] = weight match
    case TooHeavy() => Left("Weight must by less than or equal to 1,000 lbs.")
    case TooLight() => Left("Weight must be greater than or equal to 0 lbs.")
    case OkWeight() => Right(weight)

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
  def unapply(x: Double): Boolean = x >= 1000

object TooLight:
  def unapply(x: Double): Boolean = x <= 0

object OkWeight:
  def unapply(x: Double): Boolean = x <= 1000 && x >= 0