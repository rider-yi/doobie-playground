package example.schema.values

import java.util.UUID

import eu.timepit.refined.api.Refined
import eu.timepit.refined.refineV
import eu.timepit.refined.string.Uuid

/**
  * 注文ID
  */
case class OrderId(value: String Refined OrderId.Spec)

object OrderId {
  type Spec = Uuid

  def generate: OrderId = {
    val uuid = UUID.randomUUID.toString
    OrderId(refineV[Spec].unsafeFrom(uuid))
  }
}
