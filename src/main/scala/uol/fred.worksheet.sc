import java.io.IOException
import zio.UIO
import zio.*
import zio.Console.*

val prints = List(
  ZIO.attempt("The").catchAll(_ => ZIO.SucceedNow("zombie")),
  ZIO.SucceedNow("sexi"),
  ZIO.SucceedNow("foxi"),
  ZIO.SucceedNow("freedom"),
  ZIO.SucceedNow("fighter"),
)

val printWords = ZIO.collectAll(prints).map(_.mkString(" "))
Runtime.default.unsafeRun(printWords)
println("hello world")

// yikes
import Cause.*
def recoverFromSomeDefects[R, E, A](zio: ZIO[R, E, A])(
    f: Throwable => Option[A]
): ZIO[R, E, A] =
  def rec(c: Cause[E]): ZIO[R, E, A] = c match
    case Die(t) => ZIO.fromOption(f(t)).orElse(zio)
    case _      => zio
  zio.foldCauseZIO(rec(_), _ => zio)

def logFailures[R, E, A](zio: ZIO[R, E, A]): ZIO[R, E, A] =
  zio.foldCauseZIO(
    c => ZIO.SucceedNow(c.prettyPrint) *> zio,
    _ => zio
  )

def ioException[R, A](
    zio: ZIO[R, Throwable, A]
): ZIO[R, java.io.IOException, A] =
  zio.refineOrDie[IOException] { case e: IOException => e }

val parseNumber: ZIO[Any, Throwable, Int] =
  ZIO.attempt("foo".toInt).refineToOrDie[java.lang.NumberFormatException]
