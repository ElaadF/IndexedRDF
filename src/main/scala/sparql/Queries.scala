package sparql

import java.io.FileInputStream
import java.io.File

import IO.Reader
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.sparql.util.Utils
import org.apache.jena.util._
import org.apache.jena.query.Query
import org.apache.jena.query.QueryExecution
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.QueryFactory
import org.apache.jena.query.ResultSetFormatter

object Queries extends App {
  val file = new File("resources/data/LiteMat/lubm1.ttl")
  val input = new FileInputStream(file)
  val model = ModelFactory.createDefaultModel()
  model.read(input, null, "TURTLE")

  val queryString = "" +
    "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
    "SELECT ?o WHERE { <http://www.Department0.University0.edu/FullProfessor0> <http://swat.cse.lehigh.edu/onto/univ-bench.owl#researchInterest> ?o}"

  val query = QueryFactory.create(queryString)

  // Execute the query and obtain results
  val qe = QueryExecutionFactory.create(query, model)
  Reader.time { qe.execSelect }
//  val results = qe.execSelect

  // Output query results
//  ResultSetFormatter.out(System.out, results, query)

}
