package DataClasses

import com.ichack.server.DataClasses.Country

data class Query(val country: Country, val searchTerm: String)