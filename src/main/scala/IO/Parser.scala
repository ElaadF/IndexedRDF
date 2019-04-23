package IO

import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD._
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source
import scala.util.Try


object Parser {
  /** Read a file using a path
    *
    *  @param path path's file
    *  @return a list of lines of the file
    */
  def read(path: String): Try[List[String]] = {
    Try(Source.fromFile(path).getLines.toList)
  }

  def extract(data: RDD[String]): RDD[(String, String, String)] = {
    data.map(line => {
     val tab: Array[String] = line.split(" ")
     (tab(0), tab(1), tab(2))
   })
  }
}

object HelloWorld {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("simpleSparkApp").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("resources/data/LiteMat/lubm1.ttl")

    val triple = Parser.extract(rdd)
    //triple.foreach(print(_))

    val indexRDF = triple.map({case(s,p,o) => (s+p, o)})
    //indexRDF.foreach(x => println(x+"\n"))

    val indexed = IndexedRDD(indexRDF).cache()
    println(indexed.get(indexRDF.first()._1.toString))
  }
}