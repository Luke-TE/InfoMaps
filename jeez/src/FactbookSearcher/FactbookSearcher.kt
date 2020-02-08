package FactbookSearcher

import DataClasses.Query
import DataClasses.QueryResult
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class FactbookSearcher() {
    fun search(query: Query): QueryResult? {
        val mapper = jacksonObjectMapper()
        val country = mapper.readTree("src/resources/countrydata/factbook.json")
        val countryData = country[query.country.name]["data"]
        val node = recSearch(countryData, query.searchTerm)
        if (node != null) {
            return null //classify(node)
        } else return null
    }

    private fun recSearch(data: JsonNode, searchTerm: String): JsonNode? {
        if (!data.fields().hasNext()) return null
        else if (data.has(searchTerm)) {
            return data[searchTerm]
        } else {
            return data.fields().asSequence().toList().map { recSearch(it.value, searchTerm) }.filterNotNull().first()
        }
    }
}

