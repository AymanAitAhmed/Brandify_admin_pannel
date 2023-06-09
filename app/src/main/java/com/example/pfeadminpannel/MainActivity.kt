package com.example.pfeadminpannel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pfeadminpannel.components.BottomNavBar
import com.example.pfeadminpannel.components.BottomNavItem
import com.example.pfeadminpannel.components.Screens
import com.example.pfeadminpannel.ui.theme.PFEAdminPannelTheme
import com.example.pfeadminpannel.views.allUsers.AllUsersScreen
import com.example.pfeadminpannel.views.postInfo.PostInfoScreen
import com.example.pfeadminpannel.views.reviewPosts.PostsToReviewScreen
import com.example.pfeadminpannel.views.reviewPosts.PostsToReviewViewModel
import com.example.pfeadminpannel.views.splashScreen.SplashScreen
import com.example.pfeadminpannel.views.userProfile.UserProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PFEAdminPannelTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                val postsToReviewViewModel = hiltViewModel<PostsToReviewViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            BottomNavBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = stringResource(id = R.string.users),
                                        route = Screens.UsersListScreen.route,
                                        icon = R.drawable.baseline_people_24
                                    ),
                                    BottomNavItem(
                                        name = stringResource(id = R.string.review),
                                        route = Screens.ReviewPostsScreen.route,
                                        icon = R.drawable.baseline_list_24
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true

                                    }
                                }
                            )
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screens.SplashScreen.route,
                            modifier = Modifier.padding(it)
                        ) {
                            navigation(
                                startDestination = Screens.ReviewPostsScreen.route,
                                route = Graph.REVIEW_POSTS
                            ) {

                                composable(Screens.ReviewPostsScreen.route) {
                                    PostsToReviewScreen(
                                        navController = navController,
                                        viewModel = postsToReviewViewModel
                                    )
                                }
                                composable(Screens.PostInfoScreen.route) {
                                    PostInfoScreen(
                                        navController = navController,
                                        viewModel = postsToReviewViewModel
                                    )
                                }

                            }

                            navigation(
                                startDestination = Screens.UsersListScreen.route,
                                route = Graph.USERS
                            ) {
                                composable(route = Screens.UsersListScreen.route) {
                                    AllUsersScreen(navController = navController)
                                }
                                composable(route = "${Screens.UserProfileScreen.route}/{user_uid}") {
                                    val userUID = it.arguments?.getString("user_uid") ?: ""
                                    UserProfile(userUID, navController = navController)
                                }
                            }
                            composable(Screens.SplashScreen.route) {
                                SplashScreen(navController = navController)
                            }

                        }
                    }

                }
            }
        }
    }
}


object Graph {
    const val REVIEW_POSTS = "review_posts"
    const val USERS = "users"
}