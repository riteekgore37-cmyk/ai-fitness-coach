package com.aifitnesscoach.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
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

            // Clear entire session safely
            UserPrefUtil.logout(this)
            UserPrefUtil.setUserLoggedIn(this, false)

            // Navigate to Splash screen
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // ✅ Achievement buttons
        val achievement1 = findViewById<ImageButton>(R.id.achievementsView1)
        val achievement2 = findViewById<ImageButton>(R.id.achievementsView2)
        val achievement3 = findViewById<ImageButton>(R.id.achievementsView3)

        achievement1.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)
        }

        // Optional: Add click listeners if needed
        achievement2.setOnClickListener {
            // Future feature
        }

        achievement3.setOnClickListener {
            // Future feature
        }
    }
}
