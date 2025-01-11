package com.example.mentalguardians.ui.history

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalguardians.R
import com.example.mentalguardians.ui.main.MainViewModel
import com.example.mentalguardians.ui.theme.poppinsFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel, mainViewModel: MainViewModel){

    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()
    val scope: CoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        historyViewModel.clearList()
        historyViewModel.getAllHistory(
            onError = {errorMessage->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        )
    }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    LaunchedEffect(modalSheetState.isVisible) {
        if (!modalSheetState.isVisible) {
            mainViewModel.onModalBottomVisibilityChange(false)
            historyViewModel.clearDetail()
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.White)
            ){
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = historyViewModel.historyUIState.detailHistory.date,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black,
                            )
                            Text(
                                text = historyViewModel.historyUIState.detailHistory.time,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black,
                            )
                        }
                        Image(
                            painter = if(historyViewModel.historyUIState.detailHistory.isGood) painterResource(id = R.drawable.goodmood) else painterResource(id = R.drawable.badmood),
                            contentDescription = "Image Thumbnail",
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        item{
                            Text(
                                text = historyViewModel.historyUIState.detailHistory.content,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Riwayat",
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF002055),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 62.dp)
                        .background(
                            Color.White
                        ),
                    state = lazyColumnState
                ) {
                    items(historyViewModel.historyUIState.listContent) { item ->

                        val dateCreatedAt = item.createdAt.substring(0, 10)
                        val tahun = dateCreatedAt.substring(0, 4)
                        val bulan = dateCreatedAt.substring(5, 7)

                        var convertedBulan = bulan
                        when (bulan) {
                            "01" -> convertedBulan = "Januari"
                            "02" -> convertedBulan = "Februari"
                            "03" -> convertedBulan = "Maret"
                            "04" -> convertedBulan = "April"
                            "05" -> convertedBulan = "Mei"
                            "06" -> convertedBulan = "Juni"
                            "07" -> convertedBulan = "Juli"
                            "08" -> convertedBulan = "Agustus"
                            "09" -> convertedBulan = "September"
                            "10" -> convertedBulan = "Oktober"
                            "11" -> convertedBulan = "November"
                            "12" -> convertedBulan = "Desember"
                        }

                        val tanggal = dateCreatedAt.substring(8, 10)

                        val myDate = "$tanggal $convertedBulan $tahun"

                        val timeString = item.createdAt.substring(11, 16)

                        // Formatter untuk mem-parsing string waktu
                        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

                        // Parse waktu sebagai LocalTime dalam GMT+0
                        val utcTime = LocalTime.parse(timeString, timeFormatter)

                        // Konversi waktu dari GMT+0 ke GMT+7
                        val zonedUtcTime =
                            utcTime.atDate(java.time.LocalDate.now()).atZone(ZoneOffset.UTC)
                        val wibTime = zonedUtcTime.withZoneSameInstant(ZoneOffset.ofHours(7))

                        // Format waktu dalam zona GMT+7 (WIB)
                        val formattedWibTime = wibTime.format(timeFormatter)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        historyViewModel.setDetail(
                                            id = item.id,
                                            content = item.content,
                                            isGood = item.isGood,
                                            date = myDate,
                                            time = formattedWibTime
                                        )
                                        mainViewModel.onModalBottomVisibilityChange(true)
                                        modalSheetState.show()
                                    }
                                }
                                .drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    drawLine(
                                        color = Color(0xFF848A94), // Warna border
                                        start = Offset(0f, size.height), // Mulai dari kiri bawah
                                        end = Offset(size.width, size.height), // Hingga kanan bawah
                                        strokeWidth = strokeWidth
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = 16.dp, top = 12.dp),
                                text = myDate,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Color(0xFF848A94),
                            )
                            Row(
                                modifier = Modifier.padding(vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = formattedWibTime,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = Color(0xFF848A94),
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF3580FF)
                                )
                            }
                        }
                    }
                    if (historyViewModel.historyUIState.isLoadingLoadMore) {
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
                            lastVisibleIndex >= historyViewModel.historyUIState.listContent.size - 3
                        } ?: false
                    }.collect { isEndReached ->
                        if (!historyViewModel.historyUIState.isLoading && isEndReached && !historyViewModel.historyUIState.isLastPage) {
                            historyViewModel.getAllHistory(
                                onError = { errorMessage ->
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    }
                }
            }

            if (historyViewModel.historyUIState.isLoading) {
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
}