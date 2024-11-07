package com.example.mentalguardians.data.response

// response untuk post mood

data class MoodResponse(
    val status: Boolean,
    val message: String,
    val data: MoodData
)

data class MoodData(
    val isGood: Boolean
)

// response untuk history

data class GetMoodResponse(
    val status: Boolean,
    val message: String,
    val metadata: GetMoodMetadata,
    val data: List<GetMoodData>
)

data class GetMoodMetadata(
    val page: Int,
    val limit: Int
)

data class GetMoodData(
    val id: Int,
    val content: String,
    val isGood: Boolean,
    val createdAt: String
)

data class DetailMoodData(
    val id: Int,
    val content: String,
    val isGood: Boolean,
    val date: String,
    val time: String
)