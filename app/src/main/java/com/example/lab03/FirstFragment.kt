package com.example.lab03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FirstFragment : Fragment() {
    // ViewModel
    private lateinit var myViewModel: NoteViewModel
    private lateinit var myAdapter: RecyclerAdapter

    private var createNoteButton: AppCompatButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myViewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]

        myAdapter = RecyclerAdapter(ArrayList(), object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                myViewModel.selectNoteId(id)
            }
            override fun onCheckBoxClick(note: Note, isChecked: Boolean) {
                myViewModel.updateNote(Note(note.name, note.description, isChecked, note.resourceId, note.id))
            }
        })

        myViewModel.notes.observe(viewLifecycleOwner) { task ->
            myAdapter.updateData(task)
        }

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = myAdapter

        val fragmentView = getView()
        createNoteButton = fragmentView?.findViewById(R.id.createNoteButton)

        createNoteButton?.setOnClickListener {
            val bundle = Bundle().apply {
                putBoolean(NOTE_SHOULD_CREATE, true)
            }
            findNavController().navigate(R.id.action_main_to_edit_note, bundle)
        }
    }


}