package com.aifitnesscoach.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aifitnesscoach.android.databinding.ActivityEditProfileBinding
import com.aifitnesscoach.android.network.RetrofitService
import com.aifitnesscoach.android.network.models.UpdateProfileRequest
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var selectedGender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genders = arrayOf("male", "female", "other")

        binding.genderButton.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Select Gender")
                .setItems(genders) { dialog, which ->
                    selectedGender = genders[which]
                    binding.genderButton.text = genders[which]
                    dialog.dismiss()
                }
                .show()
        }

        binding.startButton.setOnClickListener {

            val weight = binding.weightValueEditText.text.toString().toIntOrNull()
            val height = binding.heightValueEditText.text.toString().toIntOrNull()

            val session = UserPrefUtil.getUserData(this)
            val token = session?.token

            if (token.isNullOrEmpty()) {
                Toast.makeText(this, "Token missing", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = UpdateProfileRequest(
                dob = null,
                gender = selectedGender,
                height = height,
                weight = weight,
                fitness_level = null,
                injuries = emptyList(),
                preferences = null
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitService.getApiService(this@EditProfileActivity)
                        .updateProfile("Bearer $token", request)

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@EditProfileActivity,
                            "Profile Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this@EditProfileActivity, ProfileActivity::class.java)
                        )
                        finish()

                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Update Failed: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
