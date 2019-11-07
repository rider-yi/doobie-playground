package example.schema

import example.schema.values._

/**
  * 注文テーブル
  */
case class Order(
    id: OrderId,
    symbol: Symbol,
    side: Side,
    quantity: Quantity
)
