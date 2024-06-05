package com.devan.lab.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.devan.lab.Models.User
import com.devan.lab.R
import com.devan.lab.Utils.RoundedCornersTransformation
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.service.FirebaseService
import com.example.validator.validateInputs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class SignupActivity : AppCompatActivity() {

    private lateinit var signUpButton: AppCompatButton
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rePasswordEditText: EditText
    private lateinit var userPic: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var selectedImageUri: Uri
    private lateinit var frameLayout: FrameLayout
    private lateinit var loadingAnimation: RelativeLayout


    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
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
        profileImageView = findViewById(R.id.profileImageView)
        userPic = findViewById(R.id.userPic)
        frameLayout = findViewById(R.id.frameLayout)
        loadingAnimation = findViewById(R.id.loading_animation)

        loadingAnimation.visibility = View.GONE
    }

    private fun setupListeners() {
        profileImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

        signUpButton.setOnClickListener {
            performClickAnimation(signUpButton)
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val rePassword = rePasswordEditText.text.toString()
            val provider = ProviderType.BASIC.toString()

            if (!this::selectedImageUri.isInitialized) {
                ToastManager.showToast("Please select a profile picture.", this, ToastType.INFO)
                return@setOnClickListener
            }

            val (isValid, message) = validateInputs(name, email, password, rePassword)
            if (!isValid) {
                ToastManager.showToast(message, this, ToastType.INFO)
                return@setOnClickListener
            }

            loadingAnimation.visibility = View.VISIBLE

            firebaseService.uploadProfileImage(selectedImageUri) { imageUrl ->
                val user = User(name, email, provider, imageUrl)
                firebaseService.registerUser(user, password) { success, _ ->
                    if (success) {
                        loadingAnimation.visibility = View.GONE
                        showHome(user.email, ProviderType.BASIC)
                    } else {
                        ToastManager.showToast("We could not process your data.", this, ToastType.INFO)
                    }
                }
            }
        }
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider)
        }
        ToastManager.showToast("Registration Successfully", this, ToastType.SUCCESS)
        startActivity(homeIntent)
    }

    private fun loadImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .transform(RoundedCornersTransformation(this, 22f))
            .into(profileImageView)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            userPic.text = "Looks great!"
            selectedImageUri = data?.data ?: return@registerForActivityResult
            loadImage(selectedImageUri)
        }
    }
}
