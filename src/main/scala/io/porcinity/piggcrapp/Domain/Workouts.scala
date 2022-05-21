package io.porcinity.piggcrapp.Domain

import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID
import eu.timepit.refined.types.string.NonEmptyFiniteString
import io.circe.Codec
import io.circe.refined.*
import eu.timepit.refined.api._
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import org.latestbit.circe.adt.codec.JsonTaggedAdt.{PureEncoder, PureDecoder}
import eu.timepit.refined.types.string.NonEmptyString
import cats.data.*
import cats.syntax.all.*
import User.UserId
import com.aventrix.jnanoid.jnanoid.NanoIdUtils

object Workout {

  final case class Workout(
      workoutId: WorkoutId,
      date: WorkoutDate,
      variation: WorkoutVariation,
      // exercises: List[Exercise],
      user: User.UserId
  ) derives Codec.AsObject

  type WorkoutId = NonEmptyString

  object WorkoutId
      extends RefinedTypeOps[WorkoutId, String]
      with CatsRefinedTypeOpsSyntax

  enum WorkoutVariation derives PureEncoder, PureDecoder {
    case UpperA, UpperB, UpperC, LowerA, LowerB, LowerC
  }

  object WorkoutVariation {

    def fromString(
        variation: String
    ): Either[NonEmptyChain[String], WorkoutVariation] = variation match {
      case "UpperA" => UpperA.asRight
      case "UpperB" => UpperB.asRight
      case "UpperC" => UpperC.asRight
      case "LowerA" => LowerA.asRight
      case "LowerB" => LowerB.asRight
      case "LowerC" => LowerC.asRight
      case _        => "Invalid variation".leftNec
    }

  }

  type WorkoutUser = NonEmptyFiniteString[20]

  object WorkoutUser
      extends RefinedTypeOps[WorkoutUser, String]
      with CatsRefinedTypeOpsSyntax

  type WorkoutDate = LocalDate

  object WorkoutDate {

    def apply(date: LocalDate): Either[NonEmptyChain[String], WorkoutDate] =
      Right(date)

  }

  final case class WorkoutDto(date: LocalDate, variation: String)

  object WorkoutDto {

    def toDomain(
        dto: WorkoutDto,
        userId: String
    ): Either[NonEmptyChain[String], Workout] = {
      val id = NanoIdUtils.randomNanoId

      (
        WorkoutId.from(id).toEitherNec,
        WorkoutDate(dto.date),
        WorkoutVariation.fromString(dto.variation),
        UserId.from(userId).toEitherNec
      ).parMapN(Workout.apply)
    }

  }

}
