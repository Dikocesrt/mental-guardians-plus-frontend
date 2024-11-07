package com.example.mentalguardians.ui.article

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun ArticleScreen(articleViewModel: ArticleViewModel, categoryArticles: String, navigateToDetailArticleScreen: (Int) -> Unit) {

    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(Unit) {
        articleViewModel.removeAllList()
        articleViewModel.getAllArticles(
            onError = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            },
            category = categoryArticles
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
            items(articleViewModel.articleUIState.listContent) { item ->

                val createdAt = item.createdAt.substring(0, 10)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable {
                            navigateToDetailArticleScreen(item.id)
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
                                painter = rememberAsyncImagePainter(model = item.thumbnailURL),
                                contentDescription = "Image Thumbnail",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = item.author,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF848A94),
                            )
                            Text(
                                text = item.title,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF002055),
                            )
                            Text(
                                text = createdAt,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF848A94),
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(lazyColumnState) {
            snapshotFlow {
                lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.let { lastVisibleIndex ->
                    lastVisibleIndex >= articleViewModel.articleUIState.listContent.size - 3
                } ?: false
            }.collect { isEndReached ->
                if (!articleViewModel.articleUIState.isLoading && isEndReached && !articleViewModel.articleUIState.isLastPage) {
                    articleViewModel.getAllArticles(
                        onError = {errorMessage->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        },
                        category = categoryArticles
                    )
                }
            }
        }

        if (articleViewModel.articleUIState.isLoading) {
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