package octogato.app

import cats.Show
import cats.effect.IO
import cats.syntax.show.*
import octogato.common.ApiHost
import octogato.common.Token

opaque type CommandResult = String
object CommandResult:
  inline def make[T: Show](t: T): CommandResult = t.show
  given (using Show[String]): Show[CommandResult] = summon

opaque type CommandError = String
object CommandError:
  inline def make[E <: Throwable](e: E): CommandError = e.getMessage
  given (using Show[String]): Show[CommandError] = summon

type CommandFn = (ApiHost, Token) => IO[Either[CommandError, CommandResult]]
