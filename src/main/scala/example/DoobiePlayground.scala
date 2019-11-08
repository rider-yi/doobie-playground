package example

import cats.data.NonEmptyList
import cats.effect.{Blocker, IO, Resource}
import doobie.free.connection._
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.ExecutionContexts
import eu.timepit.refined.auto._
import example.dao.OrderDao
import example.schema.Order
import example.schema.values._

object DoobiePlayground extends App {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  // Resource yielding a transactor configured with a bounded connect EC and an unbounded
  // transaction EC. Everything will be closed and shut down cleanly after use.
  val transactor: Resource[IO, HikariTransactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      be <- Blocker[IO] // our blocking EC
      xa <- HikariTransactor.newHikariTransactor[IO](
        "com.mysql.cj.jdbc.Driver", // driver classname
        "jdbc:mysql://localhost:3306/playground", // connect URL
        "root", // username
        "", // password
        ce, // await connection here
        be // execute JDBC operations here
      )
    } yield xa

  val order1 = Order(OrderId.generate, Symbol.BND, Side.Buy, Quantity(BigDecimal(1.5)))
  val order2 = Order(OrderId.generate, Symbol.EMB, Side.Buy, Quantity(BigDecimal(5)))
  val order3 = Order(OrderId.generate, Symbol.GLD, Side.Sell, Quantity(BigDecimal(2.5)))

  val cio: ConnectionIO[NonEmptyList[Order]] = for {
    _ <- OrderDao.create(order1) // insert
    _ <- OrderDao.batchCreate(NonEmptyList.of(order2, order3)) // batch insert
    _ <- IO(println("3 orders created!!")).to[ConnectionIO] // IO ~> ConnectionIO
    x <- OrderDao.findBy(order1.id)
    ys <- OrderDao.selectNelBy(NonEmptyList.of(order2.id, order3.id))
  } yield {
    x :: ys
  }

  // ConnectionIO ~> IO
  val io: IO[NonEmptyList[Order]] = transactor.use { xa =>
    cio.transact(xa) // 全てのDBアクセスを1トランザクションで行う
  }

  println(io.unsafeRunSync())
}
