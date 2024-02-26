package com.example.lab03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity(), SecondFragment.OnSaveButtonListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        if (savedInstanceState == null) {
            var fragment = SecondFragment()
            fragment.setNoteName(intent.getStringExtra(NOTE_NAME))
            fragment.setNoteDescription(intent.getStringExtra(NOTE_DESCRIPTION))
            Log.d("ADD_FRAGMENT", "new added second")
            supportFragmentManager.beginTransaction().replace(R.id.SecondFragmentContainerView, fragment, "SecondFragment").commit()
        }
    }

    override fun onSaveButtonClicked(name: String, description: String) {
        val intent = Intent()
        intent.putExtra(NOTE_NAME, name)
        intent.putExtra(NOTE_DESCRIPTION,description)
        setResult(RESULT_OK, intent)
        finish()
    }

}