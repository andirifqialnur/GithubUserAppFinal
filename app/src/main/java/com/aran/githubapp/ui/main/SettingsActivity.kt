package com.aran.githubapp.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.aran.githubapp.R
import com.aran.githubapp.databinding.ActivitySettingsBinding
import com.aran.githubapp.mode.SettingViewModelFactory
import com.aran.githubapp.mode.SharedPref
import com.aran.githubapp.model.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switches)
        val textTitle = findViewById<TextView>(R.id.theme_text)
        val icon = findViewById<ImageView>(R.id.icon)

        val pref = SharedPref.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref)).get(SettingsViewModel::class.java)

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
                textTitle.setText(R.string.light_mode)
                icon.setImageResource(R.drawable.ic_light_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
                textTitle.setText(R.string.dark_mode)
                icon.setImageResource(R.drawable.ic_dark_mode)
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}