package com.example.mentalguardians.utils

fun extractYouTubeVideoId(url: String): String? {
    val regex = Regex("v=([\\w-]+)")
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(1)?.value
}