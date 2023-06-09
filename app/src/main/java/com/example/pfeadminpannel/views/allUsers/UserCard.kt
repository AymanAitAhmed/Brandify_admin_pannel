package com.example.pfeadminpannel.views.allUsers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.pfeadminpannel.R
import com.example.pfeadminpannel.module.localServer.User

@Composable
fun UserCard(user: User,navigateToUserProfile : ()->Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = navigateToUserProfile)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp)
        ) {
            SubcomposeAsyncImage(
                model = user.photoUrl, contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    ), loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Icon(imageVector = Icons.Default.Person, contentDescription =null , tint = MaterialTheme.colors.onSurface )
                },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                user.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Text(
                    text = user.email,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = user.userUID,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = user.postsCount.toString(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(
                            if (user.isEmailVerified) Color(0xFF4AD61A) else Color(
                                0xFFE02C2C
                            )
                        )
                )
            }


        }
    }

}