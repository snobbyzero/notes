package com.example.notes.repository

import android.util.JsonReader
import android.util.Log
import com.example.notes.model.*
import com.example.notes.retrofit.NotesRetrofitService
import com.google.gson.Gson
import java.io.StringReader
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class NotesRepository @Inject constructor(
    private val notesRetrofitService: NotesRetrofitService,
    private val gson: Gson
) {

    suspend fun getNotes(): List<Note>? {
        var notes: List<Note>? = null
        val response = notesRetrofitService.getNotes()
        response.errorBody()?.let {
            Log.e("ERR", "getNotes: $it")
        }
        val body = gson.fromJson(response.body(), NotesListResponse::class.java)
        if (body.status == 0) {
            val errorResponse = gson.fromJson(response.body(), ErrorResponse::class.java)
            Log.e("ERR", "getNotes: ${errorResponse.error}")
        } else if (body.status == 1) {
            notes = body.data[0].map {
                Note(
                    it.id,
                    it.body,
                    longToDate(it.da),
                    if (it.da != it.dm) longToDate(it.dm) else ""
                )
            }
        }
        return notes
    }

    suspend fun saveNote(text: String): String? {
        try {
            val response = notesRetrofitService.saveNote(text)

            response.errorBody()?.let {
                Log.e("ERR", "saveNote: $it")
            }
            val body = gson.fromJson(response.body(), AddNoteResponse::class.java)
            if (body.status == 0) {
                val errorResponse = gson.fromJson(response.body(), ErrorResponse::class.java)
                Log.e("ERR", "saveNote: ${errorResponse.error}")
            } else if (body.status == 1) {
                return body.data.id
            }
        } catch (ex: Exception) {
            Log.e("ERR", "saveNote: $ex")
        }
        return null
    }

    private fun longToDate(date: Long): String =
        SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(Date(date * 1000))


}