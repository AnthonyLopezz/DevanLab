package com.devan.lab.Adapter

import UserProgress
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Activity.CourseDetailActivity
import com.devan.lab.Models.Course
import com.devan.lab.R

class CourseProgressAdapter : RecyclerView.Adapter<CourseProgressAdapter.CourseViewHolder>() {

    private var courses: List<Pair<Course, UserProgress>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_ongoing, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val (course, progress) = courses[position]
        holder.bind(course, progress)
    }

    override fun getItemCount(): Int = courses.size

    fun setCourses(newCourses: Map<Course, UserProgress>) {
        courses = newCourses.toList()
        notifyDataSetChanged()
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val courseName: TextView = itemView.findViewById(R.id.course_title)
        private val courseIcon: ImageView = itemView.findViewById(R.id.pic)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val percentTxt: TextView = itemView.findViewById(R.id.percentTxt)

        fun bind(course: Course, progress: UserProgress) {
            courseName.text = course.title
            val context = itemView.context
            val iconResId = context.resources.getIdentifier(course.icon, "drawable", context.packageName)
            courseIcon.setImageResource(iconResId.takeIf { it != 0 } ?: R.drawable.placeholder_icon)
            percentTxt.text = "${progress.percentage.toInt()}%"
            progressBar.progress = progress.percentage.toInt()

            itemView.setOnClickListener {
                val intent = Intent(context, CourseDetailActivity::class.java).apply {
                    putExtra("course_id", course.id)
                }
                context.startActivity(intent)
            }
        }


    }
}
