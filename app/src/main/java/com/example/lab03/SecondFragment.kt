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
        fun onSaveButtonClicked(name: String, description: String, isChecked: Boolean?)
        fun onDeleteButtonClicked()
    }

    // Declare an instance of the interface
    private var buttonListener: ButtonListener? = null


    private var name: String? = null
    private var description: String? = null
    private var isChecked: Boolean = false

    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var checkBox: CheckBox? = null
    private var saveNoteButton: Button? = null
    private var deleteButton: Button? = null

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

        if (savedInstanceState == null) {
            noteName?.setText(name)
            noteDescription?.setText(description)
            checkBox?.isChecked = isChecked as Boolean
        }

        saveNoteButton?.setOnClickListener {
            buttonListener?.onSaveButtonClicked(noteName?.text.toString(), noteDescription?.text.toString(), checkBox?.isChecked)
        }

        deleteButton?.setOnClickListener {
            buttonListener?.onDeleteButtonClicked()
        }
    }

    fun setNote(n: String, d: String, b: Boolean) {
        name = n
        description = d
        isChecked = b

        noteName?.setText(name)
        noteDescription?.setText(description)
        checkBox?.isChecked = isChecked as Boolean
    }


}