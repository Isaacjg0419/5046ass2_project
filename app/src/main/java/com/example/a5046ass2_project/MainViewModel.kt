package com.example.a5046ass2_project

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.a5046ass2_project.map.PropertyDatabase
import com.example.a5046ass2_project.profile.CurrentUser
import com.example.a5046ass2_project.profile.UserProfile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun getProfileFromRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                var tempUser = CurrentUser.profile?.email?.let {
                    PropertyDatabase
                        .getInstance(getApplication())
                        .profileDAO()
                        .getProfileByEmail(it)
                }
                if (tempUser != null) {
                    CurrentUser.profile = tempUser
                }
                Log.d("MainActivity: Current user", CurrentUser.profile?.gender.toString())
            } catch (e: Exception) {
                Log.e("MainActivity", "GetProfileFromRoom error")
            }
            try {
                updateProfileFromFireStore()
                Log.d("MainActivity: Current user from fireStore", CurrentUser.profile?.gender.toString())
            } catch (e: Exception) {
                Log.e("MainActivity", "GetProfileFromFireStore error")
            }
        }
    }

    private fun updateProfileFromFireStore() {
        val db = Firebase.firestore
        val docRef = CurrentUser.profile?.email?.let {
            db.collection("profiles").document(it)
        }
        docRef?.get()?.addOnSuccessListener { documentSnapshot ->
            CurrentUser.profile = documentSnapshot.toObject<UserProfile>()
        }
    }
}