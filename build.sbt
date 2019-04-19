name := "IndexedRDF"
version := "1.0"
scalaVersion := "2.10.6"

val sparkVersion = "1.6.0"
val indexedRDDversion = "0.3"

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"
resolvers += "Repo at github.com/ankurdave/maven-repo" at "https://github.com/ankurdave/maven-repo/raw/master"

libraryDependencies ++= Seq(
  "amplab" % "spark-indexedrdd" % indexedRDDversion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "com.ankurdave" %% "part" % "0.1"
)
