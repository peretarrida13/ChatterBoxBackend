package com.peretarrida.chatterapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatterAppApplication

fun main(args: Array<String>) {
    runApplication<ChatterAppApplication>(*args)
}
