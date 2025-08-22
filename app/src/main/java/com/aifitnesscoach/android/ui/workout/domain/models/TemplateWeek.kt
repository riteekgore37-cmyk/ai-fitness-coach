package com.aifitnesscoach.android.ui.workout.domain.models

data class TemplateWeek(
    val days: List<Day>,
    val week_description: String,
    val week_name: String,
    val week_number: Int
)