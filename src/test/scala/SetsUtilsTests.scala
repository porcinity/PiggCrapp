import io.porcinity.piggcrapp.Domain.Sets.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues

import java.util.UUID
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.porcinity.piggcrapp.Domain.Exercise.ExerciseId

class SetsUtilsTests extends UnitSpec {

  val exerciseId = ExerciseId.unsafeFrom(NanoIdUtils.randomNanoId)

  "Weights" should {
    "not be created with invalid input" in {
      assert(Weight.from(1500.00).isLeft)
      assert(Weight.from(-10).isLeft)
    }
  }
  "be created with valid input" in {
    assert(Weight.from(1000).isRight)
    assert(Weight.from(400.55).isRight)
    assert(Weight.from(0).isRight)
  }

  "Reps" should {
    "not be created with invalid input" in {
      assert(Reps.from(155).isLeft)
      assert(Reps.from(-1).isLeft)
    }
    "should be created with valid input" in {
      assert(Reps.from(15).isRight)
      assert(Reps.from(0).isRight)
      assert(Reps.from(150).isRight)
    }
  }

  "A RegularSet" should {
    "not be created with invalid weight" in {
      val dto = AddRegularSet(-5.0, 1300)
      val result = AddRegularSet.toDomain(dto, exerciseId)

      assert(result.isLeft)
    }
    "be created with valid reps" in {
      val dto = AddRegularSet(400.0, 30)
      val result = AddRegularSet.toDomain(dto, exerciseId)

      assert(result.isRight)
      inside(result) { case Right(set) =>
        set.weight.value == 400.0 && set.reps.value == 30
      }
    }
  }

  "A RestPause set" should {
    "be created with valid input" in {
      val data = AddRestPauseSet("High", 300.0)
      val result = RestPauseSet.create(data, exerciseId)
      assert(result.isRight)
      inside(result) { case Right(rp) =>
        rp.range == RestPauseRange.High && rp.weight.value == 300.0
      }
    }
    "not be created with invalid input" in {
      val data = AddRestPauseSet("Bad", -8.0)
      val result = RestPauseSet.create(data, exerciseId)
      assert(result.isLeft)
    }

    "be able to add valid Rest Pause sets" in {
      val data = AddRestPauseSet("Base", 100.0)
      val set = RestPauseSet.create(data, exerciseId)
      val reps = Reps.from(10)

      val result = for {
        r <- reps
        s <- set
        res <- s.addReps(r)
      } yield res

      assert(result.isRight)
      inside(result) { case Right(rp) => rp.restPauseSets.length == 1 }
    }

    "not be able to add more than 3 valid RestPause sets" in {
      val reps = List(
        Reps.unsafeFrom(12),
        Reps.unsafeFrom(10),
        Reps.unsafeFrom(5)
      )
      val set =
        RestPauseSet(
          RestPauseSetId.unsafeFrom(NanoIdUtils.randomNanoId),
          RestPauseRange.Base,
          Weight.unsafeFrom(100.0),
          reps,
          exerciseId
        )

      val result = set.addReps(Reps.unsafeFrom(12))

      assert(result.isLeft)
      assert(result == Left("All Rest Pause sets are completed."))
    }
  }
}
