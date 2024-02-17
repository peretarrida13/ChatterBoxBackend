package com.peretarrida.chatterapp.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "messages", schema = "public")
open class MessageEntity(
    @Id
    @Column(name="id", nullable = false)
    @SequenceGenerator(name = "idMessageGenerator", sequenceName = "messages_message_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idMessageGenerator")
    open var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "chatid", referencedColumnName = "id")
    val chat: ChatsEntity,

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "id")
    val sender: UserEntity,

    @Column(name="messagetext", nullable = false)
    val messageText: String,

    @Column(name = "sentat", nullable = false)
    val sentAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "readed", nullable = false)
    var readed: Boolean = false,
) {}