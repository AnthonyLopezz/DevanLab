package com.devan.lab.Activity

import UserProgress
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.devan.lab.Models.Course
import com.devan.lab.R
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.Utils.showAlert
import com.devan.lab.Utils.showConfirm
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ModuleDetailActivity : AppCompatActivity() {

    private lateinit var moduleName: TextView
    private lateinit var moduleDescription: TextView
    private lateinit var completeModule: ConstraintLayout
    private lateinit var moduleWebView: WebView
    private var course: Course? = null

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_module_detail)
        initComponents()

        val prefs: SharedPreferences =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        val courseId: Int = prefs.getInt("course_id", 0)
        val moduleId = intent.getIntExtra("module_id", -1)
        val email: String? = prefs.getString("email", null)

        if (email != null) {
            setup(courseId, moduleId)
            Log.d("ModuleDetail", "Course ID $courseId, Module ID $moduleId")

            if (courseId != -1 && moduleId != -1) {
                initData(courseId, moduleId)
            } else {
                Log.e("ModuleDetail", "Invalid course or module ID")
            }
        } else {
            Log.e("ModuleDetail", "User email is null")
        }
    }



    private fun initComponents() {
        moduleName = findViewById(R.id.module_name)
        moduleDescription = findViewById(R.id.moduleDesc)
        completeModule = findViewById(R.id.completeModule)
        moduleWebView = findViewById(R.id.moduleWebView)
    }

    private fun fetchCourse(id: String) {
        firebaseService.getCourseById(id) { course, error ->
            if (course != null) {
                this.course = course
                Log.d("CourseGot", "COURSE: ${this.course}")

            }
        }
    }

    private fun setup(courseId: Int, moduleId: Int) {
        completeModule.setOnClickListener {
            performClickAnimation(completeModule)
            val prefs: SharedPreferences =
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
            val email: String? = prefs.getString("email", null)
            if (email != null) {
                markModuleAsCompleted(email, courseId, moduleId)
            } else {
                Log.e("ModuleDetail", "User email is null")
            }
        }
    }

    private fun initData(courseId: Int, moduleId: Int) {
        firebaseService.getCourseById(courseId.toString()) { course, error ->
            if (course != null) {
                val module = course.modules.find { it.id == moduleId }
                if (module != null) {
                    moduleName.text = module.title
                    moduleDescription.text = module.description

                    moduleWebView.loadData(module.videoUrl, "text/html", "utf-8")
                    moduleWebView.settings.javaScriptEnabled = true

                } else {
                    Log.e("ModuleDetail", "Module not found")
                }
            } else {
                Log.e("ModuleDetail", error ?: "Error fetching course data")
            }
        }
    }

    private fun markModuleAsCompleted(email: String, courseId: Int, moduleId: Int) {
//        showAlert("Course $courseId Module $moduleId Email $email ", this)

        fetchCourse(courseId.toString())

        firebaseService.getUserProgress(email, courseId) { userProgress, error ->
            Log.d("ModuleDetail", "userProgress: $userProgress")

            if (userProgress != null && course != null) {
                val updatedCompletedModules = userProgress.completedModules.toMutableList()
                if (!updatedCompletedModules.contains(moduleId)) {
                    updatedCompletedModules.add(moduleId)
                    val newPercentage = (updatedCompletedModules.size.toDouble() / course!!.modules.size) * 100
                    val updatedUserProgress = userProgress.copy(
                        completedModules = updatedCompletedModules,
                        percentage = newPercentage
                    )
                    firebaseService.updateUserProgress(email, updatedUserProgress) { success, updateError ->
                        if (success) {
                            showConfirm("Module marked as completed", this)
                            Log.d("ModuleDetail", "Module marked as completed")
                        } else {
                            showAlert("Error updating progress", this)
                            Log.e("ModuleDetail", "Error updating progress: $updateError")
                        }
                    }
                }
            } else {
                Log.e("ModuleDetail", error ?: "Error fetching user progress")
            }
        }
    }
}
