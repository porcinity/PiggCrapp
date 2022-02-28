package Domain

import java.util.UUID
import java.util.UUID.randomUUID

case class User(userId: UserId, userName: UserName, age: Age, weight: Weight)

opaque type UserId = UUID

opaque type UserName = String

object UserName:
  def apply(value: String): Either[String, UserName] = value match
    case TooLong() => Left("Name cannot be over 100 characters.")
    case TooShort() => Left("Name cannot be fewer than 5 characters.")
    case NumbersOrChars() => Left("Name cannot contain numbers or special characters.")
    case Empty() => Left("Name cannot be empty.")
    case _ => Right(value)

opaque type Age = Int

object Age:
  def apply(value: Int): Either[String, Age] = value match
    case TooOld() => Left("Age cannot be greater than 100.")
    case TooYoung() => Left("Age must be greater than 18.")
    case _ => Right(value)

opaque type Weight = Double

object Weight:
  def apply(value: Double): Either[String, Weight] = value match
    case TooHeavy() => Left("Weight must be less than 400lbs.")
    case TooLight() => Left("Weight cannot be less than 70lbs.")
    case _ => Right(value)

// Name extractors
object TooShort:
  def unapply(x: String): Boolean = x.length < 5 && x.nonEmpty

object TooLong:
  def unapply(x: String): Boolean = x.length > 100

object NumbersOrChars:
  def unapply(x: String): Boolean = !x.matches("^[a-zA-Z]+$")

object Empty:
  def unapply(x: String): Boolean = x.isEmpty

// Age extractors
object TooOld:
  def unapply(x: Int): Boolean = x > 100

object TooYoung:
  def unapply(x: Int): Boolean = x < 18

// Weight extractors
object TooHeavy:
  def unapply(x: Double): Boolean = x > 400

object TooLight:
  def unapply(x: Double): Boolean = x < 70