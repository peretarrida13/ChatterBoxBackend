package com.peretarrida.chatterapp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainClassController {
    @GetMapping("/")
    fun main() : String {
        return "Welcome to ChatterApp"
    }
}