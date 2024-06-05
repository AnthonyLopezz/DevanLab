package com.devan.lab.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Models.Concept
import com.devan.lab.R

class ConceptAdapter : RecyclerView.Adapter<ConceptAdapter.ConceptViewHolder>() {

    private var concepts: List<Concept> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConceptViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_concept, parent, false)
        return ConceptViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConceptViewHolder, position: Int) {
        holder.bind(concepts[position])
    }

    fun setConcepts(newConcepts: List<Concept>) {
        concepts = newConcepts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = concepts.size


    class ConceptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val conceptName: TextView = itemView.findViewById(R.id.conceptTitle)
        private val conceptDescription: TextView = itemView.findViewById(R.id.conceptDesc)
        private val category: TextView = itemView.findViewById(R.id.conceptCategory)

        fun bind(concept: Concept) {
            conceptName.text = concept.title
            conceptDescription.text = concept.description
            category.text = concept.category
        }
    }
}