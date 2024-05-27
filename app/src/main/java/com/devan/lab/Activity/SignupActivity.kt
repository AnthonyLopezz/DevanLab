package com.devan.lab.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.devan.lab.Models.User
import com.devan.lab.R
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.service.FirebaseService
import com.example.validator.validateEmail
import com.example.validator.validateInputs
import com.example.validator.validatePasswords
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignupActivity : AppCompatActivity() {

    private lateinit var signUpButton: AppCompatButton
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rePasswordEditText: EditText

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        initComponents()
        setupListeners()
    }

    private fun initComponents() {
        nameEditText = findViewById(R.id.nameEditText)
        signUpButton = findViewById(R.id.signupButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        rePasswordEditText = findViewById(R.id.rePasswordEditText)
    }

    private fun setupListeners() {
        signUpButton.setOnClickListener {
            performClickAnimation(signUpButton)
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val rePassword = rePasswordEditText.text.toString()
            val provider = ProviderType.BASIC.toString()

            val (isValid, message) = validateInputs(name, email, password, rePassword)
            if (!isValid) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(name, email, provider)
            firebaseService.registerUser(user, password) { success, error ->
                if (success) {
                    showHome(user.email, ProviderType.BASIC)
                } else {
                    showAlert(error ?: "An error occurred with user registration.")
                }
            }
        }
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider)
        }
        startActivity(homeIntent)
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}