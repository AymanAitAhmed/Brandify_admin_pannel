package com.example.pfeadminpannel.views.reviewPosts

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pfeadminpannel.R

@Composable
fun PostsToReviewTopBar(
    text: Int
) {
    val menuExpended = remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(text = stringResource(id = text))
        },
        actions = {
            IconButton(onClick = { menuExpended.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp,
        contentColor = MaterialTheme.colors.onBackground
    )
}