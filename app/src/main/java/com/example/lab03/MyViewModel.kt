package com.example.lab03

import androidx.lifecycle.ViewModel

class TaskModel(val id: Int, var name: String, var description: String)
open class MyViewModel: ViewModel() {
    private var notes = mutableListOf(
        TaskModel(0, "Заметка про лабы", "Нужно все сделать"),
        TaskModel(1, "Вторая заметка", "Описание второй заметки")
    )

    private var lastIndex = notes.last().id
    private var size = notes.size

    fun getAllNotes(): Array<TaskModel> {
        return notes.toTypedArray();
    }

    fun getSize(): Int {
        return size
    }

    fun getNote(id: Int): TaskModel {
        return notes[id]
    }

    fun updateNote(id: Int, name: String, description: String): TaskModel {
        notes[id].name = name
        notes[id].description = description
        return notes[id]
    }

    fun addNote(name: String, description: String) {
        notes.add(TaskModel(lastIndex + 1, name, description))
        size = notes.size
        lastIndex = notes.last().id
    }

}