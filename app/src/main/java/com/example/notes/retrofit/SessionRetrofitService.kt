package com.example.notes.retrofit


import com.example.notes.model.SessionResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface SessionRetrofitService {

    @FormUrlEncoded
    @POST(".")
    suspend fun createNewSession(@Field("a") session: String = "new_session") : Response<JsonObject>
}