package com.devan.lab.Adapter

import android.content.Intent

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Activity.CourseDetailActivity
import com.devan.lab.Models.Course
import com.devan.lab.R


class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {


    private var courses: List<Course> = listOf()
    private var courseListFull: List<Course> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])

        holder.itemView.setOnClickListener {
            val course = courses[position]
            holder.onClick(course.id)
        }

    }

    override fun getItemCount(): Int = courses.size

    fun setCourses(newCourses: List<Course>) {
        courses = newCourses
        courseListFull = ArrayList(courses)
        notifyDataSetChanged()
    }

    fun filterList(filteredList: List<Course>) {
        courses = filteredList
        notifyDataSetChanged()
    }

    fun getCourses(): List<Course> {
        return courseListFull
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        private val courseName: TextView = itemView.findViewById(R.id.course_title)
        private val courseIcon: ImageView = itemView.findViewById(R.id.pic)
        private lateinit var course: Course

        fun bind(course: Course) {
            courseName.text = course.title
            val context = itemView.context
            val iconResId = context.resources.getIdentifier(course.icon, "drawable", context.packageName)
            if (iconResId != 0) {
                courseIcon.setImageResource(iconResId)
            } else {
                courseIcon.setImageResource(R.drawable.placeholder_icon)
            }
        }

        fun onClick(courseId: Int) {
            Log.d("CourseAdapter", "Course ID $courseId")
            val context = itemView.context
            val intent = Intent(context, CourseDetailActivity::class.java).apply {
                putExtra("course_id", courseId)
            }

            context.startActivity(intent)
        }
    }
}

