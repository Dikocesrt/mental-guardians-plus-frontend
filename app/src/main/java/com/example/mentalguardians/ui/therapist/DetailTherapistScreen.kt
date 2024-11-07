package com.example.mentalguardians.ui.therapist

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mentalguardians.R
import com.example.mentalguardians.ui.theme.poppinsFontFamily
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailTherapistScreen(detailTherapistViewModel: DetailTherapistViewModel, id: Int){
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        detailTherapistViewModel.getDetailTherapist(
            onError = {errorMessage->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            },
            therapistId = id
        )
    }

    val therapistData = detailTherapistViewModel.detailTherapistUIState.therapistData

    var specialist = ""

    when (therapistData.specialist) {
        "bullying" -> specialist = "Perundungan/Bullying"
        "trauma" -> specialist = "Trauma"
        "family" -> specialist = "Permasalahan Keluarga"
        "school" -> specialist = "Permasalahan Akademik"
        "love" -> specialist = "Romansa/Percintaan"
        "finance" -> specialist = "Permasalahan Finansial"
        else -> specialist = ""
    }

    if(detailTherapistViewModel.detailTherapistUIState.isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 64.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = therapistData.photoURL),
                    contentDescription = "Image Thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = therapistData.name,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color(0xFF1A1A1A),
                        )
                        Text(
                            text = "Sp. $specialist",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Color(0xFF5B5F67),
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Rp. ${
                                NumberFormat.getNumberInstance(Locale("id", "ID"))
                                    .format(therapistData.fee)
                            }",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF002055),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Per Jam",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF002055)
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "About",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF002055),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        backgroundColor = Color(0xFF3580FF),
                        shape = RoundedCornerShape(16)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pengalaman),
                            contentDescription = null,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${therapistData.experience} Tahun",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF5B5F67),
                        )
                        Text(
                            text = "Pengalaman",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF5B5F67),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        backgroundColor = Color(0xFF3580FF),
                        shape = RoundedCornerShape(16)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alumnus),
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${therapistData.bachelorAlmamater} (${therapistData.bachelorGraduationYear})",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF5B5F67),
                        )
                        Text(
                            text = "Alumnus",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF5B5F67),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        backgroundColor = Color(0xFF3580FF),
                        shape = RoundedCornerShape(16)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.lokasi),
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${therapistData.practiceLocation}, ${therapistData.practiceCity}",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF5B5F67),
                        )
                        Text(
                            text = "Lokasi Praktik",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF5B5F67),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        backgroundColor = Color(0xFF3580FF),
                        shape = RoundedCornerShape(16)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.umur),
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${therapistData.age} Tahun",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF5B5F67),
                        )
                        Text(
                            text = "Umur",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF5B5F67),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        backgroundColor = Color(0xFF3580FF),
                        shape = RoundedCornerShape(16)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.layanan),
                            contentDescription = null,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (therapistData.consultationMode == "both") "Online & Offline" else therapistData.consultationMode.replaceFirstChar { it.uppercase() },
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF5B5F67),
                        )
                        Text(
                            text = "Layanan Konsultasi",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF5B5F67),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3580FF)),
                    onClick = {
                        val linkURL = "https://wa.me/${therapistData.phoneNumber}"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkURL))
                        context.startActivity(intent)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.whatsapp),
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hubungi Psikiater",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}