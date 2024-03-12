package com.example.lab03

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

interface OutputNoteData {
    val name: String
    val description: String
    val isChecked: Boolean
    val id: Int
    val shouldDelete: Boolean
}

interface InputNoteData {
    val id: Int?
    val name: String
    val description: String
    val isChecked: Boolean
    val isCreating: Boolean?
}

const val NOTE_ID = "NOTE_ID"
const val NOTE_NAME = "NOTE_NAME"
const val NOTE_DESCRIPTION = "NOTE_DESCRIPTION"
const val NOTE_IS_CHECKED = "NOTE_IS_CHECKED"
const val NOTE_SHOULD_DELETE = "NOTE_SHOULD_DELETE"

class SecondActivityContract : ActivityResultContract<InputNoteData, OutputNoteData?>() {
    override fun createIntent(context: Context, input: InputNoteData): Intent {
        return Intent(context, SecondActivity::class.java)
            .putExtra(NOTE_ID, input.id)
            .putExtra(NOTE_NAME, input.name)
            .putExtra(NOTE_DESCRIPTION, input.description)
            .putExtra(NOTE_IS_CHECKED, input.isChecked)

    }

    override fun parseResult(resultCode: Int, intent: Intent?): OutputNoteData? {
        if (intent == null) {
            return null
        }

        if (resultCode != Activity.RESULT_OK) {
            return null
        }

        return object : OutputNoteData {
            override val id: Int = intent.getIntExtra(NOTE_ID, 0)
            override val name = intent.getStringExtra(NOTE_NAME) ?: ""
            override val description = intent.getStringExtra(NOTE_DESCRIPTION) ?: ""
            override val isChecked = intent.getBooleanExtra(NOTE_IS_CHECKED, false)
            override val shouldDelete = intent.getBooleanExtra(NOTE_SHOULD_DELETE, false)
        }

    }
}