package com.example.lab03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var addNoteButton: Button? = null
    private var saveNoteButton: Button? = null
    private var showNoteButton: Button? = null

    private var notes = mutableListOf<TaskModel>(
        TaskModel(0, "Заметка про лабы", "Нужно все сделать"),
    )

    private var currentNote = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteName = findViewById(R.id.editNoteName)
        noteDescription = findViewById(R.id.editNoteDescription)
        addNoteButton = findViewById(R.id.addNoteButton)
        saveNoteButton = findViewById(R.id.saveNoteButton)
        showNoteButton = findViewById(R.id.showNoteButton)

        showNote()
    }

    private fun showNote() {
        // Находим нужную заметку, если ее нет, то выходим
        val note = notes.find {
            it.id == currentNote
        } ?: return

        noteName?.setText(note.name)
        noteDescription?.setText(note.description)

    }

    fun showNextNote(view: View) {
        if (currentNote <= notes.size - 1) {
            currentNote++
        }
        showNote()
    }

    fun showPrevNote(view: View) {
        if (currentNote > 0) {
            currentNote--
        }

        showNote()
    }

    fun showLastNote(view: View) {
        currentNote = notes.size - 1
        showNote()
    }

    fun saveNote(view: View) {
        val name = noteName?.text.toString()
        val description = noteDescription?.text.toString()

        // notes.add(TaskModel(notes.size, name, description))

        // Находим нужную заметку, если ее нет, то выходим
        val note = notes.find {
            it.id == currentNote
        } ?: return

        note.name = name
        note.description = description

        showToast("Заметка отредактирована")

    }

    fun addNote(view: View) {
        notes.add(TaskModel(notes.size, "", ""))
        currentNote = notes.size - 1
        showNote()

        showToast("Добавлена новая заметка")
    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
            .show()
    }

}