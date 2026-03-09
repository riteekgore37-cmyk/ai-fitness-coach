package com.aifitnesscoach.android.ui.onboarding.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.ui.home.HomeActivity
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // MotionLayout starts blinking automatically—no extra code needed.

        handler.postDelayed({
            if (!UserPrefUtil.isUserLoggedIn(this)) {
                startActivity(Intent(this, WelcomeScreenActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }, 3000) // wait 3s before moving on
    }
}
