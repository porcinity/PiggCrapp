package io.porcinity.piggcrapp.Api

import org.http4s.circe.JsonDecoder
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.*
import io.porcinity.piggcrapp.Persistence.Exercises
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import cats.Monad
import cats.syntax.all.*
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import io.porcinity.piggcrapp.Domain.Exercise.*

class ExerciseRoutes[F[_]: Monad: JsonDecoder](repository: Exercises[F])
    extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case GET -> Root / "workouts" / WorkoutIdVar(workoutId) / "exercises" =>
      for {
        exercises <- repository.findAllExercises(workoutId)
        res <- Ok(exercises)
      } yield res

    case GET -> Root / "exercises" / ExerciseIdVar(exerciseId) =>
      for {
        e <- repository.findSingleExercise(exerciseId)
        res <- e.fold(NotFound())(x => Ok(x))
      } yield res

    case req @ POST -> Root / "workouts" / WorkoutIdVar(
          workoutId
        ) / "exercises" =>
      for {
        dto <- req.asJsonDecode[ExerciseData]
        w <- Exercise.create(dto, workoutId).pure[F]
        res <- w.fold(
          e => UnprocessableEntity(e),
          x => Ok(repository.insertExercise(x))
        )
      } yield res

    case req @ PUT -> Root / "exercises" / ExerciseIdVar(exerciseId) =>
      for {
        dto <- req.asJsonDecode[ExerciseData]
        data <- ExerciseName.from(dto.name).pure[F]
        dbExercise <- repository.findSingleExercise(exerciseId)
        res <- (dbExercise, data) match {
          case (Some(exercise), Right(info)) => {
            val updatedExercise =
              exercise.copy(exerciseName = ExerciseName.unsafeFrom(info.value))
            val dbSave =
              repository.updateExercise(exercise.workoutId, updatedExercise)
            Ok(dbSave)
          }
          case _ => BadRequest()
        }
      } yield res

    case DELETE -> Root / "exercises" / ExerciseIdVar(exerciseId) =>
      for {
        d <- repository.deleteExercise(exerciseId)
        res <- d.fold(NotFound())(_ => NoContent())
      } yield res

  }

}
