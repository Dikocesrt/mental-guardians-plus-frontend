package com.example.mentalguardians.ui.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalguardians.R
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun ThirdOnBoardScreen(navigateToLoginScreen: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.third_onboard),
            contentDescription = "First Onboard Image",
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f).padding(start = 32.dp, end = 32.dp)
        )
        Text(
            text = "Meditasi Mandiri",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0xFF3580FF),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp)
        )
        Text(
            text = "Nikmati Konten\nMenenangkan Dan\nPenuh Pembelajaran", // Menggabungkan teks dengan newline
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 31.sp,
            color = Color(0xFF002055),
            textAlign = TextAlign.Start,
            lineHeight = 48.sp, // Mengatur jarak antar baris
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.third_slidebar),
            contentDescription = "First Slide Bar",
            modifier = Modifier
                .height(16.dp)
                .width(72.dp)
                .padding(start = 32.dp)
        )
        Spacer(modifier = Modifier.height(44.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { navigateToLoginScreen() }, modifier = Modifier.padding(0.dp)) {
                Text(
                    text = "Mulai",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF002055)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdOnBoardScreenPreview(){
    ThirdOnBoardScreen({})
}