package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.UserEntity
import com.peretarrida.chatterapp.model.ChatInfoDTO
import com.peretarrida.chatterapp.model.SignInBodyDTO
import com.peretarrida.chatterapp.model.UserDTO

interface UserService {
    fun signIn(user : SignInBodyDTO) : UserDTO

    fun getUserByToken(token : String) : UserEntity?

    fun validToken(token : String) : Boolean

    fun getUserByUsername(username : String) : UserEntity?

    fun getUserByUsernameController(jwt: String, username : String) : ArrayList<String>

    fun findAllUsers(jwt: String) : List<String>

    fun getUsername(jwt: String) : UserDTO

}