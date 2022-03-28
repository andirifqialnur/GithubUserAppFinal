package com.aran.githubapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.aran.githubapp.R
import com.aran.githubapp.mode.SettingViewModelFactory
import com.aran.githubapp.mode.SharedPref
import com.aran.githubapp.model.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = SharedPref.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(
                this,
                SettingViewModelFactory(pref)
            ).get(SettingsViewModel::class.java)

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val delay = 3000L
        Handler(mainLooper).postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delay)
    }
}