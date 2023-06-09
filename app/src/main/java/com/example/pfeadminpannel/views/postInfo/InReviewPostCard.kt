package com.example.pfeadminpannel.views.postInfo

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pfeadminpannel.components.PicsLayout

@Composable
fun InReviewPostCard(userUID : String, message:String, imagesUrls : List<String>?) {

    val showFullText = remember {
        mutableStateOf(false)
    }

    Card(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .border(
                            2.dp, MaterialTheme.colors.onBackground, CircleShape
                        ),
                    tint = MaterialTheme.colors.onBackground
                    )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = userUID,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(20.dp)
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            if (!showFullText.value) {
                Text(
                    text = message,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .clickable {
                            showFullText.value = !showFullText.value
                        }
                )
            } else {
                Text(
                    text = message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showFullText.value = !showFullText.value
                        }
                )
            }

            Spacer(modifier = Modifier.size(12.dp))
            imagesUrls?.let {
                PicsLayout(imagesUrls = it)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }


}
