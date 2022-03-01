package Domain

import java.time.*

import java.util.UUID

case class User(userId: UserId,
                userName: UserName,
                age: Age,
                weight: UserWeight,
                createdDate: LocalDate )

opaque type UserId = UUID

opaque type UserName = String

object UserName:
  def apply(value: String): Either[String, UserName] = value match
    case TooLongName() => Left("Name cannot be over 100 characters.")
    case TooShortName() => Left("Name cannot be fewer than 5 characters.")
    case NumbersOrCharsName() => Left("Name cannot contain numbers or special characters.")
    case Empty() => Left("Name cannot be empty.")
    case _ => Right(value)

opaque type Age = Int

object Age:
  def apply(age: Int): Either[String, Age] = age match
    case TooOld() => Left("Age cannot be greater than 100.")
    case TooYoung() => Left("Age must be greater than 18.")
    case _ => Right(age)

opaque type UserWeight = Double

object UserWeight:
  def apply(weight: Double): Either[String, UserWeight] = weight match
    case TooHeavyUser() => Left("Weight must be less than 400lbs.")
    case TooLightUser() => Left("Weight cannot be less than 70lbs.")
    case _ => Right(weight)

// Name extractors
object TooShortName:
  def unapply(x: String): Boolean = x.length < 5 && x.nonEmpty

object TooLongName:
  def unapply(x: String): Boolean = x.length > 100

object NumbersOrCharsName:
  def unapply(x: String): Boolean = !x.matches("^[a-zA-Z]+$")

// Age extractors
object TooOld:
  def unapply(x: Int): Boolean = x > 100

object TooYoung:
  def unapply(x: Int): Boolean = x < 18

// Weight extractors
object TooHeavyUser:
  def unapply(x: Double): Boolean = x > 400

object TooLightUser:
  def unapply(x: Double): Boolean = x < 70