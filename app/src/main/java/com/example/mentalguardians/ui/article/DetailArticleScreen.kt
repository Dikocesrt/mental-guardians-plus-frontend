package com.example.mentalguardians.ui.article

import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.rememberAsyncImagePainter
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun DetailArticleScreen(detailArticleViewModel: DetailArticleViewModel, id: Int) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        detailArticleViewModel.getDetailArticle(
            onError = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            },
            articleID = id
        )
    }

    if (detailArticleViewModel.detailArticleUIState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 64.dp)
        ) {
            item {
                Image(
                    painter = rememberAsyncImagePainter(model = detailArticleViewModel.detailArticleUIState.articleData.thumbnailURL),
                    contentDescription = "Image Thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    text = detailArticleViewModel.detailArticleUIState.articleData.title,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A1A),
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                // Menggunakan AndroidView untuk mendukung rendering HTML
                AndroidView(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    factory = { context ->
                        TextView(context).apply {
                            text = HtmlCompat.fromHtml(
                                detailArticleViewModel.detailArticleUIState.articleData.content,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                            textSize = 12f
                            setTextColor(android.graphics.Color.BLACK)
                        }
                    }
                )
            }
        }
    }
}
