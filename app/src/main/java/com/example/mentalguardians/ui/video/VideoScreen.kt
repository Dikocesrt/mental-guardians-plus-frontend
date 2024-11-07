package com.example.mentalguardians.ui.video

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mentalguardians.ui.theme.poppinsFontFamily
import com.example.mentalguardians.utils.extractYouTubeVideoId

@Composable
fun VideoScreen(videoViewModel: VideoViewModel, categoryVideos: String){
    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(Unit) {
        videoViewModel.clearList()
        videoViewModel.getAllVideos(
            onError = {error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            },
            categoryVideos = categoryVideos
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 64.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyColumnState
        ) {
            items(videoViewModel.videoUIState.listVideo) { item ->

                val videoID = extractYouTubeVideoId(item.videoID)
                val thumbnailURL = "https://img.youtube.com/vi/$videoID/mqdefault.jpg"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.videoID))
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(16),
                    border = BorderStroke(1.dp, Color(0xFF848A94)),
                    backgroundColor = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .height(90.dp)
                                .width(90.dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = thumbnailURL),
                                contentDescription = "Image Thumbnail",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = item.author,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF002055),
                            )
                            Text(
                                text = item.title,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF002055),
                            )
                            Text(
                                text = item.labels,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF848A94),
                            )
                            Text(
                                text = "${item.likes} Likes | ${item.views} Views",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF848A94),
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(lazyColumnState) {
            snapshotFlow {
                lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.let { lastVisibleIndex ->
                    lastVisibleIndex >= videoViewModel.videoUIState.listVideo.size - 3
                } ?: false
            }.collect { isEndReached ->
                if (!videoViewModel.videoUIState.isLoading && isEndReached && !videoViewModel.videoUIState.isLastPage) {
                    videoViewModel.getAllVideos(
                        onError = {error ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        },
                        categoryVideos = categoryVideos
                    )
                }
            }
        }

        if (videoViewModel.videoUIState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}