package com.example.mentalguardians.data.response

data class TherapistResponse(
    val status: Boolean,
    val message: String,
    val metadata: TherapistMetadata,
    val data: List<TherapistData>
)

data class DetailTherapistResponse(
    val status: Boolean,
    val message: String,
    val metadata: TherapistMetadata,
    val data: TherapistData
)

data class TherapistMetadata(
    val page: Int,
    val limit: Int
)

data class TherapistData(
    val id: Int,
    val name: String,
    val age: Int,
    val specialist: String,
    val photoURL: String,
    val phoneNumber: String,
    val gender: String,
    val experience: Int,
    val fee: Long,
    val practiceCity: String,
    val practiceLocation: String,
    val bachelorAlmamater: String,
    val bachelorGraduationYear: Int,
    val consultationMode: String
)