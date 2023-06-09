package com.example.pfeadminpannel.views.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pfeadminpannel.Graph
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.components.Screens
import com.example.pfeadminpannel.module.Response
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = 1) {
        delay(2000L)
        navController.navigate(Graph.REVIEW_POSTS) {
            popUpTo(Screens.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(350.dp)
            )
        }

    }
}