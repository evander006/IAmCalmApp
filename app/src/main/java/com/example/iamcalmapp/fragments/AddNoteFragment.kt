package com.example.iamcalmapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.iamcalmapp.R
import com.example.iamcalmapp.databinding.FragmentAddNoteBinding
import com.example.iamcalmapp.roomdb.Note
import com.example.iamcalmapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private var noteToEdit: Note?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddNoteBinding.inflate(layoutInflater)
        noteToEdit=arguments?.getParcelable("note")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this)[NoteViewModel::class.java]
        binding.dateTextView.text= SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        if (noteToEdit!=null){
            binding.titleEditText.setText(noteToEdit?.title)
            binding.contentEditText.setText(noteToEdit?.desc)
            binding.saveButton.text="Update"
        }
    }

    override fun onResume() {
        super.onResume()
        binding.saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote(){
        val title=binding.titleEditText.text.toString().trim()
        val description=binding.contentEditText.text.toString().trim()
        val date=binding.dateTextView.text.toString().trim()

        val note=noteToEdit?.copy(title=title, desc = description, date = date)
            ?: Note(id = 0, title=title, desc = description, date = date)
        if (noteToEdit==null){
            viewModel.insert(note)
        }else{
            viewModel.update(note)
        }

        val fragment = NotesFragment()

        val activity = context as AppCompatActivity

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}