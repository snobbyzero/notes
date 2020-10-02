package com.example.notes.activity.main

import androidx.lifecycle.ViewModel
import com.example.notes.repository.SessionRepository
import com.example.notes.util.SharedPrefsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    fun saveToken() {
        if (sharedPrefsManager.getToken() == "-") {
            sharedPrefsManager.saveToken("0qZLjIS-Fo-T2pVq96")
        }
    }

    fun saveSession() {
        if (sharedPrefsManager.isFirstLaunch()) {
            CoroutineScope(Dispatchers.IO).launch {
                sessionRepository.saveSession()
                sharedPrefsManager.saveFirstLaunch()
            }
        }
    }
}