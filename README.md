[![Build Status](https://travis-ci.com/ElaadF/IndexedRDF.svg?branch=master)](https://travis-ci.com/ElaadF/IndexedRDF)
[![codecov](https://codecov.io/gh/ElaadF/IndexedRDF/branch/master/graph/badge.svg)](https://codecov.io/gh/ElaadF/IndexedRDF)


# Indexed RDF

### Implémentation

La première approche a été de créer une HashMap de String -> String, mais voyant les performance j'ai décidé d'utiliser une hashMap de Long -> Long.   
Le fichier est encodé pour pouvoir l'indexer avec RDD plus efficacement, bien que cela semble plus rapide, un gros problème eprsiste. En effet la recherche semble bien plus longue (facteur 100) avec l'indexation des RDF qu'en executant une simple requête via Jena. Bloquant ansi la suite du projet.

Le projet contient donc 3 registres :
String -> Long : encode les combinaisons de s,p,o
Long -> : permet de retrouver la string à partir de son id
Long -> Long : utiliser pour l'indexation via indexedRDD

Extrait d'un résultat de benchmarck de recherche :   
```
Moyenne IndexedRDF  : 50843088 ns
Moyenne requête Jena: 570477 ns
```

Aucune explication n'a été trouvée, l'hypothèse voudrait que la recherche soit bien plus rapide, tandis que l'ajout ou la supression plus lente car il faut maintenir l'indexe à jour.
(https://github.com/amplab/spark-indexedrdd)
