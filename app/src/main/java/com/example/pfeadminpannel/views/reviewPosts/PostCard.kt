package com.example.pfeadminpannel.views.reviewPosts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.components.Screens
import com.example.pfeadminpannel.module.Post

@Composable
fun PostCard(
    id: Int?,
    userUID: String?,
    message: String?,
    imagesUrls: List<String>?,
    viewModel: PostsToReviewViewModel,
    navController: NavController
) {

    val actionPreformed = remember {
        mutableStateOf(false)
    }

    Card(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .clickable {
                viewModel.currentPostCardClickedInfo.value = Post(id,userUID,message,imagesUrls)
                navController.navigate(route = Screens.PostInfoScreen.route)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            imagesUrls?.let {
                SubcomposeAsyncImage(
                    model = it.first(), contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(70.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        ), loading = {
                        CircularProgressIndicator()
                    }, error = {
                        painterResource(id = R.drawable.baseline_cancel_24)
                    },
                    contentScale = ContentScale.Crop
                )
            }




            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "$userUID",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$message",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.body2,
                    color = if (isSystemInDarkTheme()) Color(0xFF959595) else Color(0xFFBABABA)
                )
            }


            if (!actionPreformed.value) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            id?.let { ID ->
                                userUID?.let { USER_UID ->
                                    message?.let { MESSAGE ->
                                        actionPreformed.value = true
                                        viewModel.acceptePost(
                                            imagesUrls = imagesUrls,
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

                        }, modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.background,
                            backgroundColor = Color(0xFF85E779)
                        )
                    ) {
                        Text(text = "Accept")
                    }

                    OutlinedButton(
                        onClick = {
                            id?.let {
                                actionPreformed.value = true
                                viewModel.deletePostFromReview(it, actionPreformed)
                            } ?: run {
                                viewModel.errorMessage.value = "error: null Id"
                            }
                        }, shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color(0xFF85E779),
                            backgroundColor = Color.Transparent
                        ),
                        border = BorderStroke(2.dp, Color(0xFF85E779))
                    ) {
                        Text(text = "Decline")
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


}

