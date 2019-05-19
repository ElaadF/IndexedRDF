import IO.Reader
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD._
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD
import org.apache.spark.util.SizeEstimator
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD


object Main extends App {
  val conf = new SparkConf().setAppName("simpleSparkApp").setMaster("local")
  val sc = new SparkContext(conf)
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.OFF)
  val rdd = sc.textFile("resources/data/LiteMat/lubm1.ttl")
  val str = "<http://www.Department0.University0.edu/FullProfessor0><http://swat.cse.lehigh.edu/onto/univ-bench.owl#researchInterest>"

  val triple = Reader.extract(rdd)
  //triple.foreach(print(_))


  val o_ps: RDD[(String, String)] = triple.map({case(s,p,o) => (o, p+s)})
  val p_sp: RDD[(String, String)] = triple.map({case(s,p,o) => (p, s+p)})
  val s_po: RDD[(String, String)] = triple.map({case(s,p,o) => (s, p+o)})
  val sp_o: RDD[(String, String)] = triple.map({case(s,p,o) => (s+p, o)})
  val op_s: RDD[(String, String)] = triple.map({case(s,p,o) => (o+p, s)})
  val so_p: RDD[(String, String)] = triple.map({case(s,p,o) => (s+o, p)})
  val spo: RDD[(String, String)] = triple.map({case(s,p,o) => (s+o+p, "None")})

  val combinationToIndex = o_ps ++ p_sp ++ s_po ++ sp_o ++ op_s ++ so_p
  //indexRDF.foreach(x => println(x+"\n"))

  val indexed = IndexedRDD(combinationToIndex).cache()

  val classicSearch = Reader.time { Reader.search(triple, str) }
  val indexedSearch = Reader.time { indexed.get(str) }

  println(indexed.get(str))
  println("\n\n\n Disk use : " + (SizeEstimator.estimate(indexed) / 1000000.0) + " Mb")
}
