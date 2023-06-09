package com.example.pfeadminpannel.views.reviewPosts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.views.allUsers.AllUsersReviewTopBar


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsToReviewScreen(
    viewModel: PostsToReviewViewModel,
    navController: NavController
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = viewModel.errorMessage.value) {
        if (viewModel.errorMessage.value != "") {
            snackbarHostState.showSnackbar(viewModel.errorMessage.value)
        }
    }
    LaunchedEffect(key1 = viewModel.successMessage.value) {
        if (viewModel.successMessage.value != "") {
            snackbarHostState.showSnackbar(viewModel.successMessage.value)
        }
    }

    val postsToReview by viewModel.postsToReviewList.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.getPostsList() })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            PostsToReviewTopBar(text = R.string.review)
        }
    ) { paddingValues ->
            Box(
                modifier = Modifier
                    .pullRefresh(refreshState)
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (postsToReview.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        items(postsToReview) { post ->

                            PostCard(
                                id = post.id,
                                userUID = post.userUID,
                                message = post.message,
                                imagesUrls = post.imagesUrls,
                                viewModel = viewModel,
                                navController = navController
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                        }
                    }
                }else{
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
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


                PullRefreshIndicator(
                    isRefreshing,
                    refreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }



    }

}