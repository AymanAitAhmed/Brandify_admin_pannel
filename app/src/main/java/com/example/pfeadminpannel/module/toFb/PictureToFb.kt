package com.example.pfeadminpannel.module.toFb

import com.example.pfeadminpannel.components.Constants

data class PictureToFb(
    val access_token: String = Constants.ACCESS_TOKEN,
    val published: Boolean = false,
    val url: String
)