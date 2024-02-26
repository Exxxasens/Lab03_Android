package com.example.lab03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), SecondFragment.OnSaveButtonListener  {
    private lateinit var myViewModel: MyViewModel
    private var firstFragment: FirstFragment = FirstFragment.newInstance()
    private var secondFragment: SecondFragment? = null

    private var selectedIndex: Int = 0

    private var secondActivity = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            myViewModel.updateNote(selectedIndex, it.name, it.description)
            firstFragment.refreshListView()
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
            setDataSecondFragment()
            supportFragmentManager.beginTransaction().replace(R.id.SecondFragmentContainerView,
                secondFragment!!, "SecondFragment").commit()
        }

    }
    fun runSecondAcitvity() {
        val note = myViewModel.getNote(selectedIndex)
        secondActivity.launch(
            object : EditNoteData {
                override val name = note.name
                override val description = note.description
            }
        )
    }

    fun setSelectedIndex(index: Int) {
        selectedIndex = index

        if (secondFragment != null) {
            setDataSecondFragment()
        } else {
            runSecondAcitvity()
        }
    }

    fun setDataSecondFragment() {
        val note = myViewModel.getNote(selectedIndex)
        secondFragment!!.setNoteName(note.name)
        secondFragment!!.setNoteDescription(note.description)
    }

    override fun onSaveButtonClicked(name: String, description: String) {
        myViewModel.updateNote(selectedIndex, name, description)
        firstFragment.refreshListView()
    }

}