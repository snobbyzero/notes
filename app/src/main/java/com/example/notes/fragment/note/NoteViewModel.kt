package com.example.notes.fragment.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.model.Note
import javax.inject.Inject

class NoteViewModel @Inject constructor() : ViewModel() {

    val note = MutableLiveData<Note>()
}