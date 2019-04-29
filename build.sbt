name := "IndexedRDF"
version := "1.0"
scalaVersion := "2.10.6"

val sparkVersion = "1.6.0"
val indexedRDDversion = "0.3"
val scalachekVersion = "1.14.0"

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"
resolvers += "Repo at github.com/ankurdave/maven-repo" at "https://github.com/ankurdave/maven-repo/raw/master"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "amplab" % "spark-indexedrdd" % indexedRDDversion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "com.ankurdave" %% "part" % "0.1",
  "org.scalacheck" %% "scalacheck" % scalachekVersion % "test",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.apache.jena" % "jena" % "3.10.0" pomOnly()

)

