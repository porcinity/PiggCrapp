package io.porcinity.piggcrapp.Api

import cats.Monad
import cats.syntax.all.*
import io.porcinity.piggcrapp.Persistence.Users
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import io.porcinity.piggcrapp.Domain.User.UserId

class UsersRoutes[F[_]: JsonDecoder: Monad](
    repository: Users[F]
) extends Http4sDsl[F] {

  object UserIdVar {
    def unapply(str: String): Option[UserId] = Some(UserId.unsafeFrom(str))
  }

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

  }

}
