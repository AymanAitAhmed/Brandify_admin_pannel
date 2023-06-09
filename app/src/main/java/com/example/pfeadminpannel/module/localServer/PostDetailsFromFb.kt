package com.example.pfeadminpannel.module.localServer

data class PostDetailsFromFb(
    val like : Int,
    val haha : Int,
    val angry : Int,
    val love : Int,
    val wow : Int,
    val sad : Int,
    val comments : Int?,
    val shares : Int? = null,
    val message : String,
    val images : List<String>?,
    val id : String
)
