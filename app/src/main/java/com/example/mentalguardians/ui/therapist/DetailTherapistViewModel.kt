package com.example.mentalguardians.ui.therapist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.TherapistData
import com.example.mentalguardians.data.response.TherapistResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class DetailTherapistUIState(
    val isLoading: Boolean = false,
    val therapistData: TherapistData = TherapistData(0, "", 0, "", "", "", "", 0, 0L, "", "", "", 0, "")
)

class DetailTherapistViewModel(private val apiClient: ApiClient): ViewModel() {
    var detailTherapistUIState by mutableStateOf(DetailTherapistUIState())
        private set

    fun getDetailTherapist(onError: (String) -> Unit, therapistId: Int){
        viewModelScope.launch{
            detailTherapistUIState = detailTherapistUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getDetailTherapist(id = therapistId)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        val therapistData = TherapistData(
                            id = successResponse.data.id,
                            name = successResponse.data.name,
                            age = successResponse.data.age,
                            specialist = successResponse.data.specialist,
                            photoURL = successResponse.data.photoURL,
                            phoneNumber = successResponse.data.phoneNumber,
                            gender = successResponse.data.gender,
                            experience = successResponse.data.experience,
                            fee = successResponse.data.fee,
                            practiceCity = successResponse.data.practiceCity,
                            practiceLocation = successResponse.data.practiceLocation,
                            bachelorAlmamater = successResponse.data.bachelorAlmamater,
                            bachelorGraduationYear = successResponse.data.bachelorGraduationYear,
                            consultationMode = successResponse.data.consultationMode
                        )
                        detailTherapistUIState = detailTherapistUIState.copy(therapistData = therapistData)
                    }else{
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, TherapistResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("VideoViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                detailTherapistUIState = detailTherapistUIState.copy(isLoading = false)
            }
        }
    }
}