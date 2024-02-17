package com.peretarrida.chatterapp.repository

import com.peretarrida.chatterapp.domain.ChatsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatsRepository : JpaRepository<ChatsEntity, Long> {
}