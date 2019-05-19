import java.util.UUID

import IO.Reader
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD._
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD
import org.apache.spark.util.SizeEstimator
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD



object Main extends App {
  val getStr = Reader.mapLongToStr("resources/data/LiteMat/lubm1.ttl")
  val getId = Reader.mapStrToLong("resources/data/LiteMat/lubm1.ttl")


  val conf = new SparkConf().setAppName("simpleSparkApp").setMaster("local")
  val sc = new SparkContext(conf)
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.OFF)

  val rdd = sc.textFile("resources/data/LiteMat/lubm1.ttl")
  val str = "<http://www.Department0.University0.edu/FullProfessor0><http://swat.cse.lehigh.edu/onto/univ-bench.owl#researchInterest>"

  val triple = Reader.extract(rdd)

  val finalmap = triple.map({case(s,p,o) => (getId(s), getId(o))})
  //triple.foreach(print(_))

  val o_ps: RDD[(String, String)] = triple.map({case(s,p,o) => (o, p+s)})
  val p_so: RDD[(String, String)] = triple.map({case(s,p,o) => (p, s+o)})
  val s_po: RDD[(String, String)] = triple.map({case(s,p,o) => (s, p+o)})
  val sp_o: RDD[(String, String)] = triple.map({case(s,p,o) => (s+p, o)})
  val op_s: RDD[(String, String)] = triple.map({case(s,p,o) => (o+p, s)})
  val so_p: RDD[(String, String)] = triple.map({case(s,p,o) => (s+o, p)})
  val spo: RDD[(String, String)] = triple.map({case(s,p,o) => (s+o+p, "None")})

  val combinationToIndex = sc.union(List(o_ps,p_so, s_po, sp_o, op_s, so_p, spo ))
  val id = getId(str)
  println( "id : " + id)
  println("str : " + getStr(id))
  val test = getStr.map({case(a,b) => (a, getId(b))})
//  println(test("<http://www.Department0.University0.edu/FullProfessor0>"))
  //indexRDF.foreach(x => println(x+"\n"))
  val lol = sc.parallelize(test.toList)

  val indexed = IndexedRDD(combinationToIndex).cache()
  val indexed_sp = IndexedRDD(finalmap).cache()

var avrg: Long= 0
  for( a <- 0 to 800){
    avrg = avrg + Reader.time { indexed_sp.get(24) }
  }

  println("Moyenne : " + avrg/800)

//  println(getStr(indexed_sp.get(24).get))

  println("Disk use : " + (SizeEstimator.estimate(indexed_sp) / 1000000.0) + " Mb")

}
