package com.example.notes.fragment.create_note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.notes.repository.NotesRepository
import com.example.notes.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateNoteViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {

     fun save(text: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = notesRepository.saveNote(text)))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error occurred!"))
        }
    }

}