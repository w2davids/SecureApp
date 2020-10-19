package com.example.secureapp.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _isPassCodeChecked = MutableLiveData<Boolean>()
    val onPassCodeChecked: LiveData<Boolean>
        get() = _isPassCodeChecked

    fun onCheckedChange(checked: Boolean) {
        _isPassCodeChecked.value = checked
    }
}