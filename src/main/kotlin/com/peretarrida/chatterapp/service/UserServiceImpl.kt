package com.peretarrida.chatterapp.service

import com.peretarrida.chatterapp.domain.UserEntity
import com.peretarrida.chatterapp.model.SignInBodyDTO
import com.peretarrida.chatterapp.model.UserDTO
import com.peretarrida.chatterapp.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

@Service
class UserServiceImpl(private val userRepository: UserRepository ) : UserService {

    override fun signIn(user: SignInBodyDTO): UserDTO{
        val userExists = userRepository.findByUsername(user.username)

        if(userExists != null){
            var passwordDecripted = decryptPassword(userExists.password);
            if(passwordDecripted.equals(user.password)){
                return UserDTO(
                    username = userExists.username,
                    token = generateToken(userExists.username)
                )
            } else{
                throw Exception("Incorrect Password");
            }

        } else{
            try{
                val newUser = UserEntity(
                    username = user.username,
                    password = encryptPassword(user.password),
                )

                userRepository.save(newUser)

                return UserDTO(
                    username = user.username,
                    token = generateToken(user.username)
                )
            } catch (e : Exception) {
                throw e
            }
        }

    }

    private fun generateToken(username: String) : String{
        val secretKey = "Scotty13" // You should use a more secure way to store your key

        return Jwts.builder()
            .setSubject(username)
            .signWith(SignatureAlgorithm.HS512, secretKey.toByteArray()).compact()
    }

    private fun encryptPassword(password: String) : String{
        val cipher = Cipher.getInstance("AES")
        val secretKey = SecretKeySpec("Bar12345Bar12345".toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val value = cipher.doFinal(password.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(value)
    }

    private fun decryptPassword(password: String) : String{
        val cipher = Cipher.getInstance("AES")
        val secretKey = SecretKeySpec("Bar12345Bar12345".toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(password))
        return String(plainText, Charsets.UTF_8)
    }

    override fun getUserByToken(token : String): UserEntity? {
        val secretKey = "Scotty13" // You should use a more secure way to store your key

        val claims = Jwts.parser().setSigningKey(secretKey.toByteArray()).parseClaimsJws(token).body

        return userRepository.findByUsername(claims.subject)
    }

    override fun validToken(token: String): Boolean {
        val secretKey = "Scotty13" // You should use a more secure way to store your key

        val claims = Jwts.parser().setSigningKey(secretKey.toByteArray()).parseClaimsJws(token).body

        val user = userRepository.findByUsername(claims.subject)

        return user != null;
    }

    override fun getUserByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }

    override fun findAllUsers(jwt: String): ArrayList<String> {
        if(!validToken(jwt)) throw Exception("Invalid Token")
        val activeUser = getUserByToken(jwt)
        val users = userRepository.findAll()

        val result = ArrayList<String>()

        for(user in users){
            if(user.username != activeUser?.username) {
                result.add(
                    user.username,
                )
            }
        }

        return result
    }
    override fun getUserByUsernameController(jwt: String, username: String): ArrayList<String> {
        if(!validToken(jwt)) throw Exception("Invalid Token")
        val activeUser = getUserByToken(jwt)
        val users = userRepository.findByUsernameContaining(username)

        val result = ArrayList<String>()

        for(user in users){
            if(user.username != activeUser?.username) {
                result.add(
                    user.username,
                )
            }
        }

        return result
    }

    override fun getUsername(jwt: String): UserDTO {
        if(!validToken(jwt)) throw Exception("Invalid Token")
        val activeUser = getUserByToken(jwt)
        return UserDTO(
            username = activeUser!!.username,
            token = ""
        )
    }
}