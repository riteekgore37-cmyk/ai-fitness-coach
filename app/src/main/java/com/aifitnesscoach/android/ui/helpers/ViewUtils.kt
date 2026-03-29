package com.aifitnesscoach.android.ui.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.aifitnesscoach.android.R
import java.net.URLEncoder

object ViewUtils {

    private const val PROXY_BASE =
        "https://ai-fitness-coach-backend-an8o.onrender.com/api/v1/proxy/image?url="

    /**
     * Rewrites ExerciseDB image URLs through our backend proxy
     * to bypass the regional 422 block in India.
     * All other URLs are loaded directly.
     */
    fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
        if (imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(R.drawable.baseline_broken_image_24)
                .centerCrop()
                .into(imageView)
            return
        }

        // Broaden the check to catch any exercisedb.io URL (http, https, different subdomains or paths)
        // while avoiding double-proxying if the URL is already proxied.
        val finalUrl = if (imageUrl.contains("exercisedb.io", ignoreCase = true) &&
            !imageUrl.contains("ai-fitness-coach-backend-an8o.onrender.com", ignoreCase = true)) {
            try {
                PROXY_BASE + URLEncoder.encode(imageUrl, "UTF-8")
            } catch (e: Exception) {
                imageUrl
            }
        } else {
            imageUrl
        }

        Glide.with(context)
            .load(finalUrl)
            .placeholder(R.drawable.baseline_broken_image_24)
            .error(R.drawable.baseline_broken_image_24)
            .centerCrop()
            .into(imageView)
    }
}
