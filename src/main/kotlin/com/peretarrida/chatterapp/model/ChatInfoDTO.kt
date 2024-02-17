package com.peretarrida.chatterapp.model

import java.time.LocalDateTime

class ChatInfoDTO (
    val chatName : String,
    val isGroup : Boolean = false,
    val createdAt : LocalDateTime = LocalDateTime.now(),
    val newUsername : String
)