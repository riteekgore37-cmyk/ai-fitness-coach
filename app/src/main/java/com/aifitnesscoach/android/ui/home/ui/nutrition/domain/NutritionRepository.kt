package com.aifitnesscoach.android.ui.home.ui.nutrition.domain

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.PlanBody
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.AddCustomMealBody
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse

interface NutritionRepository {

    suspend fun getTodayMeal(token: String): ApiResult<TodayMealsResponse>
    suspend fun getDailyGoals(token: String): ApiResult<DailyGoalsResponse>
    suspend fun getTodayInTake(token: String): ApiResult<TodayInTakeResponse>
    suspend fun getMyMealPlan(token: String): ApiResult<MyMealPlanResponse>

    suspend fun getAllMealsPlans(token: String): ApiResult<AllMealsPlansResponse>
    suspend fun getAllIngredients(
        token: String,
        page: Int,
        limit: Int
    ): ApiResult<IngredientsResponse>

    suspend fun searchIngredients(
        token: String,
        searchTerm: String,
    ): ApiResult<IngredientsResponse>

    suspend fun addCustomMeal(
        token: String,
        data: AddCustomMealBody,
    ): ApiResult<BaseResponse>

    suspend fun enrollIntoPlan(token: String, planId: PlanBody): ApiResult<BaseResponse>


}