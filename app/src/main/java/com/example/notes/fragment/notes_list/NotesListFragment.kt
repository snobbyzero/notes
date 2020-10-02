package com.example.notes.fragment.notes_list

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.databinding.FragmentNotesListBinding
import com.example.notes.fragment.create_note.CreateNoteFragment
import com.example.notes.fragment.note.NoteFragment
import com.example.notes.model.Note
import com.example.notes.util.Status
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NotesListFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NotesListViewModel

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var notesAdapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, modelFactory)[NotesListViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        setupUI()
        observe()
        setupRecyclerView()

        return binding.root
    }

    private fun observe() {
        viewModel.getNotes().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.apply {
                            notesRecyclerView.visibility = View.VISIBLE
                            swipeRefreshLayout.isRefreshing = false
                            notesAdapter.addNotes(it.data!!)
                        }
                    }
                    Status.ERROR -> {
                        binding.apply {
                            notesRecyclerView.visibility = View.VISIBLE
                            swipeRefreshLayout.isRefreshing = false
                            showDialog()
                        }
                    }
                    Status.LOADING -> {
                        binding.apply {
                            notesRecyclerView.visibility = View.GONE
                            swipeRefreshLayout.isRefreshing = true
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
            .setPositiveButton("Retry") { _, _ -> observe() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun setupUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        with(binding) {
            addNodeFab.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setCustomAnimations(R.animator.slide_up, 0, 0, R.animator.slide_down)
                    .replace(
                        R.id.fragmentContainer,
                        CreateNoteFragment.newInstance(),
                        CreateNoteFragment::class.java.simpleName
                    )
                    .addToBackStack(NotesListFragment::class.java.simpleName)
                    .commit()
            }
            swipeRefreshLayout.setOnRefreshListener { observe() }
        }
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesListAdapter(arrayListOf(), object : View.OnClickListener {
            override fun onClick(view: View) {
                val pos = binding.notesRecyclerView.getChildAdapterPosition(view)
                val note = notesAdapter.dataset[pos]
                showNote(note)
            }

        })
        with(binding.notesRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = notesAdapter
        }
    }

    private fun showNote(note: Note) {
        val bundle = Bundle()
        bundle.putSerializable(NOTE, note)
        val noteFragment = NoteFragment.newInstance()
        noteFragment.arguments = bundle
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_open_exit)
            .replace(
                R.id.fragmentContainer,
                noteFragment,
                NoteFragment::class.java.simpleName
            )
            .addToBackStack(NotesListFragment::class.java.simpleName)
            .commit()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        const val NOTE = "NOTE"
        fun newInstance() = NotesListFragment()
    }

}