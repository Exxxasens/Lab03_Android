package com.example.lab03

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class NoteViewModel( private val dao: NoteDao): ViewModel() {
    private val _selectedNoteId = MutableLiveData<Int>(null)
    val selectedNoteId: LiveData<Int> get() = _selectedNoteId
    fun selectNoteId(index: Int) {
        _selectedNoteId.value = index
    }
    fun getSelectedId(): Int {
        return selectedNoteId.value ?: 0
    }

    val notes: LiveData<List<Note>> = dao.getNotes()

    fun insertNote(note: Note) {
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            dao.updateNote(note)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            dao.deleteNote(id)
        }
    }

}

class TaskViewModelFactory(private val dao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}

