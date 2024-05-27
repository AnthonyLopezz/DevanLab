package com.devan.lab.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.R


class ArchiveAdapter(private val items: List<String>) :
    RecyclerView.Adapter<ArchiveAdapter.Viewholder>() {

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_archive, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.title.text = item

    }
}