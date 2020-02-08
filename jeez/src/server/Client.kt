import DataClasses.Query
import DataClasses.QueryResult
import com.ichack.server.DataClasses.Country
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post

class Client(val ktorClient: HttpClient) {
    suspend fun post(country: Country, query: Query) {
        val queryResult = ktorClient.post<QueryResult>(
            HttpRequestBuilder(

            )
        )
    }
}