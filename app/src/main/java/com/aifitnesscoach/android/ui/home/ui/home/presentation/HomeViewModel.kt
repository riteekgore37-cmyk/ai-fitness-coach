package com.aifitnesscoach.android.ui.home.ui.home.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.RetrofitService
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.home.ui.home.data.HomeRepositoryImpl
import com.aifitnesscoach.android.ui.home.ui.home.domain.models.HomePageResponse
import com.aifitnesscoach.android.ui.home.ui.home.domain.usecase.HomePageUseCase
import com.aifitnesscoach.android.ui.home.ui.home.domain.usecase.TodayInTakeUseCase
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private var apiService = RetrofitService.getApiService(getApplication())
    private var homeRepository = HomeRepositoryImpl(apiService)
    private var homeUseCase = HomePageUseCase(homeRepository)

    private var todayInTakeUseCase = TodayInTakeUseCase(homeRepository)

    private val _homeResponse = MutableStateFlow<ApiResult<HomePageResponse>?>(null)
    val homeResponse: StateFlow<ApiResult<BaseResponse>?> get() = _homeResponse

    private val _getTodayInTake = MutableStateFlow<ApiResult<TodayInTakeResponse>?>(null)
    val todayInTake: StateFlow<ApiResult<TodayInTakeResponse>?> get() = _getTodayInTake

    fun getUserHomePage(token: String) {
        viewModelScope.launch {
            val result = homeUseCase.invoke(token)
            _homeResponse.value = result
        }
    }

    fun getTodayInTake(token: String) {
        viewModelScope.launch {
            val result = todayInTakeUseCase.invoke(token)
            _getTodayInTake.value = result
        }
    }
}