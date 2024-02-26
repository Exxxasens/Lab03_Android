package com.example.lab03

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


class SecondFragment : Fragment() {

    // Interface for communication with the activity
    interface OnSaveButtonListener {
        fun onSaveButtonClicked(name: String, description: String)
    }

    // Declare an instance of the interface
    var onButtonClickListener: OnSaveButtonListener? = null


    private var noteNameString: String? = null
    private var noteDescriptionString: String? = null

    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var saveNoteButton: Button? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the hosting activity implements the interface
        if (context is OnSaveButtonListener) {
            onButtonClickListener = context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        if (savedInstanceState != null) {
            noteName?.setText(savedInstanceState.getString("CURRENT_NAME"))
            noteDescription?.setText(savedInstanceState.getString("CURRENT_DESCRIPTION"))
        } else {
            noteName?.setText(noteNameString)
            noteDescription?.setText(noteDescriptionString)
        }


        saveNoteButton?.setOnClickListener {
            onButtonClickListener?.onSaveButtonClicked(noteName?.text.toString(), noteDescription?.text.toString())
        }

    }

    public fun setNoteName(name: String?) {
        noteNameString = name ?: ""
    }

    public fun setNoteDescription(description: String?) {
        noteDescriptionString = description ?: ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_NAME", noteName?.text.toString())
        outState.putString("CURRENT_DESCRIPTION", noteDescription?.text.toString())
    }

}