package io.porcinity.piggcrapp.Domain

import Workout.Workout
import cats.data.*
import cats.syntax.all.*
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import eu.timepit.refined.api._
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import eu.timepit.refined.types.numeric.NonNegInt
import eu.timepit.refined.types.string.NonEmptyFiniteString
import io.circe.Codec
import io.circe.refined._

import java.time.*

object User {

  final case class User(
      userId: UserId,
      userName: UserName,
      age: Age,
      // workouts: List[Workout],
      weight: UserWeight,
      createdDate: CreatedDate
  ) derives Codec.AsObject

  type UserId = NonEmptyFiniteString[30]

  object UserId
      extends RefinedTypeOps[UserId, String]
      with CatsRefinedTypeOpsSyntax

  type UserName = NonEmptyFiniteString[20]

  object UserName
      extends RefinedTypeOps[UserName, String]
      with CatsRefinedTypeOpsSyntax

  type Age = NonNegInt

  object Age extends RefinedTypeOps[Age, Int] with CatsRefinedTypeOpsSyntax

  type UserWeight = NonNegInt

  object UserWeight
      extends RefinedTypeOps[UserWeight, Int]
      with CatsRefinedTypeOpsSyntax

  opaque type CreatedDate = LocalDate

  object CreatedDate {

    def apply(date: LocalDate): EitherNec[String, CreatedDate] = date.rightNec

  }

  final case class UserDto(
      name: String,
      age: Int,
      weight: Int
  ) derives Codec.AsObject

  object UserDto {

    def toDomain(dto: UserDto): Either[NonEmptyChain[String], User] = {
      val id = NanoIdUtils.randomNanoId()
      val date = CreatedDate(LocalDate.now)

      (
        UserId.from(id).toEitherNec,
        UserName
          .from(dto.name)
          .leftMap(_ => "Username must be 30 characters or fewer.")
          .toEitherNec,
        Age
          .from(dto.age)
          .leftMap(_ => "User's age must be at least 18.")
          .toEitherNec,
        UserWeight
          .from(dto.weight)
          .leftMap(_ => "Weight cannot be a negative number.")
          .toEitherNec,
        date
      ).parMapN(User.apply)
    }

  }

}
