package com.peretarrida.chatterapp.domain

import jakarta.persistence.*

@Entity
@Table(name="userchats", schema="public")
open class UserChatEntity(
    @Id
    @Column(name="id", nullable = false)
    @SequenceGenerator(name = "idChatGenerator", sequenceName = "chats_chat_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idChatGenerator")
    open var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    val user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "chatid", referencedColumnName = "id")
    val chat: ChatsEntity
) {}
