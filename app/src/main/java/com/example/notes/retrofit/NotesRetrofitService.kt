package com.example.notes.retrofit

import com.example.notes.model.BaseResponse
import com.example.notes.model.NotesListResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NotesRetrofitService {

    @FormUrlEncoded
    @POST(".")
    suspend fun getNotes(@Field("a") session: String = "get_entries"): Response<JsonObject>

    @FormUrlEncoded
    @POST(".")
    suspend fun saveNote(
        @Field("body") body: String,
        @Field("a") session: String = "add_entry"
    ): Response<JsonObject>
}