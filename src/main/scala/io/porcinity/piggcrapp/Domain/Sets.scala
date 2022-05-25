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
import io.porcinity.piggcrapp.Domain.Exercise.ExerciseId
import cats.syntax.all.*
import io.porcinity.piggcrapp.Domain.Sets.RegularSet

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
  ) derives Codec.AsObject

  case class AddRegularSet(weight: Double, reps: Int) derives Codec.AsObject

  object AddRegularSet {

    def toDomain(
        setDto: AddRegularSet,
        exerciseId: ExerciseId
    ): EitherNec[String, RegularSet] = {
      val id = NanoIdUtils.randomNanoId

      (
        RegularSetId.from(id).toEitherNec,
        Weight.from(setDto.weight).toEitherNec,
        Reps.from(setDto.reps).toEitherNec,
        exerciseId.asRight.toEitherNec
      ).parMapN(RegularSet.apply)

    }

  }

  case class RestPauseSet(
      restPauseSetId: RestPauseSetId,
      range: RestPauseRange,
      weight: Weight,
      restPauseSets: List[Reps],
      exercise: Exercise.ExerciseId
  )

  object RestPauseSet {

    // def apply(
    //     restPauseSetId: RestPauseSetId,
    //     range: RestPauseRange,
    //     weight: Weight,
    //     exerciseId: Exercise.ExerciseId
    // ) =
    //   new RestPauseSet(restPauseSetId, range, weight, List[Reps](), exerciseId)

    def create(data: AddRestPauseSet, exerciseId: ExerciseId) = {
      (
        RestPauseSetId.from(NanoIdUtils.randomNanoId).toEitherNec,
        RestPauseRange.fromString(data.range).toEitherNec,
        Weight.from(data.weight).toEitherNec,
        List[Reps]().asRight.toEitherNec,
        exerciseId.asRight.toEitherNec
      ).parMapN(RestPauseSet.apply)
    }

    extension (rpSet: RestPauseSet) {
      def addReps(reps: Reps): Either[String, RestPauseSet] =
        rpSet.restPauseSets match {
          case x if x.length == 3 => Left("All Rest Pause sets are completed.")
          case _ =>
            Right(rpSet.copy(restPauseSets = rpSet.restPauseSets :+ reps))
        }
    }
  }

  case class AddRestPauseSet(range: String, weight: Double)
      derives Codec.AsObject

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

  enum RestPauseRange {
    case Base, Medium, High
  }

  object RestPauseRange {

    def fromString(range: String): Either[String, RestPauseRange] =
      range match {
        case "Base"   => Base.asRight
        case "Medium" => Medium.asRight
        case "High"   => High.asRight
        case _        => "Invalid range.".asLeft
      }

  }

  type Weight = Double Refined Interval.Closed[0.0, 1000.0]

  object Weight
      extends RefinedTypeOps[Weight, Double]
      with CatsRefinedTypeOpsSyntax

  type Reps = Int Refined Interval.Closed[0, 150]

  object Reps extends RefinedTypeOps[Reps, Int] with CatsRefinedTypeOpsSyntax

}
