package com.example.notes.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NoteResponse(
    @SerializedName("id") val id: String,
    @SerializedName("body") val body: String,
    @SerializedName("da") val da: Long,
    @SerializedName("dm") val dm: Long
) {

}