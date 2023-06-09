package com.example.pfeadminpannel.module.toFb

import com.example.pfeadminpannel.components.Constants

data class PostToFb(
    val access_token: String = Constants.ACCESS_TOKEN,
    val attached_media: List<AttachedMedia>,
    val message: String,
    val published: Boolean = true
)