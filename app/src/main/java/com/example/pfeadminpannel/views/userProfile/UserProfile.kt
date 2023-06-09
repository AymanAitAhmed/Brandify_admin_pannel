package com.example.pfeadminpannel.views.userProfile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.components.isScrollingUp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfile(
    userUID: String,
    viewModel: UserProfileViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(key1 = userUID) {
        viewModel.userUID.value = userUID
        viewModel.getUserProfileInfo()
    }
    LaunchedEffect(key1 = viewModel.userDeleted.value) {
        if (viewModel.userDeleted.value) {
            navController.popBackStack()
        }
    }
    val userProfileInfo by viewModel.userProfileInfo.collectAsStateWithLifecycle()

    var statisticsSelected by remember {
        mutableStateOf(true)
    }
    val listState = rememberLazyListState()
    viewModel.isScrollingUp.value = listState.isScrollingUp()

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val refreshingState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        viewModel.getUserProfileInfo()
    })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(visible = viewModel.isScrollingUp.value&& !viewModel.deleteButtonClicked.value) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Delete")
                    },
                    onClick = {
                        viewModel.deleteUser()
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onBackground
                )
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SubcomposeAsyncImage(
                            model = userProfileInfo.photoUrl,
                            contentDescription = null,
                            loading = {
                                CircularProgressIndicator(color = MaterialTheme.colors.primary)
                            },
                            error = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onBackground
                                )
                            },
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                            userProfileInfo.username?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onBackground,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }


                            Text(
                                text = userProfileInfo.email,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.onBackground,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = userProfileInfo.userUID,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.onBackground,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

            }


            StatisticsPostsBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                onStatisticsClick = {
                    statisticsSelected = true
                },
                onPostsClick = {
                    statisticsSelected = false
                })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshingState),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    state = listState
                ) {

                    if (statisticsSelected) {
                        item {
                            StatisticsList(viewModel = viewModel, userProfileInfo = userProfileInfo)
                        }
                    } else {
                        if (userProfileInfo.allPosts.isNotEmpty()) {
                            item {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(16.dp)
                                )
                            }

                            items(userProfileInfo.allPosts) { userPost ->
                                userProfileInfo.username?.let {
                                    AcceptedPostCard(
                                        postDetailsFromFb = userPost,
                                        username = it
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(16.dp)
                                )
                            }
                        } else {
                            if (viewModel.isLoading.value) {
                                item {

                                    CircularProgressIndicator(modifier = Modifier.size(90.dp))
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.fetching_posts),
                                        color = MaterialTheme.colors.onSecondary
                                    )
                                }


                            } else {
                                item {
                                    Text(
                                        text = stringResource(id = R.string.empty_list),
                                        style = MaterialTheme.typography.body1,
                                        color = MaterialTheme.colors.onSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                        }
                    }
                }
                PullRefreshIndicator(
                    isRefreshing,
                    refreshingState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}