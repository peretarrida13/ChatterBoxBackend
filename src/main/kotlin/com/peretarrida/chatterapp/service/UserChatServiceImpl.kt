package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.domain.UserChatEntity
import com.peretarrida.chatterapp.domain.UserEntity
import com.peretarrida.chatterapp.model.ChatListInfo
import com.peretarrida.chatterapp.model.ChatUsers
import com.peretarrida.chatterapp.repository.ChatsRepository
import com.peretarrida.chatterapp.repository.UserChatRepository
import org.springframework.stereotype.Service

@Service
class UserChatServiceImpl(
    private val userChatRepository: UserChatRepository,
    private val userService: UserService,
    private val messageService: MessageService,
    private val chatRepository: ChatsRepository,
) : UserChatService{
    override fun getUserChats(token: String):ArrayList<ChatListInfo> {
        //parse chats
        val user = userService.getUserByToken(token)

        if(user == null) throw Exception("User does not Exists")

        val userChatSEntities = userChatRepository.getByUser(user)
        if(userChatSEntities.isEmpty()) throw Error("Empty List!")

        val result = ArrayList<ChatListInfo>()

        for(userChat in userChatSEntities){
            result.add(ChatListInfo(
                chatName = getChatName(userChat.chat, user.username),
                id = userChat.chat.id!!,
                lastMessage = messageService.getLastMessage(userChat.chat)
            ))
        }

        return result
    }

    private fun getChatName(chat: ChatsEntity, username: String): String{
        val users = userChatRepository.getByChat(chat)

        for(user in users){
            if(!user.user.username.equals(username)){
                return user.user.username
            }
        }

        throw Exception("No Chat Info!")
    }

    override fun createUserChatService(chat: ChatsEntity, user: UserEntity):Int {
        //pre token ya comprovat
        //create entity
        var userChatEntity: UserChatEntity = UserChatEntity(
            user = user,
            chat = chat
        )
        //save entity
        try {
            userChatRepository.save(userChatEntity)
        } catch (e : Exception){
            throw  Exception("Error Creating the Chat. Please Try Again!")
        }

        return 200
    }

    override fun isUserChat(user: UserEntity, chat : ChatsEntity) :Boolean {
        val userChat = userChatRepository.getByUserAndChat(user, chat)

        return userChat != null
    }


    override fun getChatInfo(token: String, chatId: Long): ChatUsers {
        return( ChatUsers(
            you = userService.getUserByToken(token)!!.username,
            other = getChatName(chatRepository.getOne(chatId), userService.getUserByToken(token)!!.username)
        ))
    }
}
