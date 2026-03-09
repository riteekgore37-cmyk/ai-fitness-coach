package com.aifitnesscoach.android.ui.workout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.databinding.ActivityTodayWorkoutBinding
import com.aifitnesscoach.android.ui.helpers.WorkoutData
import com.aifitnesscoach.android.ui.workout.adapters.WorkoutAdapter

class TodayWorkoutActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var binding: ActivityTodayWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        handleBackBtn()
        handleOnStartButton()
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val workoutData = WorkoutData.getTodayWorkout()
        val adapter = WorkoutAdapter(workoutData, this)
        binding.recyclerView.adapter = adapter
        setData()

        startButton = findViewById(R.id.startButton)
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val workoutData = WorkoutData.getTodayWorkout() ?: return

        var totalSets = 0
        for (i in workoutData.exercises) {
            totalSets += i.sets
        }
        binding.exerciseCount.text = "Exercises \n ${workoutData.total_number_exercises}"
        binding.setsCount.text = "Sets \n ${totalSets}"
        
        val duration = if (workoutData.exercises.isNotEmpty()) {
            workoutData.exercises[0].duration.toString()
        } else {
            "0"
        }
        binding.timeCount.text = "Duration \n $duration min"

        binding.dayNum.text = "Day " + workoutData.day_number
        binding.exerciseName.text = workoutData.day_type
    }

    private fun handleOnStartButton() {
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }
    }

    private fun handleBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}