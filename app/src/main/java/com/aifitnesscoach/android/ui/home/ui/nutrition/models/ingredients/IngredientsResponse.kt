package com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients

import com.aifitnesscoach.android.network.models.BaseResponse

data class IngredientsResponse(
    val `data`: List<Data>,
    val meta: Meta,

    ) : BaseResponse()