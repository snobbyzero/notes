package com.example.notes.fragment.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.model.Note
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NoteFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NoteViewModel

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, modelFactory)[NoteViewModel::class.java]
        with(binding) {
            noteViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val bundle = this.arguments
        viewModel.note.postValue(bundle?.getSerializable(NOTE) as Note)

        viewModel.note.observe(viewLifecycleOwner, Observer {
            print(it.body)
        })
        setupUI()

        return binding.root
    }

    private fun setupUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        const val NOTE = "NOTE"
        fun newInstance() = NoteFragment()
    }

}