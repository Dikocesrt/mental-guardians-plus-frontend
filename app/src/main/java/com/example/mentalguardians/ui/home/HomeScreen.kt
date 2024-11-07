package com.example.mentalguardians.ui.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navigateToVideoScreen: () -> Unit, navigateToArticleScreen: () -> Unit, navigateToStoryScreen: () -> Unit, navigateToMusicScreen: () -> Unit, navigateToGoodMoodScreen: () -> Unit, navigateToBadMoodScreen: () -> Unit){
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.getProfile { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    val items = listOf(
        ItemData("Video", "Butuh Video Menenangkan?", { navigateToVideoScreen() }),
        ItemData("Musik", "Butuh Musik Meditasi?", { navigateToMusicScreen() }),
        ItemData("Artikel", "Butuh Artikel Informatif?", { navigateToArticleScreen() }),
        ItemData("Cerita Inspiratif", "Butuh Cerita Penuh Inspirasi?", { navigateToStoryScreen() })
    )

    if(homeViewModel.HomeUIState.isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else {
        if(homeViewModel.HomeUIState.isShowDialog){
            PopUpDialog(
                homeViewModel = homeViewModel,
                navigateToGoodMoodScreen = { navigateToGoodMoodScreen() },
                navigateToBadMoodScreen = { navigateToBadMoodScreen() }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(bottom = 48.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "Hai, ${homeViewModel.HomeUIState.name}\nSenang melihatmu \uD83D\uDE4C",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF002055)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFF3580FF),
                    shape = RoundedCornerShape(12)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Bagaimana Perasaanmu?",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color(0xFFFFFFFF)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Deskripsikan kondisi anda saat ini untuk mendapatkan hasil analisis mood anda",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Color(0xFFFFFFFF)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        TextButton(
                            onClick = {
                                homeViewModel.changeShowDialog(true)
                            },
                            modifier = Modifier.padding(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Coba Sekarang!",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = Color(0xFFFFFFFF)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Fitur Meditasi",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color(0xFF002055)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { item.navigate() },
                            shape = RoundedCornerShape(16),
                            border = BorderStroke(1.dp, Color(0xFFE9F1FF))
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = item.title,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color(0xFF848A94)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.sub,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color(0xFF002055)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tekan Disini",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    color = Color(0xFF3580FF)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        if (homeViewModel.HomeUIState.isLoading2) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

data class ItemData(
    val title: String,
    val sub: String,
    val navigate: () -> Unit
)