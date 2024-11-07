package com.example.mentalguardians.data.response

data class MusicResponse(
    val status: Boolean,
    val message: String,
    val metadata: MusicMetadata,
    val data: List<MusicData>
)

data class MusicMetadata(
    val page: Int,
    val limit: Int
)

data class MusicData(
    val id: Int,
    val title: String,
    val singer: String,
    val musicURL: String,
    val thumbnailURL: String
)