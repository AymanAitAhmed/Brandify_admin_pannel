package com.example.pfeadminpannel.data.facebookApi

import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.toFb.PictureToFb
import com.example.pfeadminpannel.module.toFb.PostToFb
import com.example.pfeadminpannel.module.toFb.PostToFbResponse

typealias postImageToFacebookResponse = Response<String>
typealias postToFacebookResponse = Response<PostToFbResponse>

interface FacebookRepository {

    suspend fun postImageToFacebook(pictureToFb: PictureToFb) : postImageToFacebookResponse

    suspend fun postToFacebook(postToFb: PostToFb) : postToFacebookResponse


}