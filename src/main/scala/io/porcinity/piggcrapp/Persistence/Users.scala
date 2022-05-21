package io.porcinity.piggcrapp.Persistence

import io.porcinity.piggcrapp.Domain.User.User

trait Users[F[_]] {
  def findAllUsers: F[List[User]]
  def findUserById: F[User]
  def create(user: User): F[User]
  def update(user: User): F[User]
  def delete(user: User): F[Option[User]]
}

