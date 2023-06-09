package com.example.pfeadminpannel.data.localServerApi

import com.example.pfeadminpannel.module.Post
import com.example.pfeadminpannel.module.localServer.ApprovedPost
import com.example.pfeadminpannel.module.localServer.DeleteUserResponse
import com.example.pfeadminpannel.module.localServer.PostReceived
import com.example.pfeadminpannel.module.localServer.User
import com.example.pfeadminpannel.module.localServer.UserProfileInfo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LocalServerApi {

    @GET("review/posts")
    suspend fun getPostsToReview():List<PostReceived>

    @GET("allusers")
    suspend fun getAllUsers():List<User>

    @GET("allusers/profile/{user_uid}")
    suspend fun getUserProfileInfo(@Path("user_uid") userUID: String) : UserProfileInfo

    @DELETE("review/posts/{id}")
    suspend fun deletePostFromReview(@Path("id") id : Int)

    @DELETE("allusers/{user_uid}")
    suspend fun deleteUserWithUid(@Path("user_uid") userUID : String):DeleteUserResponse

    @POST("approved/posts")
    suspend fun insertApprovedPost(@Body approvedPost: ApprovedPost)

    @POST("approved/posts")
    suspend fun accepteAPost(@Body post: Post)
}