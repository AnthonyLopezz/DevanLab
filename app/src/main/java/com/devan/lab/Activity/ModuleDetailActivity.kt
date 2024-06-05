package com.devan.lab.Activity


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Activity.QuizActivity
import com.devan.lab.Adapter.ConceptAdapter
import com.devan.lab.Models.Course
import com.devan.lab.R
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ModuleDetailActivity : AppCompatActivity() {

    private lateinit var moduleName: TextView
    private lateinit var answerQuizBtn: Button
    private lateinit var moduleDescription: TextView
    private lateinit var moduleWebView: WebView
    private lateinit var recyclerViewConcept: RecyclerView
    private lateinit var conceptAdapter: ConceptAdapter
    private lateinit var loadingAnimation: RelativeLayout

    private var course: Course? = null

    private val firebaseService by lazy {
        FirebaseService(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance(),
            FirebaseStorage.getInstance()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_module_detail)

        recyclerViewConcept = findViewById(R.id.recyclerViewConcepts)
        recyclerViewConcept.layoutManager = LinearLayoutManager(this)

        initComponents()

        val prefs: SharedPreferences =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        val courseId: Int = prefs.getInt("course_id", 0)
        val moduleId = intent.getIntExtra("module_id", -1)
        val email: String? = prefs.getString("email", null)

        if (email != null) {
            setup(courseId, moduleId, email)
            Log.d("ModuleDetail", "Course ID $courseId, Module ID $moduleId, EMAIL: $email")

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
        conceptAdapter = ConceptAdapter()
        recyclerViewConcept.adapter = conceptAdapter
        moduleName = findViewById(R.id.module_name)
        moduleDescription = findViewById(R.id.moduleDesc)
        answerQuizBtn = findViewById(R.id.answerQuizBtn)
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
    private fun setup(courseId: Int, moduleId: Int, email: String) {
        answerQuizBtn.setOnClickListener {
            Log.e("OMG", "Button clicked!")
            Log.d("OMG", "COURSE: $courseId, MODULE: $moduleId, EMAIL: $email")

            firebaseService.getUserProgressModule(email, courseId) { userProgress, error ->
                Log.d("OMG", "getUserProgressModule callback called with userProgress: $userProgress, error: $error")
                if (userProgress?.completedModules?.contains(moduleId) == true) {
                    Log.d("OMG", "Module $moduleId is already completed.")
                    showRetakeQuizDialog(courseId, moduleId)
                } else {
                    Log.d("OMG", "Module $moduleId is not completed, starting quiz.")
                    startQuizActivity(courseId, moduleId)
                }
            }
        }
    }


    private fun showRetakeQuizDialog(courseId: Int, moduleId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Retake Quiz")
        builder.setMessage("You have already completed this quiz. Do you want to retake it?")
        builder.setPositiveButton("Yes") { _, _ ->
            startQuizActivity(courseId, moduleId)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun startQuizActivity(courseId: Int, moduleId: Int) {
        val intent = Intent(this@ModuleDetailActivity, QuizActivity::class.java)
        intent.putExtra("module_id", moduleId)
        intent.putExtra("course_id", courseId)
        startActivity(intent)
    }

    private fun initData(courseId: Int, moduleId: Int) {
        Log.d("InitData", "Initializing data for course: $courseId and module: $moduleId")
        loadingAnimation.visibility = View.VISIBLE
        firebaseService.getCourseById(courseId.toString()) { course, _ ->
            if (course != null) {
                val module = course.modules.find { it.id == moduleId }
                Log.d("ModuleDetail", "MODULE: $module")

                if (module != null) {
                    loadingAnimation.visibility = View.GONE
                    moduleName.text = module.title
                    moduleDescription.text = module.description

                    moduleWebView.loadData(module.videoUrl, "text/html", "utf-8")
                    moduleWebView.settings.javaScriptEnabled = true

                    val concepts = module.concepts
                    conceptAdapter.setConcepts(concepts)

                    val questions = module.quiz
                    Log.d("Questions", "QUESTIONS: $questions")

                    answerQuizBtn.setOnClickListener {
                        val intent = Intent(this@ModuleDetailActivity, QuizActivity::class.java)
                        intent.putParcelableArrayListExtra("list", ArrayList(questions))
                        intent.putExtra("module_id", moduleId)
                        intent.putExtra("course_id", courseId)
                        startActivity(intent)
                    }
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
}
