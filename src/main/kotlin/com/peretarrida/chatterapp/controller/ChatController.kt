package com.peretarrida.chatterapp.controller

import com.peretarrida.chatterapp.model.ChatInfoDTO
import com.peretarrida.chatterapp.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping( path = ["/api/chat"])
class ChatController (private val chatService: ChatService) {
    @PostMapping("/createChat")
    fun createChat(@RequestBody chatInfo : ChatInfoDTO, @RequestHeader("access-token") jwt : String) : ResponseEntity<Any> {
        return ResponseEntity.ok(chatService.createChat(chatInfo, jwt))
    }
}