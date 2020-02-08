package com.ichack.server

import Client
import DataClasses.Query
import DataSearchers.WikiSearcher.WikiSearcher
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
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
    val client = HttpClient(Apache) {
    }
    runBlocking {
        println(WikiSearcher(Client(client)).search(Query(listOf("canada", "new zealand"), "gdp")))
    }

}
