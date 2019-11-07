package example.schema.values

import enumeratum.values.{StringDoobieEnum, StringEnum, StringEnumEntry}

/**
  * 銘柄
  */
sealed abstract class Symbol(val value: String) extends StringEnumEntry

object Symbol extends StringEnum[Symbol] with StringDoobieEnum[Symbol] {
  case object BND extends Symbol("BND")
  case object EMB extends Symbol("EMB")
  case object GLD extends Symbol("GLD")

  val values = findValues
}
