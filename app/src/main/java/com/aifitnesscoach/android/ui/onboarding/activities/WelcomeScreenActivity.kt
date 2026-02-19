package com.aifitnesscoach.android.ui.onboarding.activities

import android.app.AlertDialog
import android.content.Intent
import com.aifitnesscoach.android.ui.onboarding.models.Session
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.databinding.ActivityWelcomeScreenBinding
import com.aifitnesscoach.android.network.RetrofitService
import com.aifitnesscoach.android.ui.home.HomeActivity
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.aifitnesscoach.android.ui.onboarding.utils.ValidationUtil
import com.aifitnesscoach.android.ui.onboarding.viewModel.UserRepository
import com.aifitnesscoach.android.ui.onboarding.viewModel.UserViewModel
import com.aifitnesscoach.android.ui.onboarding.viewModel.UserViewModelFactory

class WelcomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var viewModel: UserViewModel
    private lateinit var progress: ProgressBar
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initServerDialog()
        initViewModels()
        observeLogin()
        onRegister()
    }

    private fun initServerDialog() {
        binding.titleTv.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Server Link")

            val input = EditText(this)
            input.hint = "Enter new server link"
            input.setText(RetrofitService.BASE_URL)

            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                val newUrl = input.text.toString()
                if (newUrl.isNotEmpty()) {
                    RetrofitService.changeBaseUrl(newUrl)
                    startActivity(Intent(this, WelcomeScreenActivity::class.java))
                    finish()
                }
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
    }

    private fun init() {
        bottomSheet = BottomSheetDialog(this)

        binding.loginTextView.setOnClickListener {
            if (!bottomSheet.isShowing) {
                showLogin()
            }
        }

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, OnBoardingSplashActivity::class.java))
        }
    }

    private fun initViewModels() {
        val userRepository = UserRepository(RetrofitService.createService())
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]
    }

    private fun observeLogin() {
        viewModel.loginResponse.observe(this) { response ->

            showProgress(false)

            if (response.isSuccessful) {

                val body = response.body()

                if (body != null && body.success) {

                    // Save token
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

                    startActivity(
                        Intent(this, HomeActivity::class.java)
                    )

                    finish()

                } else {
                    Toast.makeText(
                        this,
                        body?.message ?: getString(R.string.an_error_occurred),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    this,
                    "Login failed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onRegister() {
        val showLogin = intent.getBooleanExtra("register", false)
        if (showLogin) showLogin()
    }

    private fun showLogin() {
        bottomSheet.setContentView(R.layout.login_view)
        bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.show()

        loginBtn = bottomSheet.findViewById(R.id.loginBtn)!!
        val emailEditText =
            bottomSheet.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText =
            bottomSheet.findViewById<TextInputEditText>(R.id.passwordEditText)
        progress = bottomSheet.findViewById(R.id.progress)!!

        loginBtn.setOnClickListener {

            val email = emailEditText?.text.toString().trim()
            val password = passwordEditText?.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.please_fill_all_the_data),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!ValidationUtil.validateEmail(email)) {
                Toast.makeText(this, getString(R.string.email), Toast.LENGTH_SHORT).show()
            } else if (!ValidationUtil.validatePasswordLength(password)) {
                Toast.makeText(
                    this,
                    getString(R.string.enter_at_least_8_characters),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showProgress(true)
                viewModel.loginUser(email, password)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            progress.visibility = View.VISIBLE
            loginBtn.text = ""
        } else {
            progress.visibility = View.GONE
            loginBtn.text = getString(R.string.login)
        }
    }
}
