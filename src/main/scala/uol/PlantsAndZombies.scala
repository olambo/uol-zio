package uol

import zio.Console.*
import zio.*

import java.io.IOError
import java.io.IOException
import scala.concurrent.Future

import Zombies.*

object PlantsAndZombies extends ZIOAppDefault:
  private val fut = ZIO.fromFuture(Future("All Zombies dealt with!\nHere's to a future"))

  val plants: ZIO[Has[Clock] & Has[Console], Throwable, String] =
    fut
      .zip(ZIO.succeed("Zombie free and Plant teeming world."))
      .map((l, r) => s"$l $r") <* Clock.currentDateTime.flatMap { t => printLine(t.toLocalDate.toString) }

  def run =
    val r = plants
      <* Zombies.queueAndWork.tap(printLine(_))
      <* printLine("Give the hunters time to work.") <* ZIO.sleep(2.seconds)
    r.tap(zb => printLine(s"$zb")).exitCode
