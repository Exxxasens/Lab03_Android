package com.example.lab03

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var saveNoteButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        noteName = findViewById(R.id.editNoteName2)
        noteDescription = findViewById(R.id.editNoteDescription2)
        saveNoteButton = findViewById(R.id.saveNoteButton2)

        val bundle = intent.extras
        noteName?.setText(bundle?.getString(NOTE_NAME))
        noteDescription?.setText(bundle?.getString(NOTE_DESCRIPTION))

    }

    fun onSaveNote(view: View) {
        sendResult()
    }

    private fun sendResult() {
        val intent = Intent()
        intent.putExtra(NOTE_NAME, noteName?.text.toString())
        intent.putExtra(NOTE_DESCRIPTION, noteDescription?.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}