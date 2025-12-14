package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.AppPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val appPreferences: AppPreferences) : ViewModel() {

    val darkMode: StateFlow<Boolean> = appPreferences.darkModeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleDarkMode(currentValue: Boolean) {
        viewModelScope.launch {
            appPreferences.toggleDarkMode(currentValue)
        }
    }
}
