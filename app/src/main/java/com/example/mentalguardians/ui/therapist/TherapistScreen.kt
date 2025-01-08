package com.example.mentalguardians.ui.therapist

import android.util.Log
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun TherapistScreen(therapistViewModel: TherapistViewModel, specialist: String, navigateToDetailTherapistScreen: (Int) -> Unit) {

    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()

    Log.d("TherapistScreen", "Specialist = $specialist")

    LaunchedEffect(Unit) {
        therapistViewModel.clearList()
        therapistViewModel.getAllTherapists(
            onError = {errorMessage->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            },
            specialist = specialist
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 64.dp, bottom = 48.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyColumnState
        ) {
            items(therapistViewModel.therapistUIState.listContent) { item ->

                var specialist = ""

                when (item.specialist) {
                    "personality" -> specialist = "Gangguan Kepribadian"
                    "trauma" -> specialist = "Trauma"
                    "family" -> specialist = "Permasalahan Keluarga"
                    "self care" -> specialist = "Pengembangan Diri"
                    "love" -> specialist = "Romansa/Percintaan"
                    "parenting" -> specialist = "Parenting/Pengasuhan"
                    else -> specialist = ""
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable {
                            navigateToDetailTherapistScreen(item.id)
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
//                                painter = painterResource(id = R.drawable.dummyimage),
                                painter = rememberAsyncImagePainter(model = item.photoURL),
                                contentDescription = "Image Thumbnail",
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if(item.gender == "male") "Pria" else "Wanita",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF002055),
                            )
                            Text(
                                text = item.name,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF002055),
                            )
                            Text(
                                text = "Spesialis $specialist",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF002055),
                            )
                            Text(
                                text = if (item.consultationMode == "both") "Online | Offline" else item.consultationMode.replaceFirstChar { it.uppercase() },
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF848A94),
                            )
                        }
                    }
                }
            }
            if (therapistViewModel.therapistUIState.isLoadingLoadMore) {
                item {
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

        LaunchedEffect(lazyColumnState) {
            snapshotFlow {
                lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.let { lastVisibleIndex ->
                    lastVisibleIndex >= therapistViewModel.therapistUIState.listContent.size - 3
                } ?: false
            }.collect { isEndReached ->
                if (!therapistViewModel.therapistUIState.isLoading && isEndReached && !therapistViewModel.therapistUIState.isLastPage) {
                    therapistViewModel.getAllTherapists(
                        onError = {errorMessage->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        },
                        specialist = specialist
                    )
                }
            }
        }

        if (therapistViewModel.therapistUIState.isLoading) {
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