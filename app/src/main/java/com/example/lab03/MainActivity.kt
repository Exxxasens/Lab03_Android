package com.example.lab03

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private var firstFragment: FirstFragment = FirstFragment.newInstance()


    private lateinit var myViewModel: NoteViewModel
    private lateinit var database: NoteDatabase

    private var editNote = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            if (it.shouldDelete) {
                myViewModel.deleteNote(it.id)

            } else {
                myViewModel.updateNote(Note(it.name, it.description, it.isChecked, R.drawable.no_image, it.id))
            }
        }

    }

    private var createNote = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            myViewModel.insertNote(Note(it.name, it.description, it.isChecked, R.drawable.no_image))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = NoteDatabase.getDatabase(applicationContext)

        // Creating ViewModel
        val factory = TaskViewModelFactory(database.dao)
        myViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        supportFragmentManager.beginTransaction().replace(R.id.MainFragmentContainerView,
            firstFragment, "FirstFragment").commit()


        myViewModel.selectedNoteId.observe(this) { id ->
            if (id != null) {
                val task = GetNoteByIdAsyncTask(database.dao) {
                    runEditNote(it)
                }.execute(id)
            }
        }


    }


    fun onNoteCreate(view: View) {
        createNote.launch(object : InputNoteData {
            override val id = null
            override val name = ""
            override val description = ""
            override val isChecked = false
            override val isCreating = true
        })
    }

    private fun runEditNote(note: Note) {
        editNote.launch(
            object : InputNoteData {
                override val id = note.id
                override val name = note.name
                override val description = note.description
                override val isChecked = note.isChecked
                override val isCreating = false
            }
        )
    }

    class GetNoteByIdAsyncTask(private val noteDao: NoteDao, private val callback: (Note) -> Unit) : AsyncTask<Int, Void, Note?> () {
        override fun doInBackground(vararg params: Int?): Note? {
            return noteDao.getNote(params[0] ?: return null)
        }

        override fun onPostExecute(result: Note?) {
            super.onPostExecute(result)
            if (result != null) {
                callback(result)
            }
        }
    }


}

