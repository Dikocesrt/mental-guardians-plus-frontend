package com.example.mentalguardians.ui.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalguardians.R
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun BadMoodScreen(badMoodViewModel: BadMoodViewModel, navigateToArticleScreen: (String) -> Unit, navigateToStoryScreen: (String) -> Unit, navigateToTherapistScreen: (String) -> Unit, navigateToVideoScreen: (String) -> Unit, navigateToHomeScreen: () -> Unit){
    val options = listOf("Kepribadian", "Perundungan", "Pengasuhan", "Trauma", "Keluarga", "Romansa")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 32.dp, end = 32.dp, top = 64.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.badmood),
                contentDescription = "Image Thumbnail",
                modifier = Modifier
                    .width(175.dp)
                    .height(175.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Sepertinya kamu sedang\n tidak baik - baik saja",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Apakah kamu mau berbagi mengenai masalahmu?",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                options.chunked(3).forEach { rowOptions ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowOptions.forEach { option ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { badMoodViewModel.onSelectedOptionChange(option) }
                                    .background(
                                        if (badMoodViewModel.selectedOption.value == option) Color(0xFF3580FF) else Color.White,
                                        shape = RoundedCornerShape(10)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (badMoodViewModel.selectedOption.value == option) Color(0xFF3580FF) else Color(
                                            0XFFE9F1FF
                                        ),
                                        shape = RoundedCornerShape(10)
                                    )
                                    .padding(vertical = 12.dp), // Padding dalam button
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = option,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    color = if (badMoodViewModel.selectedOption.value == option) Color.White else Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        repeat(3 - rowOptions.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Perlu psikiater atau meditasi mandiri?",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color(0XFF3580FF),
                            shape = RoundedCornerShape(25)
                        ).width(124.dp).height(48.dp)
                        .clickable {
                            badMoodViewModel.selectedOption.value?.let {
                                var convertedCategory = ""
                                when(it) {
                                    "Kepribadian" -> convertedCategory = "personality"
                                    "Perundungan" -> convertedCategory = "bullying"
                                    "Pengasuhan" -> convertedCategory = "parenting"
                                    "Trauma" -> convertedCategory = "trauma"
                                    "Keluarga" -> convertedCategory = "family"
                                    "Romansa" -> convertedCategory = "love"
                                    else -> convertedCategory = ""
                                }
                                navigateToTherapistScreen(
                                    convertedCategory
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Psikolog",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Color(0XFF3580FF),
                            shape = RoundedCornerShape(25)
                        ).width(124.dp).height(48.dp)
                        .clickable {
                            badMoodViewModel.selectedOption.value?.let {
                                var convertedCategory = ""
                                when(it) {
                                    "Kepribadian" -> convertedCategory = "personality"
                                    "Perundungan" -> convertedCategory = "bullying"
                                    "Pengasuhan" -> convertedCategory = "parenting"
                                    "Trauma" -> convertedCategory = "trauma"
                                    "Keluarga" -> convertedCategory = "family"
                                    "Romansa" -> convertedCategory = "love"
                                    else -> convertedCategory = ""
                                }
                                navigateToVideoScreen(
                                    convertedCategory
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Video",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color(0XFF3580FF),
                            shape = RoundedCornerShape(25)
                        ).width(124.dp).height(48.dp)
                        .clickable {
                            badMoodViewModel.selectedOption.value?.let {
                                var convertedCategory = ""
                                when(it) {
                                    "Kepribadian" -> convertedCategory = "personality"
                                    "Perundungan" -> convertedCategory = "bullying"
                                    "Pengasuhan" -> convertedCategory = "parenting"
                                    "Trauma" -> convertedCategory = "trauma"
                                    "Keluarga" -> convertedCategory = "family"
                                    "Romansa" -> convertedCategory = "love"
                                    else -> convertedCategory = ""
                                }
                                navigateToArticleScreen(
                                    convertedCategory
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Artikel",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Color(0XFF3580FF),
                            shape = RoundedCornerShape(25)
                        ).width(124.dp).height(48.dp)
                        .clickable {
                            badMoodViewModel.selectedOption.value?.let {
                                var convertedCategory = ""
                                when(it) {
                                    "Kepribadian" -> convertedCategory = "personality"
                                    "Perundungan" -> convertedCategory = "bullying"
                                    "Pengasuhan" -> convertedCategory = "parenting"
                                    "Trauma" -> convertedCategory = "trauma"
                                    "Keluarga" -> convertedCategory = "family"
                                    "Romansa" -> convertedCategory = "love"
                                    else -> convertedCategory = ""
                                }
                                navigateToStoryScreen(
                                    convertedCategory
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Cerita Inspiratif",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .background(
                        Color(0XFF3580FF),
                        shape = RoundedCornerShape(25)
                    ).width(124.dp).height(48.dp)
                    .clickable {
                        navigateToHomeScreen()
                    },
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Selesai",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}