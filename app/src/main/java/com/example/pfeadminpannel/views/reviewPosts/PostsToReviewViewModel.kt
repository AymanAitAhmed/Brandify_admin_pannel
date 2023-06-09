package com.example.pfeadminpannel.views.reviewPosts

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pfeadminpannel.components.convertPosts
import com.example.pfeadminpannel.data.facebookApi.FacebookRepository
import com.example.pfeadminpannel.data.localServerApi.LocalServerRepository
import com.example.pfeadminpannel.module.Post
import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.localServer.ApprovedPost
import com.example.pfeadminpannel.module.toFb.AttachedMedia
import com.example.pfeadminpannel.module.toFb.PictureToFb
import com.example.pfeadminpannel.module.toFb.PostToFb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsToReviewViewModel @Inject constructor(
    private val localServerRepository: LocalServerRepository,
    private val facebookRepository: FacebookRepository
) : ViewModel() {

    init {
        getPostsList()
    }

    private val _postsToReviewList = MutableStateFlow<List<Post>>(emptyList())
    val postsToReviewList = _postsToReviewList.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val currentPostCardClickedInfo = mutableStateOf(Post(null, null, null, null))

    val errorMessage = mutableStateOf("")

    val successMessage = mutableStateOf("")

    fun getPostsList() {
        Log.d("refreshing", "list is refreshing")
        viewModelScope.launch {
            val getPostsToReviewResponse = localServerRepository.getPostToReview()

            when (getPostsToReviewResponse) {
                is Response.Failure -> {
                    errorMessage.value = "${getPostsToReviewResponse.e.message}"
                }

                is Response.Loading -> {

                }

                is Response.Success -> {
                    _postsToReviewList.value = convertPosts(getPostsToReviewResponse.data)
                }
            }
        }
    }

    fun deletePostFromReview(
        id: Int,
        actionPreformed: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            val deletePostFromResponse = localServerRepository.deletePostFromReview(id)

            when (deletePostFromResponse) {
                is Response.Failure -> deletePostFromResponse.apply {
                    errorMessage.value = "Failed to delete: $e"
                    actionPreformed.value = false
                }

                is Response.Loading -> {

                }

                is Response.Success -> {
                    actionPreformed.value = false
                    getPostsList()
                }
            }
        }
    }

    fun acceptePost(
        imagesUrls: List<String>?,
        message: String,
        userUID: String,
        id: Int,
        actionPreformed: MutableState<Boolean>
    ) {

        viewModelScope.launch {

            val imagesPostedToFbIds = mutableListOf<AttachedMedia>()
            var imagesPostedCount = 0
            imagesUrls?.let {
                it.forEach { imageUrl ->
                    val imageToFbResponse = facebookRepository.postImageToFacebook(
                        PictureToFb(url = imageUrl)
                    )

                    when (imageToFbResponse) {
                        //imageToFbResponse success
                        is Response.Success -> {
                            Log.d("postToFb", "image posted ${imageToFbResponse.data}")
                            imagesPostedToFbIds.add(AttachedMedia(imageToFbResponse.data))
                            imagesPostedCount++
                        }
                        //imageToFbResponse failure
                        is Response.Failure -> {
                            Log.d("postToFb", "image failed to post ${imageToFbResponse.e}")
                            actionPreformed.value = false
                        }
                        //imageToFbResponse loading
                        is Response.Loading -> TODO()
                    }
                }
            }
            if (imagesPostedCount == imagesUrls?.size) {
                val postToFbResponse = facebookRepository.postToFacebook(
                    PostToFb(
                        message = message,
                        attached_media = imagesPostedToFbIds
                    )
                )

                when (postToFbResponse) {
                    //postToFbResponse failure
                    is Response.Failure -> {
                        Log.d("postToFb", "post failed: ${postToFbResponse.e}")
                        actionPreformed.value = false
                    }
                    //postToFbResponse loading
                    is Response.Loading -> TODO()
                    //postToFbResponse success
                    is Response.Success -> {
                        Log.d("postToFb", "post success to fb")
                        val insertApprovedPostResponse = localServerRepository.insertApprovedPost(
                            ApprovedPost(
                                post_id = postToFbResponse.data.id,
                                user_UID = userUID
                            )
                        )

                        when (insertApprovedPostResponse) {
                            //insertApprovedPostResponse failure
                            is Response.Failure -> {
                                Log.d(
                                    "postToFb",
                                    "post failed to insert to Mysql: ${insertApprovedPostResponse.e}"
                                )
                                actionPreformed.value = false
                            }
                            //insertApprovedPostResponse loading
                            is Response.Loading -> TODO()
                            //insertApprovedPostResponse success
                            is Response.Success -> {
                                Log.d("postToFb", "post success to MySQL")
                                deletePostFromReview(
                                    id = id,
                                    actionPreformed = actionPreformed
                                )
                                actionPreformed.value = false
                            }
                        }
                    }
                }
            }
        }
    }



}