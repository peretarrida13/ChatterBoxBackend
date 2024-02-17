package com.peretarrida.chatterapp.repository

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.domain.UserChatEntity
import com.peretarrida.chatterapp.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserChatRepository : JpaRepository<UserChatEntity, Long> {
    fun getByUser(user: UserEntity) : List<UserChatEntity>

    fun getByChat(chat : ChatsEntity) : List<UserChatEntity>
    fun getByUserAndChat(user : UserEntity, chat :ChatsEntity) : UserChatEntity?
}