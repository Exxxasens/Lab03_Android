package com.example.lab03

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity()  {
    private lateinit var myViewModel: MyViewModel
    private var firstFragment: FirstFragment = FirstFragment.newInstance()

    private var secondActivity = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            myViewModel.updateNote(myViewModel.selectedIndex, it.name, it.description)
            firstFragment.refreshListView()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.MainFragmentContainerView, firstFragment, "FirstFragment").commit()
        }
    }
    fun runSecondAcitvity(id: Int) {
        val note = myViewModel.getNote(id)
        secondActivity.launch(
            object : EditNoteData {
                override val name = note.name
                override val description = note.description
            }
        )
    }

}