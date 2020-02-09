package DataSearchers

import Client
import DataClasses.Query
import DataClasses.QueryResults
import DataSearchers.FactbookSearcher.FactbookSearcher
import DataSearchers.WikiSearcher.WikiSearcher

fun searchRoutine(query: Query, client: Client): QueryResults? {
    var results = FactbookSearcher.search(query)
    if (results.results.isNullOrEmpty()) {
        results = WikiSearcher(client).search(query)
    }
    return if (results.results.isNullOrEmpty()) {
        null
    } else results
}