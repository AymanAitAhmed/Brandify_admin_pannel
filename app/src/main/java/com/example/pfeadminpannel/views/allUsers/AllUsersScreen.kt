package com.example.pfeadminpannel.views.allUsers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.components.Screens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllUsersScreen(
    viewModel: AllUsersViewModel = hiltViewModel(),
    navController: NavController
) {

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val refreshingState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        viewModel.getAllUsersList()
    })
    val allUsersList by viewModel.allUsersList.collectAsStateWithLifecycle()

    val sortingType by viewModel.sortingType.collectAsStateWithLifecycle()

    Column {
        AllUsersReviewTopBar(
            text = R.string.users,
            viewModel = viewModel
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .pullRefresh(refreshingState),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.listIsLoading.value) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.size(90.dp)
                    )
                    Text(text = stringResource(id = R.string.list_users_loading))
                }
            } else if (allUsersList.isEmpty()) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_off_24),
                            contentDescription = null,
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier.size(90.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(id = R.string.no_users))
                    }

                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(ListSortingType.sortList(sortingType,allUsersList)) { user ->
                        UserCard(user = user, navigateToUserProfile = {
                            navController.navigate(route = "${Screens.UserProfileScreen.route}/${user.userUID}")
                        })
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
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
