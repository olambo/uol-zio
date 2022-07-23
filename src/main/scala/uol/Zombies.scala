package uol

import zio.*
import zio.Console.*

object Zombies: 

  private def work(id: String, sleeptime: Duration)(n: Int): URIO[Has[Clock] with Has[Console], Unit] =
    Console.printLine(s"hunter $id tracking zombie $n").orDie *>
      ZIO.sleep(sleeptime) *>
      Console.printLine(s"hunter $id eliminated zombie $n. It took $sleeptime").orDie

  def queueAndWork: ZIO[Has[Clock] & Has[Console], Nothing, String] =
    for
      queue <- Queue.unbounded[Int]
      _ <- queue.take.flatMap(work("joe", 1.milli)).forever.fork
      _ <- queue.take.flatMap(work("sue", 3.milli)).forever.fork
      _ <- queue.take.flatMap(work("alf", 1.second)).forever.fork
      _ <- ZIO.foreachDiscard(1 to 5)(queue.offer)
      zb <- ZIO.succeed("Zombies in the background stomping through the crop, trying to get at the camp.")
    yield zb
