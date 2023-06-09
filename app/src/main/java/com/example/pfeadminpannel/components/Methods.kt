package com.example.pfeadminpannel.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.pfeadminpannel.module.Post
import com.example.pfeadminpannel.module.localServer.PostReceived
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun convertPosts(postsReceived: List<PostReceived>): List<Post> {
    return postsReceived.map { postReceived ->
        val urls = postReceived.imagesUrls?.let { extractUrls(it) }
        Post(postReceived.id, postReceived.userUID, postReceived.message, urls)
    }
}

fun extractUrls(input: String): List<String> {
    val regex = Regex("""https?://[^\s\[\],]+""")
    val matches = regex.findAll(input)

    return matches.map { it.value }.toList()
}

fun extractFilePathFromUrl(fileUrl: String): String? {
    val expectedHost = "firebasestorage.googleapis.com"
    val expectedPathPrefix = "/v0/b/"

    val url = URL(fileUrl)
    if (url.host != expectedHost || !url.path.startsWith(expectedPathPrefix)) {
        return null
    }

    val pathWithoutPrefix = url.path.substring(expectedPathPrefix.length)
    val decodedFilePath = URLDecoder.decode(pathWithoutPrefix, StandardCharsets.UTF_8)

    val startIndex = decodedFilePath.lastIndexOf('/') + 1
    val endIndex = decodedFilePath.length
    if (startIndex >= endIndex) {
        return null
    }
    return decodedFilePath.substring(startIndex, endIndex)
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
