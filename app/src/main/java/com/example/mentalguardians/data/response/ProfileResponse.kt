package com.example.mentalguardians.data.response

data class ProfileResponse(
    val status: Boolean,
    val message: String,
    val data: ProfileData
)

data class ProfileData(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String
)
