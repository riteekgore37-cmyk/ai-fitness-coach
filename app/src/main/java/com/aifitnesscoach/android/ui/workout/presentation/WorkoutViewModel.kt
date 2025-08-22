package com.aifitnesscoach.android.ui.workout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.RetrofitService
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.workout.data.WorkoutRepositoryImpl
import com.aifitnesscoach.android.ui.workout.domain.usecase.MarkWorkoutDoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val apiService = RetrofitService.createService()
    private val workoutRepository = WorkoutRepositoryImpl(apiService)
    private val markWorkoutDoneUseCase = MarkWorkoutDoneUseCase(workoutRepository)

    private val _workoutStatus = MutableStateFlow<ApiResult<BaseResponse>?>(null)
    val workoutStatus: StateFlow<ApiResult<BaseResponse>?> get() = _workoutStatus

    fun markWorkoutDone(myWorkoutId: String, week: Int, day: Int, token: String) {
        viewModelScope.launch {
            val result = markWorkoutDoneUseCase(myWorkoutId, week, day, token)
            _workoutStatus.value = result
        }
    }
}
