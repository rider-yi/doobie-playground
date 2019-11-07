package example.schema.values

import enumeratum.values.{IntDoobieEnum, IntEnum, IntEnumEntry}

/**
  * Buy or Sell
  */
sealed abstract class Side(val value: Int) extends IntEnumEntry

object Side extends IntEnum[Side] with IntDoobieEnum[Side] {
  case object Buy extends Side(1)
  case object Sell extends Side(2)

  val values = findValues
}
