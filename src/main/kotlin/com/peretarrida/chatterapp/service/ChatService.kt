package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.model.ChatInfoDTO

interface ChatService {
    fun getChatById(chatId : Long) : ChatsEntity

    fun createChat(chat: ChatInfoDTO, jwt : String) : Long

}