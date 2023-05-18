package com.example.a5046ass2_project.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a5046ass2_project.MainActivity
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.databinding.FragmentProfileShowBinding
import java.text.SimpleDateFormat
import java.util.Date

class ProfileFragmentShow : Fragment() {

    private lateinit var binding: FragmentProfileShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileShowBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.emailValueTextView.text = CurrentUser.profile?.email
        CurrentUser.profile?.gender.also { binding.genderTextView.text = it }
        binding.dateOfBirthTextView.text = CurrentUser.profile?.dateOfBirth?.let { formatDate(it) }
        binding.editButton.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileFragmentContainer, ProfileFragmentEdit())
                .disallowAddToBackStack()
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
