package com.peretarrida.chatterapp.controller

import com.peretarrida.chatterapp.model.MessageDTO
import com.peretarrida.chatterapp.service.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/messages"])
class MessageController(private val messageService: MessageService) {

    @PostMapping("/createMessage")
    fun createMessage(@RequestBody message : MessageDTO, @RequestHeader("access-token") jwt : String): ResponseEntity<Any>{
        return ResponseEntity.ok(messageService.createMessage(message, jwt))
    }

    @GetMapping("/getMessagesByChat/{chatId}")
    fun getMessagesByChat(@PathVariable("chatId") chatId : Long, @RequestHeader("access-token") jwt: String): ResponseEntity<Any>{
        return ResponseEntity.ok(messageService.getMessageByChat(chatId, jwt))
    }

    @PostMapping("/readMessage/{messageId}")
    fun readMessage(@PathVariable("messageId") messageId : Long, @RequestHeader("access-token") jwt: String): ResponseEntity<Any>{
        return ResponseEntity.ok(messageService.readMessage(messageId, jwt))
    }
}