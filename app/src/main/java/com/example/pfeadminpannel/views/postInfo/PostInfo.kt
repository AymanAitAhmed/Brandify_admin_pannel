package com.example.pfeadminpannel.views.postInfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.views.reviewPosts.PostsToReviewViewModel

@Composable
fun PostInfoScreen(
    viewModel: PostsToReviewViewModel,
    navController: NavController
) {

    val actionPreformed = remember {
        mutableStateOf(false)
    }

    val currentPost = viewModel.currentPostCardClickedInfo.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(backgroundColor = MaterialTheme.colors.background) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (!actionPreformed.value) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .padding(start = 16.dp, end= 16.dp,top=4.dp,bottom=4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = {
                                    currentPost.id?.let {
                                        actionPreformed.value = true
                                        viewModel.deletePostFromReview(
                                            it,
                                            actionPreformed
                                        )
                                    } ?: run {
                                        viewModel.errorMessage.value = "error: null Id"
                                    }
                                }, shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color(0xFF85E779),
                                    backgroundColor = Color.Transparent
                                ),
                                border = BorderStroke(2.dp, Color(0xFF85E779)),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(text = "Decline")
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp,top=4.dp,bottom=4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    currentPost.id?.let { ID ->
                                        currentPost.userUID?.let { USER_UID ->
                                            currentPost.message?.let { MESSAGE ->
                                                actionPreformed.value = true
                                                viewModel.acceptePost(
                                                    imagesUrls = currentPost.imagesUrls,
                                                    message = MESSAGE,
                                                    userUID = USER_UID,
                                                    id = ID,
                                                    actionPreformed = actionPreformed
                                                )
                                            } ?: run {
                                                viewModel.errorMessage.value = "error: null message"
                                            }
                                        } ?: run {
                                            viewModel.errorMessage.value = "error: null user UID"
                                        }

                                    } ?: run {
                                        viewModel.errorMessage.value = "error: null Id"
                                    }

                                }, shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color(0xFF85E779)
                                ),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(text = "Accept")
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
                        }
                    }
                }

            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.post_details))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    currentPost.userUID?.let { userUID ->
                        currentPost.message?.let { message ->
                            InReviewPostCard(
                                userUID = userUID,
                                message = message,
                                imagesUrls = currentPost.imagesUrls
                            )
                        } ?: run {
                            Text(text = "null message")
                        }
                    } ?: run {
                        Text(text = "null uid")
                    }
                }

            }

        }

    }
}
