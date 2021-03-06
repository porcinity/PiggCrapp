package io.porcinity.piggcrapp.Api

import cats.Monad
import cats.syntax.all.*
import io.porcinity.piggcrapp.Persistence.Users
import io.porcinity.piggcrapp.Domain.User.{User, UserDto}
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import io.porcinity.piggcrapp.Domain.User.UserId
import io.porcinity.piggcrapp.Api.UserIdVar

class UsersRoutes[F[_]: JsonDecoder: Monad](
    repository: Users[F]
) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case GET -> Root =>
      for {
        users <- repository.findAllUsers
        res <- Ok(users)
      } yield res

    case GET -> Root / UserIdVar(id) =>
      for {
        u <- repository.findUserById(id)
        res <- u.fold(NotFound())(x => Ok(x))
      } yield res

    case req @ POST -> Root =>
      for {
        dto <- req.asJsonDecode[UserDto]
        u <- UserDto.toDomain(dto).pure[F]
        res <- u.fold(
          e => UnprocessableEntity(e),
          x => Ok(repository.create(x))
        )
      } yield res

    case req @ PUT -> Root / UserIdVar(id) =>
      for {
        dto <- req.asJsonDecode[UserDto]
        user <- repository.findUserById(id)
        res <- user.fold(NotFound())(u => {
          // implement update function
          val domainUser = UserDto.toDomain(dto)
          domainUser.fold(
            e => UnprocessableEntity(e),
            x => {
              val updatedUser =
                u.copy(
                  userName = x.userName,
                  age = x.age,
                  weight = x.weight
                )
              Created(repository.update(updatedUser))
            }
          )
        })
      } yield res

    case DELETE -> Root / UserIdVar(id) =>
      for {
        d <- repository.delete(id)
        res <- d.fold(NotFound())(_ => NoContent())
      } yield res
  }

}
