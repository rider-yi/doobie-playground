package example.dao

import cats.data.NonEmptyList
import cats.effect.IO
import com.twitter.util.Future
import doobie._
import doobie.implicits._
import doobie.refined.implicits._
import doobie.util.transactor.Transactor
import example.EnrichIO._
import example.schema.Order
import example.schema.values.OrderId

class OrderDao(implicit xa: Transactor[IO]) {
  def create(order: Order): Future[Unit] = {
    import order._
    val q = sql"""|INSERT INTO `order` (order_id, symbol, side, quantity)
                  |VALUES (${id}, ${symbol}, ${side}, ${quantity})"""
    q.stripMargin.update.run
      .transact(xa)
      .runToFuture
      .unit
  }

  def findBy(orderId: OrderId): Future[Order] = {
    sql"SELECT order_id, symbol, side, quantity FROM `order` WHERE order_id = ${orderId}"
      .query[Order]
      .unique
      .transact(xa)
      .runToFuture
  }

  def selectBy(orderIds: NonEmptyList[OrderId]): Future[List[Order]] = {
    val q =
      fr"SELECT order_id, symbol, side, quantity FROM `order` WHERE" ++
        Fragments.in(fr"order_id", orderIds) ++ fr"ORDER BY order_id"
    q.query[Order]
      .to[List]
      .transact(xa)
      .runToFuture // .to[List] の代わりに .nel, .stream (fs2のStream)などを使うこともできる
  }
}
