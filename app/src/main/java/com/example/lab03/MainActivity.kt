package com.example.lab03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), SecondFragment.OnSaveButtonListener  {
    private lateinit var myViewModel: MyViewModel
    private var firstFragment: FirstFragment = FirstFragment.newInstance()
    private var secondFragment: SecondFragment? = null

    private var selectedIndex: Int = 0

    private var secondActivity = registerForActivityResult(SecondActivityContract()) {
        if (it != null) {
            myViewModel.updateNote(selectedIndex, it.name, it.description, it.isChecked)
            firstFragment.refreshList(selectedIndex)
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
    private fun runSecondAcitvity() {
        val note = myViewModel.getNote(selectedIndex)
        secondActivity.launch(
            object : EditNoteData {
                override val name = note.name
                override val description = note.description
                override val isChecked = note.isChecked
            }
        )
    }

    fun setSelectedIndex(index: Int) {
        selectedIndex = index
        if (secondFragment != null) {
            setDataSecondFragment()
            return
        }
        runSecondAcitvity()
    }

    fun onCheckboxClicked(index: Int, isChecked: Boolean) {
        myViewModel.updateNote(index, isChecked)
        // Если два фрагмента на экране, обновить на втором экране чекбокс
        if (secondFragment != null) {
            setDataSecondFragment()
        }
    }

    private fun setDataSecondFragment() {
        val note = myViewModel.getNote(selectedIndex)
        secondFragment?.setNote(note.name, note.description, note.isChecked)
    }

    override fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean?) {
        myViewModel.updateNote(selectedIndex, name, description, isChecked ?: false)
        firstFragment.refreshList(selectedIndex)
    }

}