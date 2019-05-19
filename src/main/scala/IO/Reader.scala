package IO

import org.apache.spark.rdd.RDD
import scala.io.Source
import scala.util.Try

object Reader {

  def uuid = java.util.UUID.randomUUID

  def mapLongToStr(path: String) = {
    var count:Long = 1
    val lines = Source.fromFile(path).getLines.toList
    val sp = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (count,tab(0)+tab(1))
    }
    ).toMap

    val s = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (count,tab(0))
    }
    ).toMap

    val p = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (count,tab(1))
    }
    ).toMap

    val o = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (count,tab(2))
    }
    ).toMap

    s ++ p ++ o ++ sp
  }

  def mapStrToLong(path: String) = {
    var count:Long = 1
    val lines = Source.fromFile(path).getLines.toList
    val sp = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (tab(0)+tab(1), count)
    }
    ).toMap

    val s = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (tab(0), count)
    }
    ).toMap

    val p = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (tab(1), count)
    }
    ).toMap

    val o = lines.map(l => {
      val tab = l.split(" ")
      count = count + 1
      (tab(2), count)
    }
    ).toMap

    s ++ p ++ o ++ sp
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

  def time[R](block: => R): Long = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
//    println("Elapsed time: " + (t1 - t0) + "ns")
    t1 - t0
  }


}

object Writer {

}