import zio._
val goShopping = ZIO.attempt(println("Going to the grocery store, sometime"))
Runtime.default.unsafeRun(goShopping)
Runnable