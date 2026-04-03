package com.aifitnesscoach.android.ui.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.aifitnesscoach.android.R

object ViewUtils {

    fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
        if (imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(R.drawable.baseline_broken_image_24)
                .centerCrop()
                .into(imageView)
            return
        }

        // Handle comma-separated URLs (muscle images) - take the last valid URL
        val finalUrl = if (imageUrl.contains(",")) {
            imageUrl.split(",").lastOrNull { it.trim().startsWith("http") }?.trim() ?: imageUrl
        } else {
            imageUrl
        }

        Glide.with(context)
            .load(finalUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.baseline_broken_image_24)
            .error(R.drawable.baseline_broken_image_24)
            .centerCrop()
            .into(imageView)
    }
}