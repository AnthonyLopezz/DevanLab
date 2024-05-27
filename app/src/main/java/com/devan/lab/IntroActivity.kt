package com.devan.lab

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import com.devan.lab.Activity.HomeActivity
import com.devan.lab.Activity.LoginActivity
import com.devan.lab.Models.Course
import com.devan.lab.Models.Module

class IntroActivity : ComponentActivity() {

    private lateinit var introLayout: ConstraintLayout

    val courses = listOf(
        Course(
            id = 1,
            title = "Introduction to Programming with Python",
            icon = "ic_python",
            category = "Developing",
            modules = listOf(
                Module(
                    id = 1,
                    title = "Getting Started with Python",
                    videoUrl = "https://www.youtube.com/watch?v=_uQrJ0TkZlc",
                    description = "Learn the basics of Python programming, including syntax, variables, and data types."
                ),
                Module(
                    id = 2,
                    title = "Control Flow in Python",
                    videoUrl = "https://www.youtube.com/watch?v=wgk5n9p4FxA",
                    description = "Understand control flow statements such as if-else, for loops, and while loops in Python."
                ),
                Module(
                    id = 3,
                    title = "Functions and Modules",
                    videoUrl = "https://www.youtube.com/watch?v=2wCsSCMvg-w",
                    description = "Learn how to write reusable code using functions and modules in Python."
                )
            )
        ),
        Course(
            id = 2,
            title = "Introduction to Java Programming",
            icon = "ic_java",
            category = "Developing",
            modules = listOf(
                Module(
                    id = 1,
                    title = "Getting Started with Java",
                    videoUrl = "https://www.youtube.com/watch?v=grEKMHGYyns",
                    description = "Learn the basics of Java programming, including setup, syntax, and basic concepts."
                ),
                Module(
                    id = 2,
                    title = "Java Control Flow",
                    videoUrl = "https://www.youtube.com/watch?v=GYI3FP8uJZY",
                    description = "Understand control flow statements such as if-else, for loops, and while loops in Java."
                ),
                Module(
                    id = 3,
                    title = "Object-Oriented Programming in Java",
                    videoUrl = "https://www.youtube.com/watch?v=xlAH4dbMVnU",
                    description = "Learn the fundamentals of object-oriented programming (OOP) in Java."
                )
            )
        ),
        Course(
            id = 3,
            title = "Introduction to UI Design",
            icon = "ic_ui_design",
            category = "Designing",
            modules = listOf(
                Module(
                    id = 1,
                    title = "Basics of UI Design",
                    videoUrl = "https://www.youtube.com/watch?v=HcOc7P5BMi4",
                    description = "Learn the basics of UI design, including principles and tools."
                ),
                Module(
                    id = 2,
                    title = "Designing with Figma",
                    videoUrl = "https://www.youtube.com/watch?v=U5JYk3dQAE8",
                    description = "Understand how to use Figma for UI design projects."
                ),
                Module(
                    id = 3,
                    title = "Advanced UI Design Techniques",
                    videoUrl = "https://www.youtube.com/watch?v=6cL3FN7nC2Y",
                    description = "Explore advanced techniques in UI design."
                )
            )
        ),
        Course(
            id = 4,
            title = "Machine Learning Basics",
            icon = "ic_ml",
            category = "IA and ML",
            modules = listOf(
                Module(
                    id = 1,
                    title = "Introduction to Machine Learning",
                    videoUrl = "https://www.youtube.com/watch?v=Gv9_4yMHFhI",
                    description = "Learn the fundamentals of machine learning."
                ),
                Module(
                    id = 2,
                    title = "Supervised Learning",
                    videoUrl = "https://www.youtube.com/watch?v=EH2RZ1vJNxY",
                    description = "Understand supervised learning algorithms and techniques."
                ),
                Module(
                    id = 3,
                    title = "Unsupervised Learning",
                    videoUrl = "https://www.youtube.com/watch?v=tiN2SP3ekWw",
                    description = "Explore unsupervised learning methods."
                )
            )
        )
    )


    private lateinit var getStartedButton: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(200) //Hack
        setTheme(R.style.Theme_Lab)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        enableEdgeToEdge()

        initComponents()

        setup()

        session()

    }

    override fun onStart() {
        super.onStart()
        introLayout.visibility = View.VISIBLE
    }




    private fun session() {
        val prefs: SharedPreferences =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        val email: String? = prefs.getString("email", null)
        val provider: String? = prefs.getString("provider", null)
        Log.d("Session fun", "Email: $email, Provider: $provider")

        if (email != null) {

            introLayout.visibility = View.INVISIBLE

            showHome(email)
        }

    }

    private fun setup() {
        getStartedButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

//        saveCourses(courses)

    }

    private fun showHome(email: String) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homeIntent)
    }

    private fun initComponents() {
        introLayout = findViewById(R.id.introLayout)
        getStartedButton = findViewById(R.id.getStartedButton)
    }

}
