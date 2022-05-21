package Persistence

import Domain.User.*

trait Users[F[_]] {
  def findAllUsers: F[List[User]]
  def findUserById: F[User]
  def create(user: User): F[User]
  def update(user: User): F[User]
  def delete(user: User): F[Option[User]]
}

