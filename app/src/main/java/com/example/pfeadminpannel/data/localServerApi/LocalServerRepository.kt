package com.example.pfeadminpannel.data.localServerApi

import com.example.pfeadminpannel.module.localServer.PostReceived
import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.localServer.ApprovedPost
import com.example.pfeadminpannel.module.localServer.DeleteUserResponse
import com.example.pfeadminpannel.module.localServer.User
import com.example.pfeadminpannel.module.localServer.UserProfileInfo

typealias getPostToReviewResponse = Response<List<PostReceived>>
typealias getAllUsersResponse = Response<List<User>>
typealias getUserProfileInfoResponse = Response<UserProfileInfo>
typealias deletePostFromReviewResponse = Response<String>
typealias deleteUserWithUidResponse = Response<DeleteUserResponse>
typealias insertApprovedPostResponse = Response<String>

interface LocalServerRepository {

    suspend fun getPostToReview() : getPostToReviewResponse

    suspend fun getAllUsers(): getAllUsersResponse

    suspend fun getUserProfileInfo(userUID: String) : getUserProfileInfoResponse

    suspend fun deletePostFromReview(id :Int) : deletePostFromReviewResponse

    suspend fun deleteUserWithUid(userUID:String) : deleteUserWithUidResponse

    suspend fun insertApprovedPost(approvedPost: ApprovedPost):insertApprovedPostResponse

}