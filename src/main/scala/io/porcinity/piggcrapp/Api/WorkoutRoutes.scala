package io.porcinity.piggcrapp.Api

import org.http4s.circe.JsonDecoder
import io.porcinity.piggcrapp.Persistence.Workouts
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import cats.Monad
import cats.syntax.all.*
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import io.porcinity.piggcrapp.Api.UserIdVar

class WorkoutRoutes[F[_]: JsonDecoder: Monad](repository: Workouts[F])
    extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case GET -> Root / "users"/ UserIdVar(userId) / "workouts" =>
      for {
        workouts <- repository.findAllWorkouts(userId)
        res <- Ok(workouts)
      } yield res

    case GET -> Root / "workouts" / WorkoutIdVar(workoutId) =>
      for {
        workout <- repository.findSingleWorkout(workoutId)
        res <- workout.fold(NotFound())(x => Ok(x))
      } yield res
    
  }

}
