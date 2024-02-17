package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.domain.UserChatEntity
import com.peretarrida.chatterapp.domain.UserEntity
import com.peretarrida.chatterapp.model.ChatInfoDTO
import com.peretarrida.chatterapp.repository.ChatsRepository
import com.peretarrida.chatterapp.repository.UserChatRepository
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(
    private val chatRepository: ChatsRepository,
    private val userService: UserService,
    private val userChatRepository: UserChatRepository
) : ChatService {

    override fun getChatById(chatId: Long): ChatsEntity {
        return chatRepository.getById(chatId)
    }

    private fun checkChatExists(user: UserEntity, newUsername: String): Long {
        val userChat = userChatRepository.getByUser(user)
        val userChat2 = userChatRepository.getByUser(userService.getUserByUsername(newUsername)!!)

        for (i in userChat) {
            for (j in userChat2) {
                if (i.chat == j.chat) {
                    return i.chat.id!!
                }
            }
        }

        return 0
    }
    override fun createChat(chat: ChatInfoDTO, jwt : String): Long {
        if(userService.validToken(jwt)) {
            try {
                var exists = checkChatExists(userService.getUserByToken(jwt)!!, chat.newUsername)
                var checker : Long = 0
                if(exists != checker) {
                    return exists
                }

                val chatEntity: ChatsEntity = ChatsEntity(
                    chatName = chat.chatName,
                    isGroup = false,
                    createdAt = chat.createdAt,
                )

                chatRepository.save(chatEntity)

                val user = userService.getUserByToken(jwt)
                if(user == null) throw Exception("User does not Exists!");

                var userChatEntity = UserChatEntity(
                    user = user,
                    chat = chatEntity
                )

                var user2 = userService.getUserByUsername(chat.newUsername)
                if(user2 == null) throw Exception("User does not Exists!");

                val userChatEntity2 = UserChatEntity(
                    user = user2,
                    chat = chatEntity
                )

                userChatRepository.save(userChatEntity)
                userChatRepository.save(userChatEntity2)
                
                return chatEntity.id!!
            } catch (e: Exception) {
                System.err.println(e)
                throw Exception("Error creating Chat!")
            }
        } else{
            throw Exception("User does not Exists!")
        }
    }
}