package uol

import scala.concurrent.Future
import zio.*
import zio.Console.*
import Zombies.*

object PlantsAndZombies extends ZIOAppDefault:
  private val fut = ZIO.fromFuture(Future("here's to a future"))
  val plants: ZIO[Has[Clock] & Has[Console], Throwable, String] = fut
    .zip(ZIO.succeed("zombie free and plant teeming world"))
    .map((l, r) => s"$l $r")
    .tap(printLine(_)) <* Clock.currentDateTime.flatMap { t => printLine(t.toString) }

  def run =
    (plants *> Zombies.queueAndWork.tap(zb => printLine(s"zb:$zb")) ).exitCode
