package com.example.pfeadminpannel.module

data class Post(
val id : Int?,
val userUID : String?,
val message : String?,
val imagesUrls : List<String>? = null

)
