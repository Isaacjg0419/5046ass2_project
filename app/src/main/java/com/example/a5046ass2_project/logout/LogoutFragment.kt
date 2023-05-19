package com.example.a5046ass2_project.logout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.authentication.AuthenticationActivity
import com.example.a5046ass2_project.profile.CurrentUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogoutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.auth.signOut()
        CurrentUser.profile = null
        startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
        onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }


}