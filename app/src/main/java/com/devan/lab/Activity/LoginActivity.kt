package com.devan.lab.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.devan.lab.Models.User
import com.devan.lab.R
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.service.FirebaseService
import com.example.validator.validateEmail
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class LoginActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 3108

    private lateinit var signUpButton: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: AppCompatButton
    private lateinit var googleButton: LinearLayout
    private lateinit var facebookButton: LinearLayout
    private lateinit var loadingAnimation: RelativeLayout

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val firebaseService by lazy {
        FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)

        initComponents()

        setup()
    }


    private fun initComponents() {
        signUpButton = findViewById(R.id.signUpButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        googleButton = findViewById(R.id.googleButton)
        facebookButton = findViewById(R.id.facebookButton)
        loadingAnimation = findViewById(R.id.loading_animation)
        auth = FirebaseAuth.getInstance()

        loadingAnimation.visibility = View.GONE
    }


    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider)
        }
        startActivity(homeIntent)
    }

    private fun setup() {
        title = "Authentication"

        signUpButton.setOnClickListener {
            performClickAnimation(signUpButton)
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


        signInButton.setOnClickListener {
            performClickAnimation(signInButton)

            val email = emailEditText.text.toString()
            val pass = passwordEditText.text.isNotEmpty()



            val (isValid, message) = validateEmail(email)
            if (!isValid) {
                emailEditText.setBackgroundColor(R.drawable.fail_edittext_background)
                ToastManager.showToast(message, this, ToastType.INFO)
                return@setOnClickListener
            }

            if (!pass){
                passwordEditText.setBackgroundColor(R.drawable.fail_edittext_background)

                ToastManager.showToast("Empty Password!", this, ToastType.INFO)
                return@setOnClickListener
            }

            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                loadingAnimation.visibility = View.VISIBLE

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        loadingAnimation.visibility = View.GONE

                    } else {
                        ToastManager.showToast(
                            "Invalid Credentials.",
                            this,
                            ToastType.INFO
                        )

                    }
                }
            }
        }


        googleButton.setOnClickListener {
            performClickAnimation(googleButton)
            signInWithGoogle()
        }

        facebookButton.setOnClickListener {
            performClickAnimation(facebookButton)
        }

    }

    private fun signInWithGoogle() {

        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        val googleClient: GoogleSignInClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)

                val credential: AuthCredential =
                    GoogleAuthProvider.getCredential(account.idToken, null)

                FirebaseAuth.getInstance()
                    .signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(account.email ?: "", ProviderType.GOOGLE)
                            val welcome = "Welcome!"
                            ToastManager.showToast(welcome, this, ToastType.INFO)

                        } else {
                            ToastManager.showToast(
                                "Could not complete the Authentication.",
                                this,
                                ToastType.ERROR
                            )

                        }
                    }
            } catch (e: ApiException) {

                ToastManager.showToast(
                    "Could not complete the Authentication.",
                    this,
                    ToastType.ERROR
                )
            }


        }
    }

}