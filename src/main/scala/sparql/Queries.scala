package sparql

import java.io.FileInputStream
import java.io.File

import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.sparql.util.Utils
import org.apache.jena.util._

object Queries extends App {
      val file = new File("resources/data/LiteMat/lubm1.ttl")
      val input = new FileInputStream(file)

      val model = ModelFactory.createDefaultModel()
      model.read(input, null, "TURTLE")

      System.out.println("\n---- Turtle ----")
      model.write(System.out, "TURTLE")
}
