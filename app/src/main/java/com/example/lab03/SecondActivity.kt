package com.example.lab03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity(), SecondFragment.ButtonListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        if (savedInstanceState == null) {
            val fragment = SecondFragment()

            // Получаем данные из первой активности
            val name = intent.getStringExtra(NOTE_NAME) ?: ""
            val description = intent.getStringExtra(NOTE_DESCRIPTION) ?: ""
            val isChecked = intent.getBooleanExtra(NOTE_IS_CHECKED, false)

            Log.d("RECIVED FROM FIRST", intent.getIntExtra(NOTE_ID, -1).toString())

            // Передаем данные во второй фрагмент
            fragment.setNote(name, description, isChecked)

            supportFragmentManager.beginTransaction().replace(R.id.SecondFragmentContainerView, fragment, "SecondFragment").commit()
        }
    }

    override fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean?) {
        val noteId = intent.getIntExtra(NOTE_ID, -1)
        val intent = Intent()
        intent.putExtra(NOTE_ID, noteId)
        intent.putExtra(NOTE_NAME, name)
        intent.putExtra(NOTE_DESCRIPTION, description)
        intent.putExtra(NOTE_IS_CHECKED, isChecked)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onDeleteButtonClicked() {
        val noteId = intent.getIntExtra(NOTE_ID, -1)
        val intent = Intent()
        intent.putExtra(NOTE_ID, noteId)
        intent.putExtra(NOTE_SHOULD_DELETE, true)
        setResult(RESULT_OK, intent)
        finish()
    }

}