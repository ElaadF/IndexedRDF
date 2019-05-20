import IO.TripletElm.sp
import IO.{Indexes, Reader, SparkProcess}
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import sparql.Queries



object Main extends App {
  val nbRun = 150
  val path: String = "resources/data/LiteMat/lubm1.ttl"
  val conf = new SparkConf().setAppName("simpleSparkApp").setMaster("local")
  val sc = new SparkContext(conf)
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.OFF)
  val rdd: RDD[String] = sc.textFile(path)

  val s = "<http://www.Department0.University0.edu/FullProfessor0>"
  val p = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#researchInterest>"
  val o = ""
  val indexes: Indexes = Indexes(Indexes.mapStrToLong(path), Indexes.mapLongToStr(path))
  val indexedRDF: IndexedRDD[Long, Long] = IndexedRDD(indexes.generateEncodedIndex(sp, Reader.extract(rdd))).cache()
  val spp: SparkProcess = SparkProcess(indexedRDF)

  var avrg: Long = 0
  for( a <- 0 to 800){
    avrg = avrg + Reader.time { indexedRDF.get(indexes.getId(s+p)) }
  }

  println("Moyenne IndexedRDF  : " + avrg/nbRun + " ns")
  Queries.query1(nbRun)

}
