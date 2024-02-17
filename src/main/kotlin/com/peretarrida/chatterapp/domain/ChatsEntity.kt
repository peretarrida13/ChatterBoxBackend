package com.peretarrida.chatterapp.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chats")
class ChatsEntity (
    @Id
    @Column(name="id", nullable = false)
    @SequenceGenerator(name = "idChatGenerator", sequenceName = "chats_chat_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idChatGenerator")
    open var id: Long? = null,

    @Column(name="chatname")
    open var chatName: String?,

    @Column(name = "isgroup", nullable = false)
    open var isGroup : Boolean = false,

    @Column(name = "createdat", nullable = false)
    open var createdAt : LocalDateTime = LocalDateTime.now(),
)