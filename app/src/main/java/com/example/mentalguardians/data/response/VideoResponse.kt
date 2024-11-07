package com.example.mentalguardians.data.response

data class VideoResponse(
    val status: Boolean,
    val message: String,
    val metadata: VideoMetadata,
    val data: List<VideoData>
)

data class VideoMetadata(
    val page: Int,
    val limit: Int
)

data class VideoData(
    val id: String,
    val videoID: String,
    val title: String,
    val author: String,
    val views: Long,
    val likes: Long,
    val labels: String
)