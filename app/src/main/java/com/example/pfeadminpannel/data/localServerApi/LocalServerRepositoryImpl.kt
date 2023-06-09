package com.example.pfeadminpannel.data.localServerApi

import android.util.Log
import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.localServer.ApprovedPost
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalServerRepositoryImpl @Inject constructor(
    private val localServerApi: LocalServerApi
) : LocalServerRepository {
    override suspend fun getPostToReview(): getPostToReviewResponse {
        return try {
            val postsToReview = localServerApi.getPostsToReview()
            Log.d("getData", "getPostToReview: success")
            Response.Success(postsToReview)
        }catch (e:Exception){
            Log.d("getData", "getPostToReview: failure $e")
            Response.Failure(e)
        }
    }

    override suspend fun getAllUsers(): getAllUsersResponse {
        return try {
            Response.Success(localServerApi.getAllUsers())
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun getUserProfileInfo(userUID: String): getUserProfileInfoResponse {
        return try {
            Response.Success(localServerApi.getUserProfileInfo(userUID))
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun deletePostFromReview(id : Int): deletePostFromReviewResponse {
        return try {
            localServerApi.deletePostFromReview(id)
            Log.d("deleteData", "deletePostFromReview: Success")
            Response.Success("Post deleted successfully")
        }catch (e:Exception){
            Log.d("deleteData", "deletePostFromReview: Failure")
            Response.Failure(e)
        }
    }

    override suspend fun deleteUserWithUid(userUID: String): deleteUserWithUidResponse {
        return try {
            Response.Success(localServerApi.deleteUserWithUid(userUID))
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun insertApprovedPost(approvedPost: ApprovedPost): insertApprovedPostResponse {
        return try {
            localServerApi.insertApprovedPost(approvedPost)
            Response.Success("Post added successfully")
        }catch (e:Exception){
            Response.Failure(e)
        }
    }
}