package com.example.pfeadminpannel.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    items : List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem)->Unit
) {

    val theShowOnScreens = listOf(
        Screens.UsersListScreen,
        Screens.ReviewPostsScreen
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    val showBottomNav = theShowOnScreens.any{it.route == backStackEntry.value?.destination?.route}
    if (showBottomNav){
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 5.dp,
            modifier = modifier
        ) {
            items.forEach { item->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        onItemClick(item)
                    },
                    unselectedContentColor = MaterialTheme.colors.onSecondary,
                    selectedContentColor = MaterialTheme.colors.primary,
                    icon = {
                        Icon(painter = painterResource(id = item.icon), contentDescription = item.name)
                    },
                    label = {
                        Text(text = item.name, style = MaterialTheme.typography.body1)
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}