package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.ChatsEntity
import com.peretarrida.chatterapp.domain.MessageEntity
import com.peretarrida.chatterapp.domain.UserEntity
import com.peretarrida.chatterapp.model.ChatMessageDTO
import com.peretarrida.chatterapp.model.MessageDTO
import com.peretarrida.chatterapp.repository.MessageRepository
import com.peretarrida.chatterapp.repository.UserChatRepository
import org.springframework.stereotype.Service
import kotlin.collections.ArrayList

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val chatService: ChatService,
    private val userService: UserService,
    private val userChatRepository: UserChatRepository
) : MessageService{
    override fun createMessage(message: MessageDTO, token : String): Int {
        val user : UserEntity? = userService.getUserByToken(token)

        if(user == null) return 404;
        //check if the chat is from the user



        val messageEntity : MessageEntity = MessageEntity(
            chat = chatService.getChatById(message.chatId),
            sender =  user,
            messageText = message.messageText,
        )

        try {
            messageRepository.save(messageEntity)
        } catch (e : Exception){
            throw e
            return 500;
        }

        return 200;
    }

    override fun getMessageByChat(chatId: Long, token: String): List<ChatMessageDTO> {
        if(userService.validToken(token)){
            val chat = chatService.getChatById(chatId)

            //check if the chat is from user
            val userChat = userChatRepository.getByUserAndChat(userService.getUserByToken(token)!!, chat)
            if(userChat == null){
                throw Exception("Not a valid Chat")
            }

            val messagesEntity = messageRepository.getByChatId(chat.id!!)
            val result : ArrayList<ChatMessageDTO> = ArrayList<ChatMessageDTO>()
            for(message in messagesEntity){
                result.add(
                    ChatMessageDTO(
                        id = message.id!!,
                        text = message.messageText,
                        username = message.sender.username,
                        time = message.sentAt.toString(),
                        read = message.readed
                    )
                )
            }

            return result
        } else{
            throw Exception("Not a valid User")
        }

    }

    override fun getLastMessage(chatId: ChatsEntity): ChatMessageDTO {
        var messages = messageRepository.getByChatId(chatId.id!!);
        if(messages.isEmpty()){
            return ChatMessageDTO(
                id = 0,
                text = "",
                username = "",
                time = "",
                read = false
            )
        }

        return ChatMessageDTO(
            id = messages.get(messages.size-1).id!!,
            text = messages.get(messages.size-1).messageText,
            username = messages.get(messages.size-1).sender.username,
            time = messages.get(messages.size-1).sentAt.toString(),
            read = messages.get(messages.size-1).readed
        )

    }

    override fun readMessage(messageId: Long, token: String): Int {
        if(userService.validToken(token)){
            val messageOptional = messageRepository.findById(messageId)
            System.out.println(messageOptional.get())
            if(!messageOptional.isPresent){
                throw Exception("Not a valid Message")
            }
            val message = messageOptional.get()
            val userChat = userChatRepository.getByUserAndChat(userService.getUserByToken(token)!!, message.chat)
            if(userChat == null){
                throw Exception("Not a valid Chat")
            }

            message.readed = true
            messageRepository.save(message)
            return 200
        } else{
            throw Exception("Not a valid User")
        }
    }
}