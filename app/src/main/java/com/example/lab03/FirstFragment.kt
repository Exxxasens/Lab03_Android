package com.example.lab03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FirstFragment : Fragment() {
    // ViewModel
    private lateinit var myViewModel: MyViewModel
    private lateinit var myAdapter: RecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myViewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        myAdapter = RecyclerAdapter(ArrayList(myViewModel.getAllNotes()), object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                myViewModel.selectNoteIndex(position)
            }
            override fun onCheckBoxClick(position: Int, isChecked: Boolean) {
                myViewModel.updateNote(position, isChecked)
            }
        })

        myViewModel.notes.observe(viewLifecycleOwner) { notes ->
            myAdapter.updateData(notes)
        }

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = myAdapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()
    }


}