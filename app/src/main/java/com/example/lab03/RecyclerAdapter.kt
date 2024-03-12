package com.example.lab03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var dataArrayList: ArrayList<Note>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onCheckBoxClick(position: Note, isChecked: Boolean)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteName: TextView = itemView.findViewById(R.id.cardNoteName)
        val noteDescription: TextView = itemView.findViewById(R.id.cardNoteDescription)
        val noteCheckBox: CheckBox = itemView.findViewById(R.id.cardNoteCheckBox)
        val noteImage: ImageView = itemView.findViewById(R.id.cardNoteImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listData = dataArrayList[position]

        holder.noteName.text = listData.name
        holder.noteDescription.text = listData.description
        holder.noteCheckBox.isChecked = listData.isChecked == true
        holder.noteImage.setImageResource(listData.resourceId)

        holder.itemView.setOnClickListener {
            // Notify the listener about the item click
            onItemClickListener.onItemClick(dataArrayList[position].id)
        }

        holder.noteCheckBox.setOnClickListener {
            val isChecked = holder.noteCheckBox.isChecked
            onItemClickListener.onCheckBoxClick(dataArrayList[position], isChecked)
        }

    }


    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    fun updateData(newData: List<Note>) {
        dataArrayList = ArrayList(newData)
        notifyDataSetChanged()
    }
}