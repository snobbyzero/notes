package com.example.notes.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.activity.main.MainViewModel
import com.example.notes.di.ViewModelFactory
import com.example.notes.di.annotation.ViewModelKey
import com.example.notes.fragment.create_note.CreateNoteViewModel
import com.example.notes.fragment.note.NoteViewModel
import com.example.notes.fragment.notes_list.NotesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateNoteViewModel::class)
    abstract fun bindCreateNoteViewModel(viewModel: CreateNoteViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotesListViewModel::class)
    abstract fun bindNotesListViewModel(viewModel: NotesListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    abstract fun bindNoteViewModel(viewModel: NoteViewModel) : ViewModel
}