package com.example.mentalguardians.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpDialog(homeViewModel: HomeViewModel, navigateToBadMoodScreen: () -> Unit, navigateToGoodMoodScreen: () -> Unit){

    val context = LocalContext.current

    Dialog(
        onDismissRequest = { homeViewModel.changeShowDialog(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10)) // Membulatkan sudut sebesar 10%
                .padding(start = 24.dp, end = 24.dp, bottom = 8.dp, top = 24.dp)
        ) {
            Column {
                Text(
                    text = "Deskripsikan kondisi anda saat ini",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = homeViewModel.HomeUIState.moodInput,
                    onValueChange = { homeViewModel.onMoodInputChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp),
                    shape = RoundedCornerShape(10),
                    label = {
                        Text(
                            text = "Deskripsikan kondisimu",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF848A94)
                        )
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { homeViewModel.changeShowDialog(false) }
                    ) {
                        Text(
                            text = "Batal",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            homeViewModel.sendMood(
                                onSuccess = {hasil->
                                    if(hasil){
                                        navigateToGoodMoodScreen()
                                    }else{
                                        navigateToBadMoodScreen()
                                    }
                                },
                                onError = {errorMessage->
                                    Toast.makeText(context, "Error = $errorMessage", Toast.LENGTH_LONG).show()
                                }
                            )
                            homeViewModel.onMoodInputChange("")
                            homeViewModel.changeShowDialog(false)
                        }
                    ) {
                        Text(
                            text = "Kirim",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}