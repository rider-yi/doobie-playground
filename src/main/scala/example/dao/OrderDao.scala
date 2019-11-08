package example.dao

import cats.data.NonEmptyList
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.refined.implicits._
import example.schema.Order
import example.schema.values.OrderId

object OrderDao {
  def create(order: Order): ConnectionIO[Unit] = {
    import order._
    sql"""|INSERT INTO `order` (order_id, symbol, side, quantity)
          |VALUES (${id}, ${symbol}, ${side}, ${quantity})
          |""".stripMargin.update.run.void
  }

  def batchCreate(orders: NonEmptyList[Order]): ConnectionIO[Unit] = {
    val sql = "INSERT INTO `order` (order_id, symbol, side, quantity) VALUES (?, ?, ?, ?)"
    Update[Order](sql).updateMany(orders).void
  }

  def findBy(orderId: OrderId): ConnectionIO[Order] = {
    sql"SELECT order_id, symbol, side, quantity FROM `order` WHERE order_id = ${orderId}"
      .query[Order]
      .unique
  }

  def selectNelBy(orderIds: NonEmptyList[OrderId]): ConnectionIO[NonEmptyList[Order]] = {
    val q = fr"SELECT order_id, symbol, side, quantity FROM `order` WHERE" ++
      Fragments.in(fr"order_id", orderIds) ++ fr"ORDER BY order_id"
    q.query[Order].nel // レコードが無い場合はエラーで良い場合は .nel を使う。
  }
}
