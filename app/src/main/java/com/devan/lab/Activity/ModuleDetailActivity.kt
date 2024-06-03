package com.devan.lab.Activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.devan.lab.Models.Course
import com.devan.lab.R
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ModuleDetailActivity : AppCompatActivity() {

    private lateinit var moduleName: TextView
    private lateinit var moduleDescription: TextView
    private lateinit var completeModule: ConstraintLayout
    private lateinit var moduleWebView: WebView
    private var course: Course? = null
    private lateinit var loadingAnimation: RelativeLayout


    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
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
                ToastManager.showToast("Please, try again.", this, ToastType.ERROR)

            }
        } else {
            ToastManager.showToast("No active session", this, ToastType.INFO)

        }
    }



    private fun initComponents() {
        moduleName = findViewById(R.id.module_name)
        moduleDescription = findViewById(R.id.moduleDesc)
        completeModule = findViewById(R.id.completeModule)
        moduleWebView = findViewById(R.id.moduleWebView)
        loadingAnimation = findViewById(R.id.loading_animation)

        loadingAnimation.visibility = View.GONE
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
                ToastManager.showToast("No active session", this, ToastType.ERROR)

            }
        }
    }

    private fun initData(courseId: Int, moduleId: Int) {
        loadingAnimation.visibility = View.VISIBLE
        firebaseService.getCourseById(courseId.toString()) { course, error ->

            if (course != null) {
                val module = course.modules.find { it.id == moduleId }
                Log.d("ModuleDetail", "MODULE: $module")

                if (module != null) {

                    loadingAnimation.visibility = View.GONE
                    moduleName.text = module.title
                    moduleDescription.text = module.description

                    moduleWebView.loadData(module.videoUrl, "text/html", "utf-8")
                    moduleWebView.settings.javaScriptEnabled = true

                } else {
                    ToastManager.showToast("Module not found.", this, ToastType.INFO)
                    loadingAnimation.visibility = View.GONE

                }
            } else {
                ToastManager.showToast("Please, try again later.", this, ToastType.INFO)
                loadingAnimation.visibility = View.GONE

            }
        }
    }

    private fun markModuleAsCompleted(email: String, courseId: Int, moduleId: Int) {
        Log.d("ModuleDetail", "Course $courseId Module $moduleId Email $email ")
        fetchCourse(courseId.toString())
        loadingAnimation.visibility = View.VISIBLE

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
                            loadingAnimation.visibility = View.GONE
                            ToastManager.showToast("Congratulations! You complete a module", this, ToastType.SUCCESS)
                        } else {
                            loadingAnimation.visibility = View.GONE
                            ToastManager.showToast("Module Completed", this, ToastType.INFO)
                        }
                    }
                }
            } else {
                loadingAnimation.visibility = View.GONE
                ToastManager.showToast("Module Completed", this, ToastType.INFO)

            }
        }
    }
}
