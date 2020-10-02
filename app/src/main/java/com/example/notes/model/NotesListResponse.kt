package com.example.notes.model

import com.google.gson.annotations.SerializedName

data class NotesListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<List<NoteResponse>>)