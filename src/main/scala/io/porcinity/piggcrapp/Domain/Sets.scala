package io.porcinity.piggcrapp.Domain

import cats.data.*
import cats.syntax.all.*
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import eu.timepit.refined.api._
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import eu.timepit.refined.types.numeric.NonNegInt
import eu.timepit.refined.types.string.NonEmptyFiniteString
import eu.timepit.refined.numeric.*
import eu.timepit.refined.generic.*
import io.circe.Codec
import io.circe.refined._

import java.time.*
import eu.timepit.refined.types.numeric.NonNegDouble
import eu.timepit.refined.numeric.Less
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.Interval
import eu.timepit.refined.boolean.Not

object Sets {

  enum Sets:
    case Regular(regularSet: RegularSet)
    case RestPause(restPauseSet: RestPauseSet)
    case WidowMaker(widowMakerSet: WidowMakerSet)
    case ExtremeStretch(extremeStretch: ExtremeStretchSet)

  case class RegularSet(
      regularSetId: RegularSetId,
      weight: Weight,
      reps: Reps,
      exercise: Exercise.ExerciseId
  )

  case class RestPauseSet(
      restPauseSetId: RestPauseSetId,
      range: RestPauseRange,
      weight: Weight,
      restPauseSets: List[Reps],
      exercise: Exercise.ExerciseId
  )

  object RestPauseSet {

    def apply(
        restPauseSetId: RestPauseSetId,
        range: RestPauseRange,
        weight: Weight,
        exerciseId: Exercise.ExerciseId
    ) =
      new RestPauseSet(restPauseSetId, range, weight, List[Reps](), exerciseId)

    extension (rpSet: RestPauseSet) {
      def addReps(reps: Reps): Either[String, RestPauseSet] =
        rpSet.restPauseSets match {
          case x if x.length == 3 => Left("All Rest Pause sets are completed.")
          case _ =>
            Right(rpSet.copy(restPauseSets = rpSet.restPauseSets :+ reps))
        }
    }
  }

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

  type RegularSetId = NonEmptyFiniteString[21]

  object RegularSetId
      extends RefinedTypeOps[RegularSetId, String]
      with CatsRefinedTypeOpsSyntax

  type RestPauseSetId = NonEmptyFiniteString[21]

  object RestPauseSetId
      extends RefinedTypeOps[RestPauseSetId, String]
      with CatsRefinedTypeOpsSyntax

  type WidowMakerSetId = NonEmptyFiniteString[21]

  object WidowMakerSetId
      extends RefinedTypeOps[WidowMakerSetId, String]
      with CatsRefinedTypeOpsSyntax

  opaque type Time = Double

  type ExtremeStretchId = NonEmptyFiniteString[21]

  object ExtremeStretchId
      extends RefinedTypeOps[ExtremeStretchId, String]
      with CatsRefinedTypeOpsSyntax

  object Time:
    def apply(time: Double): Time = time

  enum RestPauseRange:
    case Base, Medium, High

  type Weight = Double Refined Interval.Closed[0.0, 1000.0]

  object Weight
      extends RefinedTypeOps[Weight, Double]
      with CatsRefinedTypeOpsSyntax

  type Reps = Int Refined Interval.Closed[0, 150]

  object Reps extends RefinedTypeOps[Reps, Int] with CatsRefinedTypeOpsSyntax

}
