package com.example.lab03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

public class TaskModel(val id: Int, val name: String, val description: String) {
}

class MainActivity : AppCompatActivity() {
    val notes = arrayOf(
        TaskModel(0, "First", "Desc"),
        TaskModel(1, "Second", "Desc"),
        TaskModel(2, "Third", "Desc")
    )

    val noteName = findViewById<EditText>(R.id.editNote)
    val noteDescription = findViewById<EditText>(R.id.editNoteDescription)
    val addNoteButton = findViewById<Button>(R.id.addNoteButton)
    val saveNoteButton = findViewById<Button>(R.id.saveNoteButton)
    val showNoteButton = findViewById<Button>(R.id.showNoteButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }
}