package com.example.iamcalmapp.fragments

import  androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iamcalmapp.R
import com.example.iamcalmapp.adapters.NotesAdapter
import com.example.iamcalmapp.databinding.FragmentNotesBinding
import com.example.iamcalmapp.roomdb.Note
import com.example.iamcalmapp.viewmodel.NoteViewModel


class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel= ViewModelProvider(this)[NoteViewModel::class.java]

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            if (notes.isEmpty()) {
                binding.apply {
                    text1.visibility = View.VISIBLE
                    text2.visibility = View.VISIBLE
                    recView.visibility = View.GONE
                }
            } else {
                binding.apply {
                    text1.visibility = View.GONE
                    text2.visibility = View.GONE
                    recView.visibility = View.VISIBLE
                }
                notesAdapter.submitList(notes)
            }
        }

        binding.addNoteBtn.setOnClickListener {
            navToAddNoteFragment(null) // Pass null for new note
        }
    }

    private fun navToAddNoteFragment(note: Note?) {
        val fragment = AddNoteFragment()
        val bundle = Bundle().apply {
            putParcelable("note", note)
        }
        fragment.arguments = bundle
        val activity = context as AppCompatActivity

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()


    }

    private fun setUpRecyclerView() {
        notesAdapter = NotesAdapter(
            onNoteClick = { note ->
                navToAddNoteFragment(note)
            },
            onDeleteClick = { note ->
                viewModel.delete(note)
            }
        )

        binding.recView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
