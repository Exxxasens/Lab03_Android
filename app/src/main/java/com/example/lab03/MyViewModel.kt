package com.example.lab03

import androidx.lifecycle.ViewModel

data class TaskModel(var name: String, var description: String, var isChecked: Boolean, val imageResource: Int)
open class MyViewModel: ViewModel() {
    private var notes = mutableListOf(
        TaskModel("Заметка про лабы", "Нужно все сделать", false, R.drawable.first),
        TaskModel( "Вторая заметка", "Описание второй заметки", true, R.drawable.second),
        TaskModel( "Task 3", "Description for Task 3", true, R.drawable.third))

    fun getAllNotes(): List<TaskModel> {
        return notes.toList()
    }

    fun getNote(id: Int): TaskModel {
        return notes[id]
    }

    fun updateNote(id: Int, name: String, description: String, isChecked: Boolean) {
        notes[id].name = name
        notes[id].description = description
        notes[id].isChecked = isChecked
    }

    fun updateNote(id: Int, isChecked: Boolean) {
        notes[id].isChecked = isChecked
    }



}