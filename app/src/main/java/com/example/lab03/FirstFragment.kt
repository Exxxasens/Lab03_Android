package com.example.lab03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class FirstFragment : Fragment() {
    // ViewModel
    private lateinit var myViewModel: MyViewModel
    private var listAdapter: ListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myViewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listView = view.findViewById<ListView>(R.id.listView)
        listAdapter = ListAdapter(view.context, ArrayList(myViewModel.getAllNotes()))
        listView.adapter = listAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener{ parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            if (context is MainActivity) {
                (context as MainActivity).runSecondAcitvity(position)
                myViewModel.selectedIndex = position
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()

    }

    fun refreshListView() {
        listAdapter?.notifyDataSetChanged()
    }

}