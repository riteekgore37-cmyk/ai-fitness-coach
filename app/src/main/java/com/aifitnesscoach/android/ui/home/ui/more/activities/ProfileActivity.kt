package com.aifitnesscoach.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.ui.onboarding.activities.SplashActivity
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // ✅ Edit Profile Navigation
        val editTextView = findViewById<View>(R.id.editTextView)
        editTextView.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // ✅ Logout Button
        val logoutBtn = findViewById<View>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            // Clear user session
            UserPrefUtil.saveUserData(this, null)
            UserPrefUtil.setUserLoggedIn(this, false)

            // Go to Splash/Login screen
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // ✅ Achievement buttons (3 icons)
        val achievement1 = findViewById<ImageButton>(R.id.achievementsView1)
        val achievement2 = findViewById<ImageButton>(R.id.achievementsView2)
        val achievement3 = findViewById<ImageButton>(R.id.achievementsView3)

        // 🔹 Open Challenge Activity when first icon clicked
        achievement1.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)

        }
    }
}
