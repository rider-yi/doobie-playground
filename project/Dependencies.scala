import sbt._

object Dependencies {
  lazy val doobieCore = "org.tpolecat" %% "doobie-core" % "0.8.4"
  lazy val doobieRefined = "org.tpolecat" %% "doobie-refined" % "0.8.4"
  lazy val enumeratumDoobie = "com.beachape" %% "enumeratum-doobie" % "1.5.15"
  lazy val mysqlConnectorJava = "mysql" % "mysql-connector-java" % "8.0.18"
  lazy val twitterUtilCore = "com.twitter" %% "util-core" % "19.10.0"
}
