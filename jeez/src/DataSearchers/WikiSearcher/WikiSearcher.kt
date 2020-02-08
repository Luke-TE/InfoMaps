package DataSearchers.WikiSearcher

import Client
import DataClasses.Query
import DataClasses.QueryResult
import DataClasses.QueryResults
import DataClasses.WikiInputJsonClass
import DataSearchers.Searcher
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class WikiSearcher(val client: Client) : Searcher {
    override fun search(query: Query): QueryResults {
        val resultsAsStrings = runBlocking<String?> {
            withTimeoutOrNull<String?>(10000L) {
                return@withTimeoutOrNull client.postToWiki(query.countries, query.searchTerm)
            }
        }
        println(resultsAsStrings)
        val inputJsonClass = resultsAsStrings?.let { jacksonObjectMapper().readValue<WikiInputJsonClass>(it) }
        return QueryResults(inputJsonClass?.results?.map { QueryResult(it.key, it.value.toString()) }.orEmpty())
    }
}