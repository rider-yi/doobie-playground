package schema.values

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Positive

/**
  * 発注数量
  */
case class Quantity(value: BigDecimal Refined Quantity.Spec)

object Quantity {
  type Spec = Positive // 発注数量は常に0より大きい数である
}
