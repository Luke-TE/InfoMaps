package com.ichack.server

import Client
import DataClasses.InputQuery
import DataClasses.Query
import DataSearchers.searchRoutine
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.routing.post
import io.ktor.routing.routing
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
    }
    routing {
        post("/") {
            val post = call.receive<InputQuery>()

        }
    }
    val client = HttpClient(Apache) {
    }
    runBlocking {
        searchRoutine(Query(listOf("greenland", "denmark", "palestine"), "gdp per capita"), Client(client))
    }

}
