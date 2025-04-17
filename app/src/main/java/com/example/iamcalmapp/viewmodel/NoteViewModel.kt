package com.example.iamcalmapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.iamcalmapp.roomdb.Note
import com.example.iamcalmapp.roomdb.AppDatabase
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).notesDao()
    val allNotes: LiveData<List<Note>> = dao.getNotes().asLiveData()

    fun insert(note:Note) = viewModelScope.launch{
        dao.insert(note)
    }
    fun delete(note:Note) = viewModelScope.launch{
        dao.delete(note)
    }
    fun update(note:Note) = viewModelScope.launch{
        dao.update(note)
    }

}