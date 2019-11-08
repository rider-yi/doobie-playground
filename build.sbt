import Dependencies._

ThisBuild / scalaVersion := "2.12.10"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(
    name := "doobie-playground",
    libraryDependencies ++= Seq(
      doobieCore,
      doobieHikari,
      doobieRefined,
      enumeratumDoobie,
      mysqlConnectorJava
    )
  )
