package com.example.pfeadminpannel.views.userProfile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.components.BarGraph
import com.example.pfeadminpannel.components.BarType
import com.example.pfeadminpannel.components.PieChart
import com.example.pfeadminpannel.components.PieChartInput
import com.example.pfeadminpannel.module.localServer.UserProfileInfo
import com.example.pfeadminpannel.ui.theme.angryColor
import com.example.pfeadminpannel.ui.theme.hahaColor
import com.example.pfeadminpannel.ui.theme.likesColor
import com.example.pfeadminpannel.ui.theme.loveColor
import com.example.pfeadminpannel.ui.theme.sadColor
import com.example.pfeadminpannel.ui.theme.wowColor
import kotlin.math.log

@Composable
fun StatisticsList(
    viewModel: UserProfileViewModel,
    userProfileInfo: UserProfileInfo
) {
    if (viewModel.isLoading.value) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.size(200.dp)
        )
    } else {
        Spacer(modifier = Modifier.height(16.dp))
        PieChart(
            radius = 400f,
            innerRadius = 300f,
            modifier = Modifier
                .size(300.dp),
            input = mutableListOf(
                PieChartInput(
                    color = likesColor,
                    value = userProfileInfo.totalLike,
                    description = "Total Likes"
                ),
                PieChartInput(
                    color = loveColor,
                    value = userProfileInfo.totalLove,
                    description = "Total Love"
                ),
                PieChartInput(
                    color = hahaColor,
                    value = userProfileInfo.totalHaha,
                    description = "Total Haha"
                ),
                PieChartInput(
                    color = wowColor,
                    value = userProfileInfo.totalWow,
                    description = "Total Wow"
                ),
                PieChartInput(
                    color = angryColor,
                    value = userProfileInfo.totalAngry,
                    description = "Total Angry"
                ),
                PieChartInput(
                    color = sadColor,
                    value = userProfileInfo.totalSad,
                    description = "Total Sad"
                )
            )
        )
Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.pie_chart_title),
            style = MaterialTheme.typography.body1,
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colors.onSurface
        )

        if (userProfileInfo.totalCommentsEachMonth.any { it != 0 }) {
            val graphBarData = mutableListOf<Float>()
            val datesList = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)


            Log.d("StatisticsList", "executed")
            userProfileInfo.totalCommentsEachMonth.forEachIndexed { index, value ->
                graphBarData.add(
                    index = index,
                    element = value.toFloat() / userProfileInfo.totalCommentsEachMonth.max()
                        .toFloat()
                )
            }


            Spacer(modifier = Modifier.height(25.dp))

            BarGraph(
                graphBarData = graphBarData,
                xAxisScaleData = datesList,
                barData_ = userProfileInfo.totalCommentsEachMonth,
                height = 300.dp,
                roundType = BarType.TOP_CURVED,
                barWidth = 20.dp,
                barColor = MaterialTheme.colors.primary,
                barArrangement = Arrangement.spacedBy(4.dp)
            )
            Text(
                text = stringResource(id = R.string.bar_chart_title),
                style = MaterialTheme.typography.body1,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


    }


}

