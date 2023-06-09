package com.example.pfeadminpannel.data.facebookApi

import com.example.pfeadminpannel.module.toFb.PictureToFb
import com.example.pfeadminpannel.module.toFb.PictureToFbResponse
import com.example.pfeadminpannel.module.toFb.PostToFb
import com.example.pfeadminpannel.module.toFb.PostToFbResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FacebookApi {

    @POST("111969668523345/photos")
    suspend fun postImageToFacebook(@Body pictureToFb: PictureToFb) : PictureToFbResponse

    @POST("111969668523345/feed")
    suspend fun postToFacebook(@Body postToFb: PostToFb) : PostToFbResponse

}