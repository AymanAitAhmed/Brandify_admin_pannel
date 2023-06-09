package com.example.pfeadminpannel.module.localServer

data class User(
    val userUID : String,
    val email : String,
    val name : String?=null,
    val photoUrl:String?=null,
    val isEmailVerified : Boolean,
    val postsCount : Int = 0
)
