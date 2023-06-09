package com.example.pfeadminpannel.module.localServer

data class UserProfileInfo(
    val allPosts : List<PostDetailsFromFb>,
    val userUID:String,
    val username:String?=null,
    val email : String,
    val photoUrl :String?= null,
    val totalReactions : Int,
    val totalLike : Int,
    val totalLove : Int,
    val totalHaha : Int,
    val totalWow : Int,
    val totalAngry : Int,
    val totalSad : Int,
    val totalCommentsEachMonth : List<Int>
)
