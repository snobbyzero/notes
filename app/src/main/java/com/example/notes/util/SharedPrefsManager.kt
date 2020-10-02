package com.example.notes.util

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getToken() : String = sharedPreferences.getString(TOKEN, "-")!!

    fun getSession() : String = sharedPreferences.getString(SESSION, "-")!!

    fun isFirstLaunch() : Boolean = sharedPreferences.getBoolean(FIRST_LAUNCH, true)

    fun saveToken(token: String) {
        sharedPreferences.edit { it.putString(TOKEN, token) }
    }

    fun saveSession(session: String) {
        sharedPreferences.edit { it.putString(SESSION, session) }
    }

    fun saveFirstLaunch() {
        sharedPreferences.edit { it.putBoolean(FIRST_LAUNCH, false) }
    }

    companion object Constants {
        const val TOKEN = "TOKEN"
        const val SESSION = "SESSION"
        const val FIRST_LAUNCH = "FIRST_LAUNCH"

        inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
            val editor = edit()
            operation(editor)
            editor.apply()
        }
    }
}