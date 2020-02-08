package DataSearchers

import DataClasses.Query
import DataClasses.QueryResults

interface Searcher {
    fun search(query: Query): QueryResults?
}