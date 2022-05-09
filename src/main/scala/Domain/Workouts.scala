package Domain

import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID
import eu.timepit.refined.types.string.NonEmptyFiniteString
import io.circe.Codec
import eu.timepit.refined.api._
import eu.timepit.refined.cats.CatsRefinedTypeOpsSyntax
import org.latestbit.circe.adt.codec.JsonTaggedAdt

object Workout {

  case class Workout(
      workoutId: WorkoutId,
      date: LocalDate,
      variation: WorkoutVariation,
      exercises: List[Exercise],
      owner: UserId
  ) derives Codec.AsObject

  type WorkoutId = NonEmptyFiniteString[30]

  object WorkoutId
      extends RefinedTypeOps[WorkoutId, String]
      with CatsRefinedTypeOpsSyntax

  enum WorkoutVariation
      derives JsonTaggedAdt.PureEncoder,
        JsonTaggedAdt.PureDecoder {
    case UpperA, UpperB, UpperC, LowerA, LowerB, LowerC
  }

  type UserId = NonEmptyFiniteString[20]

  object UserId
      extends RefinedTypeOps[UserId, String]
      with CatsRefinedTypeOpsSyntax
}
