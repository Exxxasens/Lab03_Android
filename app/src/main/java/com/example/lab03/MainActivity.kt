package com.example.lab03

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var addNoteButton: Button? = null
    private var saveNoteButton: Button? = null
    private var showNoteButton: Button? = null

    private var notes = mutableListOf(
        TaskModel(0, "Заметка про лабы", "Нужно все сделать"),
    )

    private var currentNote = 0

    companion object {
        const val CURRENT_NOTE = "CURRENT_NOTE"
        const val NOTE_NAME = "NOTE_NAME"
        const val NOTE_DESCRIPTION = "NOTE_DESCRIPTION"
        const val NOTES = "NOTES"
    }


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

    // lab 05
    fun onNoteEdit(view: View) {
        editNote.launch(object : EditNoteData {
            override val name = notes[currentNote].name
            override val description = notes[currentNote].description
        })
    }

    private val editNote = registerForActivityResult(EditNoteContract()) {
        if (it != null) {
            notes[currentNote].name = it.name
            notes[currentNote].description = it.description
            showNote()
        }
    }


    private fun showNote() {
        // заметку
        val note = notes[currentNote]

        noteName?.setText(note.name)
        noteDescription?.setText(note.description)

    }

    fun showNextNote(view: View) {
        if (currentNote < notes.size - 1) {
            currentNote++
            showNote()
        }
    }

    fun showPrevNote(view: View) {
        if (currentNote > 0) {
            currentNote--
            showNote()
        }
    }

    fun showLastNote(view: View) {
        currentNote = notes.size - 1
        showNote()
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



    /* Lab04 */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(CURRENT_NOTE, currentNote)
        outState.putString(NOTE_NAME, noteName?.text.toString())
        outState.putString(NOTE_DESCRIPTION, noteDescription?.text.toString())
        outState.putParcelableArrayList(NOTES, ArrayList(notes))

        showToast("Сохранение данных")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedNotes = savedInstanceState.getParcelableArrayList<TaskModel>(NOTES)

        if (savedNotes != null) {
            notes.clear()
            notes.addAll(savedNotes)
        }

        showNote()

        currentNote = savedInstanceState.getInt(CURRENT_NOTE)
        noteName?.setText(savedInstanceState.getString(NOTE_NAME))
        noteDescription?.setText(savedInstanceState.getString(NOTE_DESCRIPTION))


        showToast("Восстановление данных")

    }

    // onStart: Этот метод вызывается, когда активность становится видимой для пользователя. Например, после перехода с другой активности или при запуске приложения. Пример:
    override fun onStart() {
        super.onStart()

        showToast("Активность видима")
    }

    // onResume: Этот метод вызывается, когда активность становится активной и готовой к взаимодействию с пользователем.

    override fun onResume() {
        super.onResume()

        showToast("Активность готова к взаимодействию")
    }

    // onPause: Этот метод вызывается, когда активность теряет фокус, например, при переходе к другой активности или при сворачивании приложения.
    override fun onPause() {
        super.onPause()

        showToast("Активность теряет фокус")
    }

    // onStop: Этот метод вызывается, когда активность больше не видна пользователю. Например, при переходе к другой активности или закрытии приложения.
    override fun onStop() {
        super.onStop()

        showToast("Активность остановлена")
    }
    // onDestroy: Этот метод вызывается перед уничтожением экземпляра активности. Здесь можно освобождать ресурсы и завершать работу.
    override fun onDestroy() {
        super.onDestroy()

        showToast("Активность завершена")
    }
}