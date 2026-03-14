package com.aifitnesscoach.android.ui.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.aifitnesscoach.android.R

object ViewUtils {

    fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        if (imageUrl.isBlank()) {
            imageView.setImageResource(R.drawable.baseline_broken_image_24)
            return
        }

        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.baseline_broken_image_24)
            .error(R.drawable.baseline_broken_image_24)
            .centerCrop()
            .into(imageView)
    }
}