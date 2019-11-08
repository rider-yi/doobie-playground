import sbt._

object Dependencies {
  lazy val doobieCore = "org.tpolecat" %% "doobie-core" % "0.8.4"
  lazy val doobieHikari = "org.tpolecat" %% "doobie-hikari" % "0.8.4"
  lazy val doobieRefined = "org.tpolecat" %% "doobie-refined" % "0.8.4"
  lazy val enumeratumDoobie = "com.beachape" %% "enumeratum-doobie" % "1.5.15"
  lazy val mysqlConnectorJava = "mysql" % "mysql-connector-java" % "8.0.18"
}
