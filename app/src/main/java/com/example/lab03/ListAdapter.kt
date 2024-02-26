package com.example.lab03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, dataArrayList: ArrayList<TaskModel?>?)  : ArrayAdapter<TaskModel?> (context, R.layout.list_item, dataArrayList!!){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listData = getItem(position)
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val noteName = view?.findViewById<TextView>(R.id.cardNoteName)
        val noteDescription = view?.findViewById<TextView>(R.id.cardNoteDescription)

        noteName?.text = listData?.name
        noteDescription?.text = listData?.description

        return view as View
    }

}