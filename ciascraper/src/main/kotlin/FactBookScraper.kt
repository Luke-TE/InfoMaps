import DataClasses.Region
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun scrape(
    region: Region
): List<String> {
    val countriesNode = jacksonObjectMapper().readTree(File("resources/countrydata/factbook.json"))["countries"]
    val countriesNodeList = countriesNode.fields().asSequence().toList().filter { it.key != "world" }
    return when (region) {
        Region.Global -> countriesNodeList.map { it.key }
        Region.NorthAmerica -> extractStuff(
            countriesNodeList,
            listOf("North America", "Central America and the Caribbean")
        )
        Region.SouthAmerica -> extractStuff(countriesNodeList, listOf("South America"))
        Region.Europe -> extractStuff(countriesNodeList, listOf("Europe"))
        Region.Asia -> extractStuff(countriesNodeList, listOf("Asia", "Middle East", "Southeast Asia"))
        Region.Africa -> extractStuff(countriesNodeList, listOf("Africa"))
        Region.Oceania -> extractStuff(countriesNodeList, listOf("Oceania"))
    }
}

private fun extractStuff(
    countriesNodeList: List<MutableMap.MutableEntry<String, JsonNode>>,
    listOfContinents: List<String>
): List<String> {
    return countriesNodeList.filter { listOfContinents.contains(it.value.get("data").get("geography").get("map_references").textValue()) }
        .map { it.key }
}

