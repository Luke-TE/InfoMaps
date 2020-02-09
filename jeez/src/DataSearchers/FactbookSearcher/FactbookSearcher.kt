package DataSearchers.FactbookSearcher

import DataClasses.Query
import DataClasses.QueryResult
import DataClasses.QueryResults
import DataSearchers.Searcher
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun main() {
    println(FactbookSearcher.search(Query(listOf("canada"), "languages")))
}

object FactbookSearcher : Searcher {
    override fun search(query: Query): QueryResults {
        val mapper = jacksonObjectMapper()
        val country = mapper.readTree(File("resources/countrydata/factbook.json"))["countries"]
        return QueryResults(query.countries.map {
            val countryData: JsonNode? = country[it]?.get("data")
            val node = if (countryData != null) {
                recSearch(countryData, query.searchTerm)
            } else null
            val answer = if (node != null) {
                node.toString()
            } else null
            QueryResult(it, answer)
        }.filter { it.answer != null })
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

