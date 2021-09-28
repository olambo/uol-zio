package uol

import zio.*
import zio.Console.*

object Zombies:
  private def work(id: String)(n: Int): URIO[Has[Clock] with Has[Console], Unit] =
    Console.printLine(s"fiber $id starting work $n").orDie *>
      ZIO.sleep(1.millis) *> // todo: if this is 1 second, it hangs. Bug?
      Console.printLine(s"fiber $id finished with work $n").orDie

  def queueAndWork: ZIO[Has[Clock] & Has[Console], Nothing, String] = for
    queue <- Queue.unbounded[Int]
    _ <- queue.take.flatMap(work("left")).forever.fork
    _ <- queue.take.flatMap(work("right")).forever.fork
    _ <- ZIO.foreachDiscard(1 to 5)(queue.offer)
    zb <- ZIO.succeed("zombies in the background")
  yield (zb)
