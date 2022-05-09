package Domain

import eu.timepit.refined.api._
import java.time.*

import java.util.UUID
import eu.timepit.refined.types.string.NonEmptyFiniteString
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import eu.timepit.refined.types.numeric.NonNegInt
import Domain.Workout.Workout
import io.circe.refined._
import io.circe.Codec

object User {

  final case class User(
      userId: UserId,
      userName: UserName,
      age: Age,
      workouts: List[Workout],
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

  type CreatedDate = LocalDate
}
