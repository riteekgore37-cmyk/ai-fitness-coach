package com.aifitnesscoach.android.ui.home.ui.plan.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.ApiService
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.IngredientsPagingSource
import com.aifitnesscoach.android.ui.home.ui.nutrition.PlanBody
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.AddCustomMealBody
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse
import kotlinx.coroutines.flow.Flow

class NutritionRepositoryImp(private val apiService: ApiService) : NutritionRepository {

    override suspend fun getTodayMeal(
        token: String
    ): ApiResult<TodayMealsResponse> {

        return try {
            val response = apiService.getTodayMeals()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, TodayMealsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getDailyGoals(
        token: String
    ): ApiResult<DailyGoalsResponse> {

        return try {
            val response = apiService.getDailyGoals()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, DailyGoalsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getTodayInTake(token: String): ApiResult<TodayInTakeResponse> {

        return try {
            val response = apiService.getTodayInTake()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, TodayInTakeResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getMyMealPlan(token: String): ApiResult<MyMealPlanResponse> {
        return try {
            val response = apiService.getMyMealPlan()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, MyMealPlanResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getAllMealsPlans(token: String): ApiResult<AllMealsPlansResponse> {
        return try {
            val response = apiService.getAllMealsPlan()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, AllMealsPlansResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getAllIngredients(
        token: String, page: Int, limit: Int
    ): ApiResult<IngredientsResponse> {
        return try {
            val response = apiService.getIngredients( page, limit)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, IngredientsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun searchIngredients(
        token: String, searchTerm: String,
    ): ApiResult<IngredientsResponse> {
        return try {
            val response = apiService.searchIngredients(searchTerm)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, IngredientsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun addCustomMeal(
        token: String,
        data: AddCustomMealBody
    ): ApiResult<BaseResponse> {
        return try {
            val response = apiService.addCustomMeal(data)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, BaseResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun enrollIntoPlan(token: String, planId: PlanBody): ApiResult<BaseResponse> {
        return try {
            val response = apiService.enrollIntoPlanProgram( planId)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, BaseResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }


    fun getIngredientsPagingData(
        token: String
    ): Flow<PagingData<com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients.Data>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                IngredientsPagingSource(apiService, token)
            }).flow
    }


}
