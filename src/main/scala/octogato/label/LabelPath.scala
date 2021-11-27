package octogato.label

import cats.Show
import cats.data.ValidatedNel
import cats.syntax.apply.*
import cats.syntax.either.*
import cats.syntax.show.*
import cats.syntax.validated.*
import com.monovore.decline.Argument
import octogato.common.NonBlankString
import octogato.common.syntax.*

case class LabelPath(
  owner: Owner,
  repo: Repo
)

object LabelPath:
  given (using Show[Owner], Show[Repo]): Show[LabelPath] =
    Show.show(path => show"${path.owner}/${path.repo}")

  given (using o: Argument[Owner], r: Argument[Repo]): Argument[LabelPath] = new:
    override def read(string: String): ValidatedNel[String, LabelPath] = string match
      case s"$owner/$repo" =>
        (o.read(owner), r.read(repo)).mapN(LabelPath.apply)
      case _ =>
        s"Invalid 'owner/repo': '$string'".invalidNel

    override def defaultMetavar: String = "<owner/repo>"
