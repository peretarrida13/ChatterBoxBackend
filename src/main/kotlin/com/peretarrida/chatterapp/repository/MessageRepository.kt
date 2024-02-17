package com.peretarrida.chatterapp.repository

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.domain.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<MessageEntity, Long> {
    fun findByChatId(chatId : ChatsEntity) : ArrayList<MessageEntity>

    fun getByChatId(chatId : Long) : ArrayList<MessageEntity>
}