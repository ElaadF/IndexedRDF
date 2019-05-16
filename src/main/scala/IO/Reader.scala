package IO

import org.apache.spark.rdd.RDD
import scala.io.Source
import scala.util.Try

object Reader {

  def read(path: String): Try[List[String]] = {
    Try(Source.fromFile(path).getLines.toList)
  }

  def extract(data: RDD[String]): RDD[(String, String, String)] = {
    data.map(line => {
     val tab: Array[String] = line.split(" ")
     (tab(0), tab(1), tab(2))
   })
  }

  def search(data: RDD[(String,String,String)], ind: String): RDD[(String,String,String)] = {
    data.filter(s => s._1.contains(ind)).filter(s => s._2.contains(ind)).filter(s => s._3.contains(ind))
  }

  def searchIndex(data: RDD[(String,String)], ind: String): RDD[(String,String)] = {
    data.filter(s => s._1.contains(ind)).filter(s => s._2.contains(ind))
  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }


}

object Writer {

}