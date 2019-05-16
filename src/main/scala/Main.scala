import IO.Reader
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD._
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD
import org.apache.spark.util.SizeEstimator
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object Main extends App {
  val conf = new SparkConf().setAppName("simpleSparkApp").setMaster("local")
  val sc = new SparkContext(conf)
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.OFF)
  val rdd = sc.textFile("resources/data/LiteMat/lubm1.ttl")
  val str = "http://swat.cse.lehigh.edu/onto/univ-bench.owl#takesCourse"

  val triple = Reader.extract(rdd)
  //triple.foreach(print(_))

  val indexRDF = triple.map({case(s,p,o) => (s+p, o)})
  //indexRDF.foreach(x => println(x+"\n"))

  val indexed = IndexedRDD(indexRDF).cache()

  val classicSearch = Reader.time { Reader.search(triple, str) }
  val indexedSearch = Reader.time { Reader.searchIndex(indexed, str) }


  println(triple.first().toString())
  println(indexed.first().toString())
//  println(indexed.get(indexRDF.first()._1.toString))


  println("\n\n\n Disk use : " + (SizeEstimator.estimate(indexed) / 1000000.0) + " Mb")
}
