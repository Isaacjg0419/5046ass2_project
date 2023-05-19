package com.example.a5046ass2_project.authentication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.Date

class SignUpFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var binding: FragmentSignUpBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private var gender: String = ""

    //    private var dateOfBirth: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.genderSpinner?.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.genderSpinner?.adapter = adapter
        }

        binding?.signUpButton?.setOnClickListener(View.OnClickListener {
            if (binding?.editTextTextEmailAddress?.text.toString().isEmpty()) {
                toastMessage("Username should not be none")
            } else if (binding?.editTextTextPassword?.text.toString().isEmpty()) {
                toastMessage("Password should not be none")
            } else if (binding?.editTextTextPasswordConfirm?.text.toString().isEmpty()) {
                toastMessage("Confirm Password should not be none")
            } else if (binding?.editTextTextPasswordConfirm?.text.toString() != binding?.editTextTextPassword?.text.toString()) {
                toastMessage("Confirm password should be the same as the password")
            } else if (!isValidDate()) {
                toastMessage("Date of birth should be between 1900 and 2022")
            } else {
                singUp(
                    binding?.editTextTextEmailAddress?.text.toString(),
                    binding?.editTextTextPassword?.text.toString()
                )
            }
        })
    }

    private fun singUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    toastMessage("Sign up success!")
                    // save profile to room
                    authenticationViewModel.saveProfile(
                        binding?.editTextTextEmailAddress?.text.toString(),
                        gender,
                        handleDate()
                    )
                    // save to firebase db
                    authenticationViewModel.uploadToFireStore()
                    startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    toastMessage("Something wrong with signUp, Please try again.")
                }
            }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        gender = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //none
    }

    private fun handleDate(): Date {
//        dateOfBirth =
//            "${binding?.dateOfBirth?.dayOfMonth!!}" +
//                    "${binding?.dateOfBirth?.month!! + 1}" +
//                    "${binding?.dateOfBirth?.year!!}"
        val calendar = Calendar.getInstance()
        calendar.set(
            binding?.dateOfBirth?.year!!, binding?.dateOfBirth?.month!!,
            binding?.dateOfBirth?.dayOfMonth!!
        )

//        Log.d(ContentValues.TAG, "DateOfBirth: $dateOfBirth")
        Log.d(ContentValues.TAG, "DateOfBirth: ${Date(calendar.timeInMillis)}")
        return Date(calendar.timeInMillis)
    }

    private fun isValidDate(): Boolean {
        return binding?.dateOfBirth?.year!! in 1901..2022
    }

}