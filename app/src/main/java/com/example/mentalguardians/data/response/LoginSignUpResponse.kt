package com.example.mentalguardians.data.response

data class LoginSignUpResponse(
    val status: Boolean,
    val message: String,
    val data: LoginSignUpData
)

data class LoginSignUpData(
    val token: String
)