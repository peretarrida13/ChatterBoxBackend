package com.peretarrida.chatterapp.domain

import jakarta.persistence.*

@Entity
@Table(name="users", schema="public")
open class UserEntity(
    @Id
    @Column(name="id", nullable = false)
    @SequenceGenerator(name = "idUsersGenerator", sequenceName = "users_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idUsersGenerator")
    open var id: Int? = null,

    @Column(name="username", nullable = false)
    open var username : String,

    @Column(name = "password", nullable = false)
    open var password : String,
){}

