package DataClasses

data class QueryResult(val country: String, val answer: String?)

data class QueryResults(val results: List<QueryResult>)