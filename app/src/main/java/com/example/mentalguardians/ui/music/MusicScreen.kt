package com.example.mentalguardians.ui.music

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mentalguardians.R
import com.example.mentalguardians.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch

@Composable
fun MusicScreen(musicViewModel: MusicViewModel) {

    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentPlayingUrl by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        musicViewModel.getAllMusics(
            onError = {errorMessage->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
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
            items(musicViewModel.musicUIState.listContent) { item ->

                val isPlaying = currentPlayingUrl == item.musicURL && mediaPlayer?.isPlaying == true

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    shape = RoundedCornerShape(16),
                    border = BorderStroke(1.dp, Color(0xFF848A94)),
                    backgroundColor = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(90.dp)
                                    .width(90.dp)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = item.thumbnailURL),
                                    contentDescription = "Image Thumbnail",
                                    modifier = Modifier.height(90.dp)
                                        .width(90.dp).fillMaxSize().clip(RoundedCornerShape(5))
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = item.title,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFF002055),
                                )
                                Text(
                                    text = item.singer,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    color = Color(0xFF002055),
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (currentPlayingUrl == item.musicURL && mediaPlayer?.isPlaying == true) {
                                        mediaPlayer?.pause()
                                        currentPlayingUrl = null
                                    } else {
                                        mediaPlayer?.release() // Release any existing MediaPlayer
                                        mediaPlayer = MediaPlayer().apply {
                                            setDataSource(item.musicURL)
                                            prepareAsync()
                                            setOnPreparedListener { start() }
                                            setOnCompletionListener {
                                                currentPlayingUrl = null
                                            }
                                        }
                                        currentPlayingUrl = item.musicURL
                                    }
                                }
                            },
                            modifier = Modifier.size(64.dp)
                        ) {
                            Image(
                                painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                                contentDescription = "Play",
                                modifier = Modifier.size(64.dp),
                                colorFilter = ColorFilter.tint(Color(0xFF002055))
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(lazyColumnState) {
            snapshotFlow {
                lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.let { lastVisibleIndex ->
                    lastVisibleIndex >= musicViewModel.musicUIState.listContent.size - 3
                } ?: false
            }.collect { isEndReached ->
                if (!musicViewModel.musicUIState.isLoading && isEndReached && !musicViewModel.musicUIState.isLastPage) {
                    musicViewModel.getAllMusics(
                        onError = {errorMessage->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }

        if (musicViewModel.musicUIState.isLoading) {
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