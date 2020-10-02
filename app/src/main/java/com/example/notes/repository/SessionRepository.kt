package com.example.notes.repository

import android.util.Log
import com.example.notes.model.BaseResponse
import com.example.notes.model.ErrorResponse
import com.example.notes.model.SessionResponse
import com.example.notes.retrofit.SessionRetrofitService
import com.example.notes.util.SharedPrefsManager
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val sessionRetrofitService: SessionRetrofitService,
    private val sharedPrefsManager: SharedPrefsManager,
    private val gson: Gson
) {

    suspend fun saveSession() : String? {
        // Получаем сессию
        val response = sessionRetrofitService.createNewSession()
        response.errorBody()?.let {
            Log.e("ERR", "saveSession: $it")
        }
        val body = gson.fromJson(response.body(), SessionResponse::class.java)
        if (body.status == 0) {
            val errorResponse = gson.fromJson(response.body(), ErrorResponse::class.java)
            Log.e("ERR", "saveSession: ${errorResponse.error}", )
        } else if (body.status == 1) {
            body.data?.session?.let {
                // Сохраняем сессию
                sharedPrefsManager.saveSession(it)
                return it
            }
        }

        return null
    }
}