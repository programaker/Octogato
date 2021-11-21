package octogato.effect

import cats.effect.kernel.Resource
import cats.effect.kernel.MonadCancel

object syntax:
  extension [F[_], A](res: Resource[F, A])(using MonadCancel[F, Throwable])
    def useAsGiven[B](f: A ?=> F[B]): F[B] = res.use { a =>
      given A = a
      f
    }
