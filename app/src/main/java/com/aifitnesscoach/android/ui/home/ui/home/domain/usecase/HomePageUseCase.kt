package com.aifitnesscoach.android.ui.home.ui.home.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.home.domain.HomeRepository
import com.aifitnesscoach.android.ui.home.ui.home.domain.models.HomePageResponse

class HomePageUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(token: String): ApiResult<HomePageResponse> {
        return homeRepository.getHomePage(token)
    }
}