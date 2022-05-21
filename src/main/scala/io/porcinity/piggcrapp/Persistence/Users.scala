package io.porcinity.piggcrapp.Persistence

import io.porcinity.piggcrapp.Domain.User.User
import io.porcinity.piggcrapp.Domain.User.UserId

trait Users[F[_]] {
  def findAllUsers: F[List[User]]
  def findUserById(id: UserId): F[Option[User]]
  def create(user: User): F[User]
  def update(user: User): F[User]
  def delete(user: UserId): F[Option[User]]
}

