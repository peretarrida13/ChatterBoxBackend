package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.model.ChatMessageDTO
import com.peretarrida.chatterapp.model.MessageDTO

interface MessageService {

    fun createMessage(message: MessageDTO, token : String) : Int

    fun getMessageByChat(chatId : Long, token: String) : List<ChatMessageDTO>

    fun getLastMessage(chatId : ChatsEntity) : ChatMessageDTO

    fun readMessage(messageId : Long, token: String) : Int
}