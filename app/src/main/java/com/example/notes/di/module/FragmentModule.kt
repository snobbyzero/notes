package com.example.notes.di.module

import com.example.notes.di.annotation.FragmentScope
import com.example.notes.fragment.create_note.CreateNoteFragment
import com.example.notes.fragment.note.NoteFragment
import com.example.notes.fragment.notes_list.NotesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesCreateNoteFragment() : CreateNoteFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesNotesListFragment() : NotesListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesNoteFragment() : NoteFragment

}