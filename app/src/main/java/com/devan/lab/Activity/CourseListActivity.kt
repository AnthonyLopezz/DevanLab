package com.devan.lab.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Adapter.CourseAdapter
import com.devan.lab.Adapter.CourseProgressAdapter
import com.devan.lab.R
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.Utils.showCustomToast
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class CourseListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewProgress: RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var courseProgressAdapter: CourseProgressAdapter
    private lateinit var courseListTitle: TextView
    private lateinit var loadingAnimation: RelativeLayout
    private lateinit var filterCourse: EditText

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerViewProgress = findViewById(R.id.recyclerViewProgress)
        courseListTitle = findViewById(R.id.course_list_title)
        filterCourse = findViewById(R.id.filterCourse)
        recyclerViewProgress.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        initComponents()

        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title_name")
        courseListTitle.text = title
        if (category != null) {
            loadCourses(category)
        } else {
            Toast(this).showCustomToast("Could not complete the operation.", this, ToastType.ERROR)
        }

        filterCourse.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initComponents() {
        courseAdapter = CourseAdapter()
        courseProgressAdapter = CourseProgressAdapter()
        recyclerView.adapter = courseAdapter
        recyclerViewProgress.adapter = courseProgressAdapter
        loadingAnimation = findViewById(R.id.loading_animation)
        loadingAnimation.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        val category = intent.getStringExtra("category")
        if (category != null) {
            loadCourses(category)
        } else {
            Toast(this).showCustomToast("Could not complete the operation.", this, ToastType.ERROR)
        }
    }

    private fun loadCourses(category: String) {
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email: String? = prefs.getString("email", null)

        loadingAnimation.visibility = View.VISIBLE

        when (category) {
            "Your" -> {
                email?.let {
                    firebaseService.getAllUserProgress(it) { userProgressMap, error ->
                        if (error != null) {
                            ToastManager.showToast(error, this, ToastType.ERROR)
                            loadingAnimation.visibility = View.GONE
                        } else {
                            courseProgressAdapter.setCourses(userProgressMap)
                            loadingAnimation.visibility = View.GONE
                        }
                    }
                }
            }
            "All" -> {
                firebaseService.getAllCourses { courses, _ ->
                    if (courses != null) {
                        courseAdapter.setCourses(courses)
                        loadingAnimation.visibility = View.GONE
                    } else {
                        ToastManager.showToast("Could not complete the operation.", this, ToastType.ERROR)
                        loadingAnimation.visibility = View.GONE
                    }
                }
            }
            else -> {
                firebaseService.getCoursesByCategory(category) { courses, _ ->
                    if (courses != null) {
                        courseAdapter.setCourses(courses)
                        loadingAnimation.visibility = View.GONE
                    } else {
                        ToastManager.showToast("Could not complete the operation.", this, ToastType.ERROR)
                        loadingAnimation.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun filter(text: String) {
        val filteredCourseList = courseAdapter.getCourses().filter { course ->
            course.title.contains(text, ignoreCase = true)
        }
        courseAdapter.filterList(filteredCourseList)

        val filteredProgressList = courseProgressAdapter.getCourses().filter { (course, _) ->
            course.title.contains(text, ignoreCase = true)
        }
        courseProgressAdapter.filterList(filteredProgressList)
    }
}
