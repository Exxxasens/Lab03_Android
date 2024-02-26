package com.example.lab03

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskModel(var name: String, var description: String)
open class MyViewModel: ViewModel() {
    var notes = mutableListOf<TaskModel>(
         TaskModel("Заметка про лабы", "Нужно все сделать"),
         TaskModel( "Вторая заметка", "Описание второй заметки"))

    // fun getNotesListLiveData() = notesListLiveData

    fun getAllNotes(): List<TaskModel> {
        return notes.toList()
    }

    fun getNote(id: Int): TaskModel {
        return notes[id]
    }

    fun updateNote(id: Int, name: String, description: String) {
        notes[id].name = name;
        notes[id].description = description
    }



}