package com.example.a5046ass2_project.profile

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.a5046ass2_project.map.PropertyDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    fun updateToRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            CurrentUser.profile?.let {
                PropertyDatabase.getInstance(getApplication()).profileDAO().update(
                    it
                )
            }
        }
    }

    fun uploadToFireStore() {
        val db = Firebase.firestore
        // Create a new user with a first and last name
//
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
}