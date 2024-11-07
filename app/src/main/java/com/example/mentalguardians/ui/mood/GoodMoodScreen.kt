package com.example.mentalguardians.ui.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun GoodMoodScreen(navigateToHomeScreen: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.goodmood),
            contentDescription = "Image Thumbnail",
            modifier = Modifier
                .width(175.dp)
                .height(175.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Senang rasanya melihatmu sedang dalam keadaan baik - baik saja \uD83D\uDE4C",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
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