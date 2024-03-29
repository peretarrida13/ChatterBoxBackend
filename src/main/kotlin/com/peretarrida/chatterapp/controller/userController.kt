package com.peretarrida.chatterapp.controller

import com.peretarrida.chatterapp.model.SignInBodyDTO
import com.peretarrida.chatterapp.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/users"])
class userController( private val userService: UserService) {

    @GetMapping("/getAll")
    fun getAllUsers(@RequestHeader("access-token") jwt: String) : ResponseEntity<Any>{
        return ResponseEntity.ok(userService.findAllUsers(jwt))
    }

    @PostMapping("/signIn")
    fun signIn(@RequestBody user: SignInBodyDTO): ResponseEntity<Any> {
        System.out.println(user)
        try{
            return ResponseEntity.ok(userService.signIn(user))
        } catch (e : Exception){
            System.out.println(e)
            return ResponseEntity.badRequest().body("Error signing in")
        }
    }

    @GetMapping("/getUserByUsername/{username}")
    fun getUserByUsername(@RequestHeader("access-token") jwt : String, @PathVariable("username") username: String): ResponseEntity<Any> {
        return ResponseEntity.ok(userService.getUserByUsernameController(jwt, username))
    }

    @GetMapping("/getLoggedUser")
    fun getLoggedUser(@RequestHeader("access-token") jwt : String): ResponseEntity<Any> {
        return ResponseEntity.ok(userService.getUsername(jwt))
    }
}