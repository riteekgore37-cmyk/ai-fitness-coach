package com.aifitnesscoach.android.ui.onboarding.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.databinding.ActivityOnBoardingBinding
import com.aifitnesscoach.android.ui.onboarding.adapters.OnBoardingAdapter
import com.aifitnesscoach.android.ui.onboarding.models.UserRegisterData
import com.aifitnesscoach.android.ui.onboarding.utils.ValidationUtil

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.apply {
            bringToFront()
            elevation = 100f
            isClickable = true
            isFocusable = true
        }

        initViewPager()
        handleButtons()
        handleSystemBack()
    }

    @SuppressLint("InflateParams")
    private fun initViewPager() {
        val adapter = OnBoardingAdapter(
            listOf(
                layoutInflater.inflate(R.layout.single_selection_view, null),
                layoutInflater.inflate(R.layout.gender_selection_view, null),
                layoutInflater.inflate(R.layout.target_weight_selection_view, null),
                layoutInflater.inflate(R.layout.message_view, null),
                layoutInflater.inflate(R.layout.single_selection_view, null),
                layoutInflater.inflate(R.layout.single_selection_view, null),
                layoutInflater.inflate(R.layout.multiple_selection_view, null),
                layoutInflater.inflate(R.layout.multiple_selection_view, null),
                layoutInflater.inflate(R.layout.message_view, null)
            ),
            this
        )

        binding.viewPager.apply {
            isUserInputEnabled = false
            this.adapter = adapter
        }

        // 🔥 Prevent onboarding pages from blocking back button
        binding.viewPager.getChildAt(0)?.isClickable = false
        binding.viewPager.getChildAt(0)?.isFocusable = false
    }

    private fun handleButtons() {

        binding.nextButton.setOnClickListener {

            val current = binding.viewPager.currentItem
            val last = (binding.viewPager.adapter?.itemCount ?: 0) - 1

            if (current == last) {
                // Final page → continue
                if (ValidationUtil.validateRegistrationData(UserRegisterData.registerRequest)) {
                    startActivity(Intent(this, RegisterScreenActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.please_fill_all_the_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                binding.viewPager.currentItem = current + 1
            }

            updateButtonText()
        }

        binding.backButton.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        val current = binding.viewPager.currentItem

        if (current == 0) {
            finish()
        } else {
            binding.viewPager.currentItem = current - 1
        }

        updateButtonText()
    }

    private fun updateButtonText() {
        val current = binding.viewPager.currentItem
        val last = (binding.viewPager.adapter?.itemCount ?: 0) - 1

        binding.nextButton.text =
            if (current == last) getString(R.string.continue_)
            else getString(R.string.next)
    }

    private fun handleSystemBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
    }
}
