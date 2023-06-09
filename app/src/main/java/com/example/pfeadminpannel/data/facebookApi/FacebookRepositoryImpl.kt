package com.example.pfeadminpannel.data.facebookApi

import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.toFb.PictureToFb
import com.example.pfeadminpannel.module.toFb.PostToFb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FacebookRepositoryImpl @Inject constructor(
    private val facebookApi: FacebookApi
) : FacebookRepository {
    override suspend fun postImageToFacebook(pictureToFb: PictureToFb): postImageToFacebookResponse {
        return try {
            val response  = facebookApi.postImageToFacebook(pictureToFb)
            Response.Success(response.id)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun postToFacebook(postToFb: PostToFb): postToFacebookResponse {
        return try {
            val postToFbResponse = facebookApi.postToFacebook(postToFb)
            Response.Success(postToFbResponse)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }
}