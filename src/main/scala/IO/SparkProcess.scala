package IO

import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD


case class SparkProcess(index: IndexedRDD[Long, Long]) {

  def search(indexes: Indexes, s: Option[String], p: Option[String], o: Option[String]): Option[String] = {
   val search: String = s.getOrElse("") + p.getOrElse("")+ o.getOrElse("")
  println(search)
    index.get(indexes.getId(search)) match {
      case Some(result) =>  Some(indexes.getStr(result))
      case None => None
    }
  }

}
