package com.example.a5046ass2_project.profile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.databinding.FragmentProfileEditBinding
import java.util.Calendar
import java.util.Date

class ProfileFragmentEdit : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory()
        )[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = ViewModelProvider(this)[profileViewModel::class.java]
        initUI()
    }

    private fun initUI() {
        binding.genderSpinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.genderSpinner.adapter = adapter
        }

        binding.emailValueTextView.text = CurrentUser.profile?.email
        CurrentUser.profile?.dateOfBirth?.let {
            val calendar:Calendar = Calendar.getInstance()
            calendar.time = it
            binding.dateOfBirth.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        }
        binding.confirmEditButton.setOnClickListener {
            if (!isValidDate()) {
                toastMessage("Date of birth should be between 1900 and 2022")
            } else {
                handleDate()
                profileViewModel.updateToRoom()
                profileViewModel.uploadToFireStore()
                startProfileShowFragment()
            }
        }
    }

    private fun startProfileShowFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.profileFragmentContainer, ProfileFragmentShow())
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        CurrentUser.profile?.gender = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // none
    }

    private fun handleDate() {

        val calendar = Calendar.getInstance()
        calendar.set(
            binding.dateOfBirth.year, binding.dateOfBirth.month,
            binding.dateOfBirth.dayOfMonth
        )
        Log.d(ContentValues.TAG, "DateOfBirth: ${Date(calendar.timeInMillis)}")
        CurrentUser.profile?.dateOfBirth = Date(calendar.timeInMillis)
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidDate(): Boolean {
        return binding.dateOfBirth.year in 1901..2022
    }
}