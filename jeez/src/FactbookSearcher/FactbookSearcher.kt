package FactbookSearcher

import DataClasses.Query
import DataClasses.QueryResult
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ichack.server.DataClasses.Country
import java.io.File

fun main() {
    FactbookSearcher().search(Query(Country("brazil"), "population"))
}

class FactbookSearcher() {
    fun search(query: Query): QueryResult? {
        val mapper = jacksonObjectMapper()
        val country = mapper.readTree(File("resources/countrydata/factbook.json"))["countries"]
        val countryData = country[query.country.name]["data"]
        val node = recSearch(countryData, query.searchTerm)
        if (node != null) {
            println(node.toString())
            return null //classify(node)
        } else return null
    }

    private fun recSearch(data: JsonNode, searchTerm: String): JsonNode? {
        if (!data.fields().hasNext()) return null
        else if (data.has(searchTerm)) {
            return data[searchTerm]
        } else {
            return data.fields().asSequence().toList().map { recSearch(it.value, searchTerm) }.filterNotNull()
                .firstOrNull()
        }
    }
}

