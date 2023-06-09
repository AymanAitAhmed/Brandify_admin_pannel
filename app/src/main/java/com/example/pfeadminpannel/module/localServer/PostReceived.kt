package com.example.pfeadminpannel.module.localServer

data class PostReceived(
    val id : Int?,
    val userUID : String?,
    val message : String?,
    val imagesUrls : String? = null
)