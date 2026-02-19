package com.aifitnesscoach.android.ui.onboarding.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifitnesscoach.android.ui.onboarding.models.LoginResponse
import com.aifitnesscoach.android.ui.onboarding.models.RequestModels.RegisterRequest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // ---------------- LOGIN ----------------

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse: LiveData<Response<LoginResponse>> = _loginResponse

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(email, password)
                _loginResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()

                // Prevent crash
                val errorBody = "Network Error"
                    .toResponseBody("text/plain".toMediaTypeOrNull())

                _loginResponse.value = Response.error(500, errorBody)
            }
        }
    }

    // ---------------- REGISTER ----------------

    private val _registerResponse = MutableLiveData<Response<LoginResponse>>()
    val registerResponse: LiveData<Response<LoginResponse>> = _registerResponse

    fun registerUser(registerRequest: RegisterRequest) {

        viewModelScope.launch {

            try {

                val response = userRepository.registerUser(registerRequest)
                _registerResponse.value = response

            } catch (e: Exception) {

                e.printStackTrace()

                // Prevent crash (Render timeout etc.)
                val errorBody = "Network Error"
                    .toResponseBody("text/plain".toMediaTypeOrNull())

                _registerResponse.value = Response.error(500, errorBody)
            }
        }
    }
}
