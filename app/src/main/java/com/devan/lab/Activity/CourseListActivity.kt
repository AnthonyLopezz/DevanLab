package com.devan.lab.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Adapter.CourseAdapter
import com.devan.lab.Adapter.CourseProgressAdapter
import com.devan.lab.R
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CourseListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewProgress: RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var courseProgressAdapter: CourseProgressAdapter
    private lateinit var course_list_title: TextView

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerViewProgress = findViewById(R.id.recyclerViewProgress)
        course_list_title = findViewById(R.id.course_list_title)
        recyclerViewProgress.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        courseAdapter = CourseAdapter()
        courseProgressAdapter = CourseProgressAdapter()
        recyclerView.adapter = courseAdapter
        recyclerViewProgress.adapter = courseProgressAdapter

        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title_name")
        course_list_title.text = title
        if (category != null) {
            loadCourses(category)
        } else {
            showError("No category provided.")
        }
    }

    override fun onStart() {
        super.onStart()
        val category = intent.getStringExtra("category")
        if (category != null) {
            loadCourses(category)
        } else {
            showError("No category provided.")
        }
    }

    private fun loadCourses(category: String) {
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email: String? = prefs.getString("email", null)

        if (category == "Your") {
            email?.let {
                firebaseService.getAllUserProgress(it) { userProgressMap, error ->
                    if (error != null) {
                        showError(error)
                    } else {
                        courseProgressAdapter.setCourses(userProgressMap)
                    }
                }
            }
        } else if (category == "All") {
            firebaseService.getAllCourses { courses, error ->
                if (courses != null) {
                    courseAdapter.setCourses(courses)
                } else {
                    showError(error ?: "An error occurred while fetching courses.")
                }
            }
        } else {
            firebaseService.getCoursesByCategory(category) { courses, error ->
                if (courses != null) {
                    courseAdapter.setCourses(courses)
                } else {
                    showError(error ?: "An error occurred while fetching courses.")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
