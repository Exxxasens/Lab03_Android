package com.example.lab03

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

interface EditNoteData {
    val name: String
    val description: String
}

const val NOTE_NAME = "NOTE_NAME"
const val NOTE_DESCRIPTION = "NOTE_DESCRIPTION"


class EditNoteContract : ActivityResultContract<EditNoteData, EditNoteData?>() {
    override fun createIntent(context: Context, input: EditNoteData): Intent {
        return Intent(context, SecondActivity::class.java)
            .putExtra(NOTE_NAME, input.name)
            .putExtra(NOTE_DESCRIPTION, input.description)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): EditNoteData? {
        if (intent == null) {
            return null
        }

        if (resultCode != Activity.RESULT_OK) {
            return null
        }

        return object : EditNoteData {
            override val name = intent.getStringExtra(NOTE_NAME) ?: ""
            override val description = intent.getStringExtra(NOTE_DESCRIPTION) ?: ""
        }

    }
}