package com.devan.lab.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.devan.lab.R
import com.devan.lab.Utils.RoundedCornersTransformation
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

enum class ProviderType {
    BASIC,
    GOOGLE
}


class HomeActivity : ComponentActivity() {

    private lateinit var nameViewText: TextView
    private lateinit var seeAllButton: TextView
    private lateinit var signOutButton: ConstraintLayout
    private lateinit var developingButton: ConstraintLayout
    private lateinit var designingButton: ConstraintLayout
    private lateinit var iaMlButton: ConstraintLayout
    private lateinit var exploreButton: ConstraintLayout

    // Course
    private lateinit var noCourses: TextView
    private lateinit var yourCourseCard: ConstraintLayout
    private lateinit var yourCourseIcon: ImageView
    private lateinit var profileImageView: ImageView
    private lateinit var yourCourseTitle: TextView
    private lateinit var progressText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var percentText: TextView

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // setup
        initComponents()

        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        Log.d("SignupActivity", "Provider: $provider , Email: $email")
        initData(email ?: "")
        setup()

        val prefs: SharedPreferences.Editor? =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email", email)
        prefs?.putString("provider", provider)
        prefs?.apply()

        setupCategoryButtons()

        val welcome = "Welcome!"
        ToastManager.showToast(welcome, this, ToastType.SUCCESS)

        if (email != null) {
            fetchFirstCompletedCourse(email)
            getAllCourses(email)
        }
    }

    private fun getAllCourses(email: String) {
        firebaseService.getAllUserProgress(email) { userProgressMap, error ->
            Log.d("CourseProgress", "Course: $userProgressMap")

            if (error != null) {
            } else {
                for ((course, progress) in userProgressMap) {
                    Log.d("CourseProgress", "Course: ${course.title}, Progress: $progress")
                }
            }
        }
    }

    private fun initComponents() {
        nameViewText = findViewById(R.id.nameViewText)
        seeAllButton = findViewById(R.id.seeAllButton)
        signOutButton = findViewById(R.id.signOutButton)

        signOutButton = findViewById(R.id.signOutButton)
        developingButton = findViewById(R.id.developingButton)
        designingButton = findViewById(R.id.designingButton)
        iaMlButton = findViewById(R.id.iaMlButton)
        exploreButton = findViewById(R.id.exploreButton)

        noCourses = findViewById(R.id.noCourses)
        yourCourseCard = findViewById(R.id.yourCourseCard)
        yourCourseIcon = findViewById(R.id.yourCourseIcon)
        profileImageView = findViewById(R.id.profileImageView)
        yourCourseTitle = findViewById(R.id.yourCourseTitle)
        progressText = findViewById(R.id.progressTxt)
        progressBar = findViewById(R.id.progressBar)
        percentText = findViewById(R.id.percentTxt)

        // Inicialmente ocultamos la tarjeta de curso
        yourCourseCard.visibility = View.GONE
        noCourses.visibility = View.GONE
    }

    private fun setupCategoryButtons() {
        developingButton.setOnClickListener {
            performClickAnimation(developingButton)
            openCourseListActivity("Developing")
        }
        designingButton.setOnClickListener {
            performClickAnimation(designingButton)
            openCourseListActivity("Designing")
        }
        iaMlButton.setOnClickListener {
            performClickAnimation(iaMlButton)
            openCourseListActivity("IA and ML")
        }
        exploreButton.setOnClickListener {
            performClickAnimation(exploreButton)
            openCourseListActivity("All")
        }

        seeAllButton.setOnClickListener {
            performClickAnimation(seeAllButton)
            openCourseListActivity("Your")
        }

    }

    override fun onStart() {
        val prefs: SharedPreferences =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        val email: String? = prefs.getString("email", null)
        super.onStart()
        if (email != null) {
            fetchFirstCompletedCourse(email)
        }
    }

    private fun fetchFirstCompletedCourse(email: String) {
        firebaseService.getFirstCompletedCourse(email) { course, progress, _ ->
            Log.d("HomeActivity", "Progress: $progress")

            if (course != null && progress != null) {
                yourCourseTitle.text = course.title

                val iconResId = resources.getIdentifier(course.icon, "drawable", packageName)
                if (iconResId != 0) {
                    yourCourseIcon.setImageResource(iconResId)
                } else {
                    yourCourseIcon.setImageResource(R.drawable.placeholder_icon)
                }

                progressBar.progress = progress.percentage.toInt()
                percentText.text = "${progress.percentage.toInt()}%"
                yourCourseCard.visibility = View.VISIBLE
                noCourses.visibility = View.GONE
            } else {
                noCourses.text = "You have no registered courses"
                yourCourseCard.visibility = View.GONE
                noCourses.visibility = View.VISIBLE

            }
        }
    }

    private fun openCourseListActivity(category: String) {
        val intent = Intent(this, CourseListActivity::class.java)
        intent.putExtra("category", category)
        intent.putExtra("title_name", "$category Courses")
        startActivity(intent)
    }

    private fun initData(email: String) {
        firebaseService.getUserData(email) { user, _ ->
            if (user != null) {
                nameViewText.text = user.name
                Glide.with(this)
                    .load(user.profileImageUrl)
                    .transform(RoundedCornersTransformation(this, 25f))
                    .into(profileImageView)
            } else {
                ToastManager.showToast("Could not complete the operation.", this, ToastType.ERROR)

            }
        }
    }

    private fun setup() {
        title = "Home"

        signOutButton.setOnClickListener {
            performClickAnimation(signOutButton)

            ToastManager.showToast("See you later!", this, ToastType.INFO)
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            val prefs: SharedPreferences.Editor? =
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()

            prefs?.clear()
            prefs?.apply()
            startActivity(intent)
        }
    }

}
