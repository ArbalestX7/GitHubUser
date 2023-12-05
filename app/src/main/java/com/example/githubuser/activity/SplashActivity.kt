package com.example.githubuser.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubuser.databinding.ActivitySplashBinding
import com.example.githubuser.preferences.SettingPreferences
import com.example.githubuser.preferences.SettingViewModelFactory
import com.example.githubuser.preferences.dataStore
import com.example.githubuser.util.Constant
import com.example.githubuser.util.isDarkModeOn
import com.example.githubuser.viewmodel.SettingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        supportActionBar?.hide()
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel by viewModels<SettingViewModel> { SettingViewModelFactory(pref) }
        settingViewModel.getThemeSettings(isDarkModeOn())
            .observe(this) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                setContentView(binding.root)
            }

        activityScope.launch {
            delay(Constant.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }
    }
}