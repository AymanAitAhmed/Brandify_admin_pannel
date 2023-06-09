package com.example.pfeadminpannel.views.userProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pfeadminpannel.R


@Composable
fun StatisticsPostsBar(
    modifier: Modifier,
    onStatisticsClick: (MutableState<Boolean>) -> Unit,
    onPostsClick: (MutableState<Boolean>) -> Unit
) {
    Row(modifier = modifier) {
        val statisticsSelected = remember {
            mutableStateOf(true)
        }
        val postsSelected = remember {
            mutableStateOf(false)
        }
        BarButton(
            selected = statisticsSelected.value,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .background(if (!statisticsSelected.value) MaterialTheme.colors.surface else MaterialTheme.colors.primary)
                .clickable {
                    postsSelected.value = false
                    statisticsSelected.value = true
                    onStatisticsClick(statisticsSelected)
                },
            label = R.string.statistics,
            icon = R.drawable.baseline_bar_chart_24
        )
        BarButton(
            selected = postsSelected.value,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(if (!postsSelected.value) MaterialTheme.colors.surface else MaterialTheme.colors.primary)
                .clickable {
                    statisticsSelected.value = false
                    postsSelected.value = true
                    onPostsClick(postsSelected)
                },
            label = R.string.posts,
            icon = R.drawable.baseline_list_24
        )

    }
}

@Composable
fun BarButton(
    selected: Boolean,
    modifier: Modifier,
    label: Int,
    icon: Int
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = if (!selected) MaterialTheme.colors.onBackground else MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = label),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            color = if (!selected) MaterialTheme.colors.onBackground else MaterialTheme.colors.onBackground
        )
    }
}