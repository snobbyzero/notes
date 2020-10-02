package com.example.notes.fragment.create_note

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.FragmentCreateNoteBinding
import com.example.notes.fragment.notes_list.NotesListFragment
import com.example.notes.util.Status
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_create_note.*
import javax.inject.Inject

class CreateNoteFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CreateNoteViewModel

    private lateinit var binding: FragmentCreateNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, modelFactory)[CreateNoteViewModel::class.java]
        setupUI()

        return binding.root
    }

    private fun setupUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(binding) {
            saveButton.setOnClickListener {
                saveNote(editText.text.toString())
            }
            cancelButton.setOnClickListener { goToNodesList() }
        }
    }

    private fun saveNote(text: String) {
        viewModel.save(text).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            goToNodesList()
                        }
                    }
                    Status.ERROR -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            showDialog()
                        }
                    }
                    Status.LOADING -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setMessage("Server connection error")
            .setPositiveButton("Retry") { _, _ -> saveNote(editText.text.toString()) }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }


    private fun goToNodesList() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
            .replace(
                R.id.fragmentContainer,
                NotesListFragment.newInstance(),
                NotesListFragment::class.simpleName
            )
            .commit()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = CreateNoteFragment()
    }

}
