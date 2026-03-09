package com.aifitnesscoach.android.network

import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.home.ui.home.domain.models.HomePageResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.PlanBody
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.AddCustomMealBody
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse
import com.aifitnesscoach.android.ui.home.ui.workouts.models.Workout
import com.aifitnesscoach.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse
import com.aifitnesscoach.android.ui.onboarding.models.LoginResponse
import com.aifitnesscoach.android.ui.onboarding.models.RequestModels.LoginRequest
import com.aifitnesscoach.android.ui.onboarding.models.RequestModels.RegisterRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/api/v1/user/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/user/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<LoginResponse>

    // HOME
    @GET("/api/v1/user/homePage")
    suspend fun getHomePage(): Response<HomePageResponse>

    @GET("/api/v1/user/homePage/your-daily-intake")
    suspend fun getHomePageTodayInTake(): Response<TodayInTakeResponse>

    // WORKOUT PLAN
    @GET("/api/v1/user/myWorkouts/{id}")
    suspend fun getPlanPage(@Path("id") myWorkoutId: String): Response<PlanPageResponse>

    @PATCH("/api/v1/user/myWorkouts/{id}/progress/{week}/{day}")
    suspend fun markDoneWorkout(
        @Path("id") myWorkoutId: String,
        @Path("week") week: Int,
        @Path("day") day: Int
    ): Response<BaseResponse>

    @GET("/api/v1/user/workouts?limit=200")
    suspend fun getWorkoutPrograms(): Response<WorkoutProgramsResponse>

    @POST("/api/v1/user/myWorkouts")
    suspend fun enrollWorkoutProgram(@Body workoutId: Workout): Response<BaseResponse>

    // CUSTOM WORKOUT
    @GET("/api/v1/user/templates?limit=200")
    suspend fun getCustomWorkouts(): Response<CustomWorkoutResponse>

    @POST("/api/v1/user/templates")
    suspend fun createCustomWorkout(
        @Body customWorkoutRequest: CreateCustomWorkoutRequest
    ): Response<CreateCustomWorkoutResponse>

    // EXERCISES
    @GET("/api/v1/user/exercises")
    suspend fun getExercises(
        @Query("filterName") filterCat: String,
        @Query("filterVal") filterVal: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<ExercisesResponse>

    @GET("/api/v1/user/exercises/search")
    suspend fun getExercisesSearch(
        @Query("searchTerm") search: String,
        @Query("filter") filter: String
    ): Response<ExercisesResponse>

    // NUTRITION
    @GET("/api/v1/user/nutri-guide/today-meals")
    suspend fun getTodayMeals(): Response<TodayMealsResponse>

    @GET("/api/v1/user/nutri-guide/daily-goals")
    suspend fun getDailyGoals(): Response<DailyGoalsResponse>

    @GET("/api/v1/user/nutri-guide/todays-intake")
    suspend fun getTodayInTake(): Response<TodayInTakeResponse>

    @GET("/api/v1/user/myMealPlan")
    suspend fun getMyMealPlan(): Response<MyMealPlanResponse>

    @GET("/api/v1/user/mealPlans?limit=200")
    suspend fun getAllMealsPlan(): Response<AllMealsPlansResponse>

    // INGREDIENTS
    @GET("/api/v1/user/ingredients")
    suspend fun getIngredients(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<IngredientsResponse>

    @GET("/api/v1/user/ingredients/search?limit=50")
    suspend fun searchIngredients(
        @Query("searchTerm") search: String
    ): Response<IngredientsResponse>

    @POST("/api/v1/user/meals/eat-custom-meal")
    suspend fun addCustomMeal(
        @Body data: AddCustomMealBody
    ): Response<BaseResponse>

    @POST("/api/v1/user/myMealPlan")
    suspend fun enrollIntoPlanProgram(
        @Body data: PlanBody
    ): Response<BaseResponse>
}