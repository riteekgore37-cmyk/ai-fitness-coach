package com.aifitnesscoach.android.ui.onboarding.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.databinding.ActivityRegisterScreenBinding
import com.aifitnesscoach.android.network.RetrofitService
import com.aifitnesscoach.android.ui.home.HomeActivity
import com.aifitnesscoach.android.ui.onboarding.models.Session
import com.aifitnesscoach.android.ui.onboarding.models.UserRegisterData
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.aifitnesscoach.android.ui.onboarding.utils.ValidationUtil
import com.aifitnesscoach.android.ui.onboarding.viewModel.UserRepository
import com.aifitnesscoach.android.ui.onboarding.viewModel.UserViewModel
import com.aifitnesscoach.android.ui.onboarding.viewModel.UserViewModelFactory

class RegisterScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterScreenBinding
    private lateinit var viewModel: UserViewModel

    // Save email & password temporarily for auto login
    private var registeredEmail: String = ""
    private var registeredPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels()
        observeRegisterResponse()
        observeLoginResponse()
        doRegister()
    }

    private fun initViewModels() {
        val userRepository = UserRepository(RetrofitService.createService())
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]
    }

    private fun doRegister() {
        binding.registerButton.setOnClickListener {

            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText2.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (isValidInput()) {

                // Store for auto login
                registeredEmail = email
                registeredPassword = password

                UserRegisterData.registerRequest.name = name
                UserRegisterData.registerRequest.email = email
                UserRegisterData.registerRequest.password = password
                UserRegisterData.registerRequest.confirmPassword = confirmPassword

                binding.progessView.progressOverlay.visibility = View.VISIBLE

                viewModel.registerUser(UserRegisterData.registerRequest)
            }
        }
    }

    // 🔹 Observe Register API
    private fun observeRegisterResponse() {
        viewModel.registerResponse.observe(this) { response ->

            if (response.isSuccessful) {

                val body = response.body()

                if (body != null && body.success) {

                    Toast.makeText(
                        this,
                        body.message,
                        Toast.LENGTH_LONG
                    ).show()

                    // ✅ Automatically call login
                    viewModel.loginUser(registeredEmail, registeredPassword)

                } else {
                    binding.progessView.progressOverlay.visibility = View.GONE
                    Toast.makeText(
                        this,
                        body?.message ?: getString(R.string.an_error_occurred),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                binding.progessView.progressOverlay.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Registration failed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // 🔹 Observe Login API
    private fun observeLoginResponse() {
        viewModel.loginResponse.observe(this) { response ->

            binding.progessView.progressOverlay.visibility = View.GONE

            if (response.isSuccessful) {

                val body = response.body()

                if (body != null && body.success) {

                    // ✅ Save session
                    UserPrefUtil.saveSession(
                        this,
                        Session(
                            user = body.user,
                            token = body.token
                        )
                    )

                    UserPrefUtil.setUserLoggedIn(this, true)

                    Toast.makeText(
                        this,
                        "Welcome ${body.user.name}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // ✅ Go to Home
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        body?.message ?: "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    this,
                    "Auto login failed. Please login manually.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isValidInput(): Boolean {

        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText2.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast(getString(R.string.please_fill_all_the_data))
            return false
        }

        if (!ValidationUtil.validateEmail(email)) {
            showToast(getString(R.string.write_a_valid_email))
            return false
        }

        if (!ValidationUtil.validatePasswordLength(password)) {
            showToast(getString(R.string.enter_at_least_8_characters))
            return false
        }

        if (!ValidationUtil.validatePasswordMatch(password, confirmPassword)) {
            showToast(getString(R.string.password_don_t_match))
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}