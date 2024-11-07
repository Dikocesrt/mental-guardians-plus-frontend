package com.example.mentalguardians.data.retrofit

import com.example.mentalguardians.data.request.LoginRequest
import com.example.mentalguardians.data.request.MoodRequest
import com.example.mentalguardians.data.request.SignUpRequest
import com.example.mentalguardians.data.response.ContentResponse
import com.example.mentalguardians.data.response.DetailContentResponse
import com.example.mentalguardians.data.response.DetailTherapistResponse
import com.example.mentalguardians.data.response.GetMoodResponse
import com.example.mentalguardians.data.response.LoginSignUpResponse
import com.example.mentalguardians.data.response.MoodResponse
import com.example.mentalguardians.data.response.MusicResponse
import com.example.mentalguardians.data.response.ProfileResponse
import com.example.mentalguardians.data.response.TherapistResponse
import com.example.mentalguardians.data.response.VideoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/v1/register")
    suspend fun registerUser(@Body request: SignUpRequest): Response<LoginSignUpResponse>

    @POST("/v1/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginSignUpResponse>

    @GET("/v1/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @GET("/v1/videos")
    suspend fun getAllVideos(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("category") category: String
    ): Response<VideoResponse>

    @GET("/v1/articles")
    suspend fun getAllArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("category") category: String
    ): Response<ContentResponse>

    @GET("/v1/stories")
    suspend fun getAllStories(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("category") category: String
    ): Response<ContentResponse>

    @GET("/v1/therapists")
    suspend fun getAllTherapists(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("specialist") specialist: String
    ): Response<TherapistResponse>

    @GET("/v1/musics")
    suspend fun getAllMusics(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<MusicResponse>

    @GET("/v1/therapists/{id}")
    suspend fun getDetailTherapist(
        @Path("id") id: Int
    ): Response<DetailTherapistResponse>

    @GET("/v1/articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int
    ): Response<DetailContentResponse>

    @GET("/v1/stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: Int
    ): Response<DetailContentResponse>

    @POST("/v1/moods")
    suspend fun sendMood(@Body request: MoodRequest): Response<MoodResponse>

    @GET("/v1/moods")
    suspend fun getAllMoods(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<GetMoodResponse>
}