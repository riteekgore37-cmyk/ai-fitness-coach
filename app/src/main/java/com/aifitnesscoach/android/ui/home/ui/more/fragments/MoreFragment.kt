package com.aifitnesscoach.android.ui.home.ui.more.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aifitnesscoach.android.R
import com.aifitnesscoach.android.ui.home.ui.more.activities.ReminderActivity
import com.aifitnesscoach.android.ui.home.ui.more.activities.SettingActivity
import com.aifitnesscoach.android.ui.onboarding.activities.SplashActivity
import com.aifitnesscoach.android.ui.onboarding.utils.UserPref.UserPrefUtil

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Reminder
        val reminderCard = view.findViewById<View>(R.id.cardView5)
        reminderCard.setOnClickListener {
            startActivity(Intent(requireContext(), ReminderActivity::class.java))
        }

        // 🔹 Settings
        val settingCard = view.findViewById<View>(R.id.cardView6)
        settingCard.setOnClickListener {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }

        // 🔹 Logout
        val logoutCard = view.findViewById<View>(R.id.cardView8)
        logoutCard.setOnClickListener {

            // Clear full session safely
            UserPrefUtil.logout(requireContext())
            UserPrefUtil.setUserLoggedIn(requireContext(), false)

            val intent = Intent(requireContext(), SplashActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            requireActivity().finish()
        }
    }
}
