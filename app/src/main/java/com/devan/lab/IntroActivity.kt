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

class IntroActivity : ComponentActivity() {

    private lateinit var introLayout: ConstraintLayout

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
//        Option to save the data to Firebase
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
