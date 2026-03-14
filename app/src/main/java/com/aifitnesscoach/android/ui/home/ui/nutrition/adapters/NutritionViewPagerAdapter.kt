package com.aifitnesscoach.android.ui.home.ui.nutrition.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.databinding.DailyRoutineViewBinding
import com.aifitnesscoach.android.databinding.PlansViewBinding
import com.aifitnesscoach.android.ui.helpers.NutritionHelper
import com.aifitnesscoach.android.ui.home.ui.nutrition.OnMealClickListener
import com.aifitnesscoach.android.ui.home.ui.nutrition.OnPlanItemClickListener
import com.aifitnesscoach.android.ui.home.ui.nutrition.activities.AboutNutritionPlanActivity
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.NutritionDataModel
import kotlin.math.roundToInt


class NutritionViewPagerAdapter(
    private val context: Context,
    private val listener: OnMealClickListener,
    private val todayMealsResponse: TodayMealsResponse,
    private val todayInTakeResponse: TodayInTakeResponse,
    private val allMealsResponse: AllMealsPlansResponse,
    private val myMealsResponse: MyMealPlanResponse?   // nullable — user may not be enrolled
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnPlanItemClickListener {

    // Walks the entire view hierarchy and disables LayoutTransition parent-hierarchy
    // animation, which would otherwise crash ViewPager2 during swipe.
    private fun disableLayoutTransitions(view: View) {
        if (view is ViewGroup) {
            view.layoutTransition?.setAnimateParentHierarchy(false)
            for (i in 0 until view.childCount) {
                disableLayoutTransitions(view.getChildAt(i))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> DailyRoutineViewHolder(
                DailyRoutineViewBinding.inflate(inflater, parent, false)
            )
            1 -> PlansViewHolder(
                PlansViewBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailyRoutineViewHolder -> holder.bind(listener)
            is PlansViewHolder -> holder.bind(context)
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int = position

    inner class DailyRoutineViewHolder(private val binding: DailyRoutineViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(listener: OnMealClickListener) {
            disableLayoutTransitions(binding.root)
            updateProgressBars(todayInTakeResponse)
            setOnClickListeners(listener)
            initRecyclerView()
        }

        private fun updateProgressBars(model: TodayInTakeResponse) {
            val data = model.data
            updateProgressBar(
                binding.calProgressBar,
                data.caloriesGoal.roundToInt(),
                data.caloriesIntake.roundToInt()
            )
            binding.calValue.text = data.caloriesLeft.toString()

            updateMacroProgressBar(
                binding.carbsProgressBar, binding.carbsValue,
                data.carbsGoal.roundToInt(), data.carbsConsumed.roundToInt(), "g"
            )
            updateMacroProgressBar(
                binding.proteinProgressBar, binding.proteinValue,
                data.proteinGoal.roundToInt(), data.proteinConsumed.roundToInt(), "g"
            )
            updateMacroProgressBar(
                binding.fatsProgressBar, binding.fatsValue,
                data.fatGoal.roundToInt(), data.fatConsumed.roundToInt(), "g"
            )

            updateProgressBar(
                binding.burnedProgressBar,
                data.caloriesGoal.roundToInt(),
                data.caloriesBurned.roundToInt()
            )
            binding.burnedCal.text = "${data.caloriesBurned} Kcal"

            val intakedCalories = data.caloriesIntake.roundToInt()
            updateProgressBar(binding.intakedProgressBar, data.caloriesGoal.roundToInt(), intakedCalories)
            binding.intakedCal.text = "${intakedCalories} Kcal"
        }

        private fun updateProgressBar(progressBar: ProgressBar, max: Int, progress: Int) {
            progressBar.max = max
            progressBar.progress = progress
        }

        private fun updateProgressBar(progressBar: CircularProgressBar, max: Int, progress: Int) {
            progressBar.progressMax = max.toFloat()
            progressBar.progress = progress.toFloat()
        }

        private fun updateMacroProgressBar(
            progressBar: ProgressBar, valueTextView: TextView,
            goal: Int, consumed: Int, unit: String
        ) {
            valueTextView.text = "$consumed/$goal$unit"
            progressBar.max = goal
            progressBar.progress = consumed
        }

        private fun initRecyclerView() {
            binding.recycleView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val days = todayMealsResponse.data?.days
            if (days.isNullOrEmpty()) {
                binding.recycleView.adapter = TodayMealAdapter(context, emptyList())
                return
            }

            val mealTypeOrder = listOf("Breakfast", "Lunch", "Dinner", "Snacks")
            val sortedMealList = days[0].meals.sortedBy { meal -> mealTypeOrder.indexOf(meal.type) }
            binding.recycleView.adapter = TodayMealAdapter(context, sortedMealList)
        }

        private fun setOnClickListeners(listener: OnMealClickListener) {
            binding.lunchView.setOnClickListener { listener.onMailClick("lunch") }
            binding.breakFastView.setOnClickListener { listener.onMailClick("breakfast") }
            binding.snackView.setOnClickListener { listener.onMailClick("snack") }
            binding.dinnerView.setOnClickListener { listener.onMailClick("dinner") }
        }
    }

    inner class PlansViewHolder(private val binding: PlansViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var nutritionMealAdapter: NutritionMealAdapter

        fun bind(context: Context) {
            disableLayoutTransitions(binding.root)
            showPlanDetails()
            initNutritionMealAdapter()
            initSpinner()
        }

        private fun initSpinner() {
            val plans = arrayOf("My plans", "Other plans")
            val adapter = ArrayAdapter(context, R.layout.item_nutrition_spinner, plans)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerPlans.adapter = adapter
            binding.spinnerPlans.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        if (plans[position] == "My plans") {
                            binding.nutritionPlanCardView.visibility = View.VISIBLE
                            binding.availablePlan.visibility = View.GONE
                            initNutritionMealAdapter()
                        } else {
                            binding.nutritionPlanCardView.visibility = View.GONE
                            binding.availablePlan.visibility = View.VISIBLE
                            initPlansPrograms()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }

        private fun showPlanDetails() {
            if (myMealsResponse == null) {
                // User not enrolled in a meal plan — hide the plan card
                binding.nutritionPlanCardView.visibility = View.GONE
                return
            }

            binding.nutritionPlanCardView.visibility = View.VISIBLE
            binding.nutritionPlanCardView.setOnClickListener {
                val intent = Intent(context, AboutNutritionPlanActivity::class.java)
                NutritionHelper.selectedMyProgram = myMealsResponse.data.meal_plan
                context.startActivity(intent)
            }

            try {
                binding.standardPlanTextView.text = myMealsResponse.data.meal_plan.level
                binding.desStandardPlanTextView.text = myMealsResponse.data.meal_plan.description
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun initPlansPrograms() {
            binding.recycleView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val data = allMealsResponse.data.asReversed()
            val programsAdapter = NutritionProgramsAdapter(data, this@NutritionViewPagerAdapter)
            binding.recycleView.adapter = programsAdapter
        }

        private fun initNutritionMealAdapter() {
            binding.recycleView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val dataList = prepareData()
            nutritionMealAdapter = NutritionMealAdapter(dataList, context)
            binding.recycleView.adapter = nutritionMealAdapter
            binding.recycleView.isNestedScrollingEnabled = false
        }
    }

    private fun prepareData(): List<NutritionDataModel> {
        // If no meal plan enrolled, return empty list
        val days = myMealsResponse?.data?.days ?: return emptyList()

        val dayNames =
            listOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

        val dataList = mutableListOf<NutritionDataModel>()
        var pos = 0
        for (day in dayNames) {
            if (pos >= days.size) break
            dataList.add(NutritionDataModel(day, days[pos++].meals))
        }
        return dataList
    }

    override fun onPlanItemClick(data: com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.all_meals_plan.Data) {
        val intent = Intent(context, AboutNutritionPlanActivity::class.java)
        intent.putExtra("isAddProgram", true)
        intent.putExtra("planId", data.id)
        NutritionHelper.selectedProgram = data
        context.startActivity(intent)
    }
}