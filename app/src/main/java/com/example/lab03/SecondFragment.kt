package com.example.lab03

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment


class SecondFragment : Fragment() {

    interface ButtonListener {
        fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean, id: Int)
        fun onDeleteButtonClicked(id: Int)
        fun onCreateButtonClicked(name: String, description: String, isChecked: Boolean)
    }

    // Declare an instance of the interface
    private var buttonListener: ButtonListener? = null

    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var checkBox: CheckBox? = null
    private var saveNoteButton: Button? = null
    private var deleteButton: Button? = null

    private var id: Int? = null
    private var shouldCreate: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the hosting activity implements the interface
        if (context is ButtonListener) {
            buttonListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentView = getView()
        noteName = fragmentView?.findViewById(R.id.noteName)
        noteDescription = fragmentView?.findViewById(R.id.noteDescription)
        saveNoteButton = fragmentView?.findViewById(R.id.saveNoteButton)
        checkBox = fragmentView?.findViewById(R.id.checkBox)
        deleteButton = fragmentView?.findViewById(R.id.deleteButton)

        arguments?.let {
            if (it.getBoolean(NOTE_SHOULD_CREATE)) {
                saveNoteButton?.text = "Создать"
                shouldCreate = true
            } else {
                noteName?.setText(it.getString(NOTE_NAME))
                noteDescription?.setText(it.getString(NOTE_DESCRIPTION))
                checkBox?.isChecked = it.getBoolean(NOTE_IS_CHECKED)
                id = it.getInt(NOTE_ID)
            }

        }

        saveNoteButton?.setOnClickListener {
            val name = noteName?.text.toString()
            val description = noteDescription?.text.toString()
            val isChecked = checkBox?.isChecked ?: false

            if (shouldCreate) {
                buttonListener?.onCreateButtonClicked(name, description, isChecked)
            } else if (id != null) {
                buttonListener?.onSaveButtonClicked(name, description, isChecked, id!!)
            }
        }

        deleteButton?.setOnClickListener {
            if (id != null) {
                buttonListener?.onDeleteButtonClicked(id!!)
            }

        }
    }


}