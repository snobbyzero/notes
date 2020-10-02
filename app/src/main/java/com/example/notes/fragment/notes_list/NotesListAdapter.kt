package com.example.notes.fragment.notes_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.NoteItemBinding
import com.example.notes.model.Note

class NotesListAdapter(
    val dataset: ArrayList<Note>,
    val onNoteClickListener: View.OnClickListener
) : RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    inner class NotesListViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.onNoteClickListener = onNoteClickListener
        }

    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        val note = dataset[position]
        holder.bind(note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)

        return NotesListViewHolder(binding)
    }

    fun addNotes(notes: List<Note>) {
        with (dataset) {
            clear()
            addAll(notes)
            notifyDataSetChanged()
        }
    }
}