package com.peretarrida.chatterapp.controller

import com.peretarrida.chatterapp.service.UserChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/userChat"])
class UserChatController (private val userChatService: UserChatService){

    @GetMapping("/getUserChats")
    fun getUserChats(@RequestHeader("access-token") jwt : String): ResponseEntity<Any>{
        return ResponseEntity.ok(userChatService.getUserChats(jwt))
    }

    @GetMapping("/getChatInfo/{chatId}")
    fun getChatInfo(@RequestHeader("access-token") jwt : String, @PathVariable("chatId") chatId : Long): ResponseEntity<Any>{
        return ResponseEntity.ok(userChatService.getChatInfo(jwt, chatId))
    }
}