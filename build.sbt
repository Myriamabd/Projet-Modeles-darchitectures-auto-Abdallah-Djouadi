name := "spark1"

version := "0.1"

scalaVersion := "2.11.11"

idePackagePrefix := Some("fr.episen.dataprocessing")

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.8"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.8"
libraryDependencies += "com.github.scopt" %% "scopt" % "4.0.1"
