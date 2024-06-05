package com.devan.lab.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.R

class QuestionAdapter(
    private val correctAnswer: String,
    private val options: List<String>,
    private val listener: OnAnswerSelectedListener
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    interface OnAnswerSelectedListener {
        fun onAnswerSelected(answer: String)
    }

    private var selectedAnswer: String? = null
    private var isAnswered: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val option = options[position]
        holder.bind(option)
    }

    override fun getItemCount(): Int = options.size

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val answerTxt: TextView = itemView.findViewById(R.id.answerTxt)

        fun bind(option: String) {
            answerTxt.text = option

            answerTxt.setOnClickListener {
                if (!isAnswered) {
                    selectedAnswer = option
                    isAnswered = true
                    listener.onAnswerSelected(option)
                    notifyDataSetChanged()
                }
            }

            if (isAnswered) {
                when {
                    option == correctAnswer -> {
                        answerTxt.setBackgroundColor(itemView.context.getColor(R.color.correct_answer))
                    }

                    option == selectedAnswer -> {
                        answerTxt.setBackgroundColor(itemView.context.getColor(R.color.error_answer))
                    }
                }
            }
        }
    }
}
