package com.example.lab03

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

const val NOTE_ID = "NOTE_ID"
const val NOTE_NAME = "NOTE_NAME"
const val NOTE_DESCRIPTION = "NOTE_DESCRIPTION"
const val NOTE_IS_CHECKED = "NOTE_IS_CHECKED"
const val NOTE_SHOULD_CREATE = "NOTE_SHOULD_CREATE"


class MainActivity : AppCompatActivity(), SecondFragment.ButtonListener {
    private lateinit var myViewModel: NoteViewModel
    private lateinit var database: NoteDatabase
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        database = NoteDatabase.getDatabase(applicationContext)

        // Creating ViewModel
        val factory = TaskViewModelFactory(database.dao)
        myViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]


        myViewModel.selectedNoteId.observe(this) { id ->
            if (id != null) {
                GetNoteByIdAsyncTask(database.dao) {
                    navigateToEditNote(it)
                }.execute(id)
            }
        }


    }

    private fun navigateToEditNote(note: Note) {
        val bundle = Bundle().apply {
            putInt(NOTE_ID, note.id)
            putString(NOTE_NAME, note.name)
            putString(NOTE_DESCRIPTION, note.description)
            putBoolean(NOTE_IS_CHECKED, note.isChecked)
        }
        navController.navigate(R.id.action_main_to_edit_note, bundle)
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

    override fun onCreateButtonClicked(name: String, description: String, isChecked: Boolean) {
        navController.navigate(R.id.action_secondFragment_to_firstFragment)
        myViewModel.insertNote(Note(name, description, isChecked, R.drawable.no_image))
    }

    override fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean, id: Int) {
        navController.navigate(R.id.action_secondFragment_to_firstFragment)
        myViewModel.updateNote(Note(name, description, isChecked, R.drawable.no_image, id))
    }

    override fun onDeleteButtonClicked(id: Int) {
        navController.navigate(R.id.action_secondFragment_to_firstFragment)
        myViewModel.deleteNote(id)
    }

}

