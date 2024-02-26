package com.example.lab03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
// private const val ARG_PARAM1 = "param1"
// private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    // private var param1: String? = null
    // private var param2: String? = null


    // ViewModel
    private lateinit var viewModel: MyViewModel

    // Displayed index of TaskModel
    private var taskModelIndex = 0

    private var noteName: EditText? = null
    private var noteDescription: EditText? = null
    private var addNoteButton: Button? = null
    private var editNoteButton: Button? = null
    private var previousNoteButton: AppCompatImageButton? = null
    private var nextNoteButton: AppCompatImageButton? = null
    private var lastNoteButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val fragmentView = getView()
        noteName = fragmentView?.findViewById(R.id.editNoteName)
        noteDescription = fragmentView?.findViewById(R.id.editNoteDescription)
        addNoteButton = fragmentView?.findViewById(R.id.addNoteButton)
        editNoteButton = fragmentView?.findViewById(R.id.editNoteButton)
        previousNoteButton = fragmentView?.findViewById(R.id.prevNoteButton)
        nextNoteButton = fragmentView?.findViewById(R.id.nextNoteButton)
        lastNoteButton = fragmentView?.findViewById(R.id.lastNoteButton)

        addNoteButton?.setOnClickListener(::onAddNote)
        editNoteButton?.setOnClickListener(::onNoteEdit)
        previousNoteButton?.setOnClickListener(::onPreviousNote)
        nextNoteButton?.setOnClickListener(::onNextNote)
        lastNoteButton?.setOnClickListener(::showLastNote)

        // Restore State from savedInstanceState
        if (savedInstanceState != null) {
            Log.d("STATE", "Restore State")
            taskModelIndex = savedInstanceState.getInt(CURRENT_NOTE)
            noteName?.setText(savedInstanceState.getString(NOTE_NAME))
            noteDescription?.setText(savedInstanceState.getString(NOTE_DESCRIPTION))
        }

        displayTaskModel()

    }

    private fun displayTaskModel() {
        val note = viewModel.getNote(taskModelIndex)
        noteName?.setText(note.name.toString())
        noteDescription?.setText(note.description.toString())
    }



    companion object {

        const val CURRENT_NOTE = "CURRENT_NOTE"
        const val NOTE_NAME = "NOTE_NAME"
        const val NOTE_DESCRIPTION = "NOTE_DESCRIPTION"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SecondFragment()
    }

    fun onNoteEdit(view: View) {

    }
    fun onAddNote(view: View) {
        viewModel.addNote("", "")
        taskModelIndex = viewModel.getSize() - 1
        displayTaskModel()
    }

    private fun onPreviousNote(view: View) {
        if (taskModelIndex > 0) {
            taskModelIndex--
            displayTaskModel()
        }
    }
    private fun onNextNote(view: View) {
        if (taskModelIndex < viewModel.getSize() - 1) {
            taskModelIndex++
            displayTaskModel()
        }
    }

    private fun showLastNote(view: View) {
        taskModelIndex = viewModel.getSize() - 1
        displayTaskModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("STATE", "Saving state")
        // Save State
        outState.putInt(CURRENT_NOTE, taskModelIndex)
        outState.putString(NOTE_NAME, noteName?.text.toString())
        outState.putString(NOTE_DESCRIPTION, noteDescription?.text.toString())
    }



}