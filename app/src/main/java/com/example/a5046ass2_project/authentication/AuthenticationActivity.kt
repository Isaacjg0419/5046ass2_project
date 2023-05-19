package com.example.a5046ass2_project.authentication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a5046ass2_project.MainActivity
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.databinding.ActivityAuthenticationBinding
import com.example.a5046ass2_project.profile.CurrentUser
import com.example.a5046ass2_project.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationActivity : AppCompatActivity() {
    private var binding: ActivityAuthenticationBinding? = null

    private lateinit var auth: FirebaseAuth

    // ...
    // Initialize Firebase Auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)

        auth = Firebase.auth
        binding?.signInButton?.setOnClickListener(View.OnClickListener {
            // add customise validation logic
            if (binding?.editTextTextEmailAddress?.text.toString().isEmpty()) {
                toastMessage("Username should not be none")
            } else if (binding?.editTextTextPassword?.text.toString().isEmpty()) {
                toastMessage("Password should not be none")
            } else {
                signIn(
                    binding?.editTextTextEmailAddress?.text.toString(),
                    binding?.editTextTextPassword?.text.toString()
                )

            }

        })

        binding?.signUpButton?.setOnClickListener(View.OnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerView, SignUpFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()

        })
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    CurrentUser.profile = UserProfile(email)
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }
}