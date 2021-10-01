package uol

import zio.{Chunk, ZIO}
import zio.test.*
import zio.test.Assertion.*
import uol.Zombies

object ZombieSpec extends DefaultRunnableSpec:
  //bla one yak
  //bla tow yak

  override def spec = suite("zombies") {
    test("z") {
      for 
        zombies <- Zombies.queueAndWork
      yield assertTrue(zombies == "zombies in the background running at you")
    }
  }

object PlantSpec extends DefaultRunnableSpec:

  override def spec = suite("plants") {
    test("p") {
      for 
        plants <- PlantsAndZombies.plants
      yield assertTrue(plants == "here's to a future zombie free and plant teeming world")
    }
  }
