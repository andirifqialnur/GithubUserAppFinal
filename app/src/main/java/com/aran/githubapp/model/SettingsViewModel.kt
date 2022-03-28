package com.aran.githubapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aran.githubapp.mode.SharedPref
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SharedPref) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}