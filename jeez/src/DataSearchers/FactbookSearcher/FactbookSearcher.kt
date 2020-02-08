package DataSearchers.FactbookSearcher

import DataClasses.Query
import DataClasses.QueryResult
import DataClasses.QueryResults
import DataSearchers.Searcher
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun main() {
    println(FactbookSearcher().search(Query(listOf("canada"), "languages")))
}

class FactbookSearcher() : Searcher {
    override fun search(query: Query): QueryResults {
        val mapper = jacksonObjectMapper()
        val country = mapper.readTree(File("resources/countrydata/factbook.json"))["countries"]
        return QueryResults(query.countries.map {
            val countryData = country[it]["data"]
            val node = recSearch(countryData, query.searchTerm)
            val answer = if (node != null) {
                node.toString()
            } else null
            QueryResult(it, answer)
        })
    }

    private fun recSearch(data: JsonNode, searchTerm: String): JsonNode? {
        if (!data.fields().hasNext()) return null
        else if (data.has(searchTerm)) {
            return data[searchTerm]
        } else {
            return data.fields().asSequence().toList().mapNotNull { recSearch(it.value, searchTerm) }
                .firstOrNull()
        }
    }
}

