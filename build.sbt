name := """commentapi"""
organization := "maciej"

version := "1.0"

maintainer := "Maciej Szklarzewski <szklarzewski.maciej@gmail.com>"
maintainer in Debian := "Maciej Szklarzewski <szklarzewski.maciej@gmail.com>"
maintainer in Linux := "Maciej Szklarzewski <szklarzewski.maciej@gmail.com>"

packageSummary := "Comments REST API with Play"
packageDescription := """Comments REST API with Play"""

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(UniversalPlugin)
  .enablePlugins(DockerPlugin)

scalaVersion := "2.13.2"

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "." + artifact.extension
}

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.2"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.14"

routesImport += "constants.SortingOrder.Order"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "maciej.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "maciej.binders._"
