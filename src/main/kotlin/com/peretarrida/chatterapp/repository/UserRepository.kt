package com.peretarrida.chatterapp.repository

import com.peretarrida.chatterapp.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String) : UserEntity?

    fun findByUsernameContaining(username: String) : List<UserEntity>
}