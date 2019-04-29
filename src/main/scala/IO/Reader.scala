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
}

object Writer {

}