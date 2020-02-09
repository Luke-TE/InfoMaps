import DataClasses.WikiOutputJsonClass
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url

class Client(val ktorClient: HttpClient) {
    suspend fun postToWiki(countries: List<String>, searchTerm: String): String {
        return ktorClient.post<String>() {
            url("0.0.0.0:8080")
            body = jacksonObjectMapper().writeValueAsString(WikiOutputJsonClass(searchTerm, countries))
        }
    }
}