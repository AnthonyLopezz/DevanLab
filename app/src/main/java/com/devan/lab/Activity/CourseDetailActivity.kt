package com.devan.lab.Activity

import ModuleAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Adapter.ArchiveAdapter
import com.devan.lab.ViewModel.ArchiveViewModel
import com.devan.lab.databinding.ActivityCourseDetailBinding
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.devan.lab.R
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.google.firebase.storage.FirebaseStorage


class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailBinding
    private val archiveViewModel: ArchiveViewModel by viewModels()

    private lateinit var courseName: TextView
    private lateinit var courseCategory: TextView
    private lateinit var courseIcon: ImageView
    private lateinit var courseBg: ConstraintLayout
    private lateinit var moduleAdapter: ModuleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingAnimation: RelativeLayout


//    private var vibrantColor: Int = 0 // Variable to hold the vibrant color testing... (not implement yet)

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val archiveAdapter by lazy { ArchiveAdapter(archiveViewModel.loadDataArchive()) }
            viewArchive.apply {
                adapter = archiveAdapter
                layoutManager = LinearLayoutManager(
                    this@CourseDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
        initComponents()

        recyclerView.layoutManager = LinearLayoutManager(this)
        moduleAdapter = ModuleAdapter()
        recyclerView.adapter = moduleAdapter

        val id = intent.getIntExtra("course_id", -1)
        Log.d("CourseDetail", "Course ID $id")

        initData(id.toString())

        val prefs: SharedPreferences.Editor? =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putInt("course_id", id)
        prefs?.apply()
    }

    private fun initComponents(){
        courseName = findViewById(R.id.courseName)
        courseCategory = findViewById(R.id.courseCategory)
        courseIcon = findViewById(R.id.courseIcon)
        recyclerView = findViewById(R.id.viewModules)
        courseBg = findViewById(R.id.courseBg)

        loadingAnimation = findViewById(R.id.loading_animation)

        loadingAnimation.visibility = View.GONE
    }

    private fun initData(id: String) {
        loadingAnimation.visibility = View.VISIBLE
        firebaseService.getCourseById(id) { course, _ ->
            if (course != null) {
                courseName.text = course.title
                courseCategory.text = course.category

                moduleAdapter.setModules(course.modules)

                val iconResId = resources.getIdentifier(course.icon, "drawable", packageName)
                loadingAnimation.visibility = View.GONE
                if (iconResId != 0) {

                    courseIcon.setImageResource(iconResId)
                } else {
                    courseIcon.setImageResource(R.drawable.placeholder_icon)
                }
            } else {
                ToastManager.showToast("Could not complete the operation.", this, ToastType.ERROR)
                loadingAnimation.visibility = View.GONE
            }
        }
    }
}
