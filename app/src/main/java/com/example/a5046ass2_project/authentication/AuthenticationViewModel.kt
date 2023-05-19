package com.example.a5046ass2_project.authentication

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.a5046ass2_project.map.PropertyDatabase
import com.example.a5046ass2_project.profile.CurrentUser
import com.example.a5046ass2_project.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {
    private var auth: FirebaseAuth = Firebase.auth

    // After sign up success, upload the profile into firebase database
    fun uploadToFireStore() {
        val db = Firebase.firestore
        CurrentUser.profile?.email?.let {
            db.collection("profiles").document(it).set(CurrentUser.profile!!)
                .addOnSuccessListener {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: ${CurrentUser.profile?.email}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }

    }

    fun saveProfile(email: String, gender: String, dateOfBirth: Date) {
        CurrentUser.profile = UserProfile(email, gender, dateOfBirth)
        CoroutineScope(Dispatchers.IO).launch {
            saveToRoom()
        }
    }

    private suspend fun saveToRoom() {
        CurrentUser.profile?.let {
            PropertyDatabase.getInstance(getApplication()).profileDAO().insert(
                it
            )
        }
    }
}