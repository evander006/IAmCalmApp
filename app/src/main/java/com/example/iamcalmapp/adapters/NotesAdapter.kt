package com.example.iamcalmapp.adapters

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iamcalmapp.databinding.ItemNoteBinding
import com.example.iamcalmapp.roomdb.Note

class NotesAdapter(private val onNoteClick:(Note) ->Unit,
    private val onDeleteClick: (Note) -> Unit): ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val binding= ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note=getItem(position)
        holder.binding.apply {
            titleTextView.text=note.title
            dateTextView.text=note.date
            root.setOnClickListener { onNoteClick(note) }
            deleteButton.setOnClickListener { onDeleteClick(note) }
        }
    }

    inner class NoteViewHolder(val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root)

    class NoteDiffCallback: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem==newItem
        }

    }
}