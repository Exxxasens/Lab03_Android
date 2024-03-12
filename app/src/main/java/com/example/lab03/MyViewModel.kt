package com.example.lab03

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class TaskModel(var name: String, var description: String, var isChecked: Boolean, val imageResource: Int)
open class MyViewModel: ViewModel() {

    private val _notes = MutableLiveData<List<TaskModel>>()
    val notes: LiveData<List<TaskModel>> get() = _notes

    private val _noteIndex = MutableLiveData<Int>(null)
    val noteIndex: LiveData<Int> get() = _noteIndex

    init {
        // Initialize with some default data
        _notes.value = listOf(
            TaskModel("Заметка про лабы", "Нужно все сделать", false, R.drawable.first),
            TaskModel("Вторая заметка", "Описание второй заметки", true, R.drawable.second),
            TaskModel("Task 3", "Description for Task 3", true, R.drawable.third)
        )
    }

    fun getAllNotes(): ArrayList<TaskModel> {
        return ArrayList(notes.value!!)
    }

    fun getNote(index: Int): TaskModel? {
        return notes.value?.get(index)
    }

    fun updateNote(index: Int, name: String, description: String, isChecked: Boolean) {
        if (notes.value != null) {
            val updatedNotes = notes.value!!.toMutableList()
            updatedNotes[index] =
                TaskModel(name, description, isChecked, updatedNotes[index].imageResource)
            _notes.value = updatedNotes
        }
    }

    fun updateNote(index: Int, isChecked: Boolean) {
        val updatedNotes = notes.value?.toMutableList() ?: mutableListOf()
        updatedNotes[index] = TaskModel(
            updatedNotes[index].name,
            updatedNotes[index].description,
            isChecked,
            updatedNotes[index].imageResource
        )
        _notes.value = updatedNotes
    }

    fun createNote(name: String, description: String, isChecked: Boolean) {
        val notes = _notes.value!!.toMutableList()
        notes.add(TaskModel(name, description, isChecked, R.drawable.no_image))
        _notes.value = notes
    }

    fun selectNoteIndex(index: Int) {
        _noteIndex.value = index
    }

    fun getSelectedIndex(): Int {
        return noteIndex.value ?: 0
    }
}