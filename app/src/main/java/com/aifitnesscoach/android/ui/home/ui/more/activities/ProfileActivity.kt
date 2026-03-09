package com.aifitnesscoach.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.network.RetrofitService
import com.aifitnesscoach.android.network.models.GetProfileResponse
import com.aifitnesscoach.android.ui.onboarding.activities.SplashActivity
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameText: TextView
    private lateinit var genderValue: TextView
    private lateinit var ageValue: TextView
    private lateinit var weightValue: TextView
    private lateinit var heightValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // ✅ THESE MUST MATCH YOUR XML
        nameText = findViewById(R.id.profileNameTextView)
        genderValue = findViewById(R.id.personalInfoTextView2)
        ageValue = findViewById(R.id.personalInfoTextView4)
        weightValue = findViewById(R.id.personalInfoTextView6)
        heightValue = findViewById(R.id.personalInfoTextView8)

        loadProfile()

        // Edit Profile
        findViewById<TextView>(R.id.editTextView).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        // Logout
        findViewById<TextView>(R.id.logoutBtn).setOnClickListener {
            UserPrefUtil.logout(this)
            UserPrefUtil.setUserLoggedIn(this, false)

            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Achievements
        findViewById<ImageButton>(R.id.achievementsView1).setOnClickListener {
            startActivity(Intent(this, ChallengeActivity::class.java))
        }
    }

    private fun loadProfile() {

        val session = UserPrefUtil.getUserData(this)

        if (session == null || session.token.isEmpty()) {
            Log.d("PROFILE_DEBUG", "Session or token missing")
            return
        }

        val token = session.token

        lifecycleScope.launch {
            try {

                val response = RetrofitService.getApiService(this@ProfileActivity)
                    .getProfile("Bearer $token")

                Log.d("PROFILE_DEBUG", "Response code: ${response.code()}")

                if (response.isSuccessful) {

                    val body: GetProfileResponse? = response.body()
                    val user = body?.user

                    Log.d("PROFILE_DEBUG", "User: $user")

                    nameText.text = user?.name ?: "-"
                    genderValue.text = user?.gender ?: "-"
                    ageValue.text = user?.dob ?: "-"
                    weightValue.text = user?.weight?.toString() ?: "-"
                    heightValue.text = user?.height?.toString() ?: "-"

                } else {
                    Log.d("PROFILE_DEBUG", response.errorBody()?.string() ?: "Error")
                }

            } catch (e: Exception) {
                Log.e("PROFILE_DEBUG", "Exception: ${e.message}")
            }
        }
    }
}
