package com.example.pfeadminpannel.components

sealed class Screens(val route:String) {
    object SplashScreen : Screens("splash_screen")
    object ReviewPostsScreen : Screens("review_posts_screen")
    object PostInfoScreen : Screens("post_info_screen")
    object UsersListScreen : Screens("users_list_screen")
    object UserProfileScreen : Screens("user_profile_screen")

}
