package com.example.lab03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity(), SecondFragment.OnSaveButtonListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        if (savedInstanceState == null) {
            val fragment = SecondFragment()

            // Получаем данные из первой активности
            val name = intent.getStringExtra(NOTE_NAME) ?: ""
            val description = intent.getStringExtra(NOTE_DESCRIPTION) ?: ""
            val isChecked = intent.getBooleanExtra(NOTE_IS_CHECKED, false)

            // Передаем данные во второй фрагмент
            fragment.setNote(name, description, isChecked)

            supportFragmentManager.beginTransaction().replace(R.id.SecondFragmentContainerView, fragment, "SecondFragment").commit()
        }
    }

    override fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean?) {
        val intent = Intent()
        intent.putExtra(NOTE_NAME, name)
        intent.putExtra(NOTE_DESCRIPTION, description)
        intent.putExtra(NOTE_IS_CHECKED, isChecked)
        setResult(RESULT_OK, intent)
        finish()
    }

}