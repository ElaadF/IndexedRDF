package IO

import IO.TripletElm.SPO
import org.apache.spark.rdd.RDD

import scala.io.Source

object TripletElm {
  sealed trait SPO
  case object s extends SPO
  case object p extends SPO
  case object o extends SPO
  case object sp extends SPO
  case object po extends SPO
  case object so extends SPO
}

case class Indexes(getId: Map[String, Long], getStr: Map[Long, String]){
  def generateEncodedIndex(indexedBy: SPO, rdd: RDD[(String, String, String)]): RDD[(Long, Long)] = {
    indexedBy match {
      case s => rdd.map({case(s,p,o) => (getId(s), getId(s+p))})
      case p => rdd.map({case(s,p,o) => (getId(p), getId(s+o))})
      case o => rdd.map({case(s,p,o) => (getId(s), getId(p+o))})
      case sp => rdd.map({case(s,p,o) => (getId(s+p), getId(o))})
      case po => rdd.map({case(s,p,o) => (getId(p+o), getId(s))})
      case so => rdd.map({case(s,p,o) => (getId(s+o), getId(p))})
    }
  }
}

object Indexes {
  def mapLongToStr(path: String): Map[Long, String] = {
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

  def mapStrToLong(path: String): Map[String, Long] = {
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
}