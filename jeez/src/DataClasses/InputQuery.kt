package DataClasses

data class InputQuery(val queryString: String, val region: Region, val displayType: DisplayType)

enum class Region {
    Global, NorthAmerica, SouthAmerica, Europe, Asia, Africa, Oceania
}

enum class DisplayType {
    PerCapita, Total, Tality
}
