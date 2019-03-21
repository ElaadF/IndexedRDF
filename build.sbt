name := "IndexedRDF"

scalaVersion := "2.11.8"
resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"
libraryDependencies += "amplab" % "spark-indexedrdd" % "0.3"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"
