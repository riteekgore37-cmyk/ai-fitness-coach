package com.aifitnesscoach.android.ui.workout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aifitnesscoach.android.databinding.ActivityWeeklyWorkoutBinding
import com.aifitnesscoach.android.ui.helpers.WorkoutData
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.Day
import com.aifitnesscoach.android.ui.workout.adapters.DaysTimeLineAdapter
import com.aifitnesscoach.android.ui.workout.adapters.WorkoutAdapter

class WeeklyWorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeeklyWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeeklyWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleBackButton()
        setRecycleData()
        setData()
        setTimeLineRecycler()
        handleOnStartButton()
    }

    private fun handleBackButton() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val workoutData = WorkoutData.getTodayWorkout() ?: return
        val currentWeek = WorkoutData.getCurrentWeek() ?: return

        var totalSets = 0
        for (i in workoutData.exercises) {
            totalSets += i.sets
        }

        binding.weekTitle.text =
            "${currentWeek.week_number}: ${currentWeek.week_name}"
        binding.weekDesc.text = currentWeek.week_description
        binding.dayDetails.text =
            "Day ${workoutData.day_number} / ${WorkoutData.getWeekDaysCount()} - ${workoutData.day_type}"
        binding.exerciseCount.text = "Exercises \n ${workoutData.total_number_exercises}"
        binding.setsCount.text = "Sets \n ${totalSets}"
        
        val duration = if (workoutData.exercises.isNotEmpty()) {
            workoutData.exercises[0].duration.toString()
        } else {
            "0"
        }
        binding.expectedTime.text = "Duration \n $duration min"
    }

    private fun setTimeLineRecycler() {
        binding.timelineRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val days = getTheDays()
        if (days != null) {
            val adapter = DaysTimeLineAdapter(days)
            binding.timelineRecyclerView.adapter = adapter
        }
    }

    private fun setRecycleData() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter = WorkoutAdapter(WorkoutData.getTodayWorkout(), this)
        binding.recyclerView.adapter = adapter
    }

    private fun handleOnStartButton() {
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }
    }

    private fun getTheDays(): ArrayList<Day>? {
        val currentWeek = WorkoutData.getCurrentWeek() ?: return null
        val days: ArrayList<Day> = ArrayList(currentWeek.days)
        if (days.isNotEmpty() && days[days.size - 1].day_number != 99) {
            val dummyDay = Day(
                day_number = 99,
                day_type = "Dummy Day",
                exercises = emptyList(),
                is_done = false,
                total_number_exercises = 0
            )

            days.add(dummyDay)
        }
        return days
    }

}