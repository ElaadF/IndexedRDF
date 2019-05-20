package sparql

import java.io.{File, FileInputStream}

import IO.Reader
import org.apache.jena.query.{QueryExecutionFactory, QueryFactory}
import org.apache.jena.rdf.model.ModelFactory

object Queries extends App {


  def query1(nbRun: Int) ={
    val queryString = "SELECT ?o WHERE { <http://www.Department0.University0.edu/FullProfessor0> <http://swat.cse.lehigh.edu/onto/univ-bench.owl#researchInterest> ?o}"
    val file = new File("resources/data/LiteMat/lubm1.ttl")
    val input = new FileInputStream(file)
    val model = ModelFactory.createDefaultModel()
    model.read(input, null, "TURTLE")

    var avrg: Long= 0
    for( a <- 0 to nbRun){
      val query = QueryFactory.create(queryString)
      val qe = QueryExecutionFactory.create(query, model)
      avrg = avrg + Reader.time {qe.execSelect }
    }

    println("Moyenne requête Jena: " + avrg/100 + " ns")
  }



}
