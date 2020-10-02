package com.example.notes.fragment.notes_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.notes.model.Note
import com.example.notes.repository.NotesRepository
import com.example.notes.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NotesListViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {

    fun getNotes() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = notesRepository.getNotes()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error occurred!"))
        }
    }

}