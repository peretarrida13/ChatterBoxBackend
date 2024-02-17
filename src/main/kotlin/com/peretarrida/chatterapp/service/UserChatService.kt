package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.domain.UserEntity
import com.peretarrida.chatterapp.model.ChatListInfo
import com.peretarrida.chatterapp.model.ChatUsers

interface UserChatService {

    fun createUserChatService(chat : ChatsEntity, user : UserEntity) : Int

    fun getUserChats(token : String) : ArrayList<ChatListInfo>

    fun isUserChat(user : UserEntity, chat: ChatsEntity) : Boolean

    fun getChatInfo(token : String, chatId : Long) : ChatUsers
}