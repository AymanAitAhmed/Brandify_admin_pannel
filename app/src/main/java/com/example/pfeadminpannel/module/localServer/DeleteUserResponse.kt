package com.example.pfeadminpannel.module.localServer

data class DeleteUserResponse(
    val deleted : Boolean,
    val errorMessage:String? = null
)