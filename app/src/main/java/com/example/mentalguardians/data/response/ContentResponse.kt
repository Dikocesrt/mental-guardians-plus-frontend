package com.example.mentalguardians.data.response

data class ContentResponse(
    val status: Boolean,
    val message: String,
    val metadata: ContentMetadata,
    val data: List<ContentData>
)

data class ContentMetadata(
    val page: Int,
    val limit: Int
)

data class DetailContentResponse(
    val status: Boolean,
    val message: String,
    val metadata: ContentMetadata,
    val data: ContentData
)

data class ContentData(
    val id: Int,
    val title: String,
    val author: String,
    val content: String,
    val category: String,
    val type: String,
    val thumbnailURL: String,
    val createdAt: String
)