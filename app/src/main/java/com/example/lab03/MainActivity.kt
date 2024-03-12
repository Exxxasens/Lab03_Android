package com.example.lab03

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), SecondFragment.OnSaveButtonListener  {
    private lateinit var myViewModel: MyViewModel
    private var firstFragment: FirstFragment = FirstFragment.newInstance()
    private var secondFragment: SecondFragment? = null

    private var editNote = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            val selectedIndex  = myViewModel.getSelectedIndex()
            myViewModel.updateNote(selectedIndex, it.name, it.description, it.isChecked)
        }
    }

    private var createNote = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            myViewModel.createNote(it.name, it.description, it.isChecked)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        supportFragmentManager.beginTransaction().replace(R.id.MainFragmentContainerView,
            firstFragment, "FirstFragment").commit()

        if (findViewById<FragmentContainerView>(R.id.SecondFragmentContainerView) != null) {
            secondFragment = SecondFragment()
            setDataSecondFragment(myViewModel.getSelectedIndex())
            supportFragmentManager.beginTransaction().replace(R.id.SecondFragmentContainerView,
                secondFragment!!, "SecondFragment").commit()
        }

        myViewModel.noteIndex.observe(this) { index ->
            if (index != null) {
                onIndexSelect(index)
            }
        }

        if (secondFragment != null) {
            myViewModel.notes.observe(this, Observer<List<TaskModel>> { notes ->
                setDataSecondFragment(myViewModel.getSelectedIndex())
            })
        }

    }
    private fun runEditNote(index: Int) {
        val note = myViewModel.getNote(index)
        if (note != null) {
            editNote.launch(
                object : InputNoteData {
                    override val name = note.name
                    override val description = note.description
                    override val isChecked = note.isChecked
                    override val isCreating = false
                }
            )
        }
    }

    private fun runCreateNote() {
        createNote.launch(object : InputNoteData {
            override val name = ""
            override val description = ""
            override val isChecked = false
            override val isCreating = true
        })
    }

    private fun onIndexSelect(index: Int) {
        if (secondFragment != null) {
            setDataSecondFragment(index)
            return
        }
        runEditNote(index)
    }

    private fun setDataSecondFragment(index: Int) {
        val note = myViewModel.getNote(index)
        if (note != null) {
            secondFragment?.setNote(note.name, note.description, note.isChecked)
        }
    }

    override fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean?) {
        val selectedIndex = myViewModel.getSelectedIndex()
        myViewModel.updateNote(selectedIndex, name, description, isChecked ?: false)
    }

    fun onNoteCreate(view: View) {
        runCreateNote()
    }

}