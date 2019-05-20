package IO

import org.apache.spark.rdd.RDD

object Reader {

  def extract(data: RDD[String]): RDD[(String, String, String)] = {
    data.map(line => {
     val tab: Array[String] = line.split(" ")
     (tab(0), tab(1), tab(2))
   })
  }

  def time[R](block: => R): Long = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    t1 - t0
  }

}
