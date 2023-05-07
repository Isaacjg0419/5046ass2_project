package com.example.a5046ass2_project.Room

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a5046ass2_project.Room.entity.Customer
import com.example.a5046ass2_project.Room.viewmodel.CustomerViewModel
import com.example.a5046ass2_project.databinding.ActivityRoomBinding

class Room : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.idTextField.setPlaceholderText("This is only used for Edit")
        val customerViewModel: CustomerViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
                CustomerViewModel::class.java
            )
        customerViewModel.allCustomers.observe(this, Observer{ customers ->
            var allCustomers = ""
            for (temp in customers) {
                val customerDetails: String =
                    temp.uid.toString() + " " + temp.firstName + " " +
                            temp.lastName + " " + temp.salary
                allCustomers =
                    allCustomers + System.getProperty("line.separator") +
                            customerDetails
            }
            binding.textViewRead.setText("All data: $allCustomers")
        })
        binding.addButton.setOnClickListener {
            val name = binding.nameTextField.editText!!.text.toString()
            val surname = binding.surnameTextField.editText!!.text.toString()
            val strSalary = binding.salaryTextField.editText!!.text.toString()
            if (!name.isEmpty() && !surname.isEmpty() && !strSalary.isEmpty()) {
                val salary = strSalary.toDouble()
                val customer = Customer(name, surname, salary)
                customerViewModel.insert(customer)
                binding.textViewAdd.text = "Added Record: $name $surname $salary"
            }
        }

        binding.deleteButton.setOnClickListener {
            customerViewModel.deleteAll()
            binding.textViewDelete.setText("All data was deleted")
        }
        binding.clearButton.setOnClickListener {
            binding.nameTextField.editText!!.setText("")
            binding.surnameTextField.editText!!.setText("")
            binding.salaryTextField.editText!!.setText("")
        }
        binding.updateButton.setOnClickListener {
            val strId = binding.idTextField.editText!!.text.toString()
            var id = 0
            if (!strId.isEmpty()) id = strId.toInt()
            val name = binding.nameTextField.editText!!.text.toString()
            val surname = binding.surnameTextField.editText!!.text.toString()
            val strSalary = binding.salaryTextField.editText!!.text.toString()
            if (!name.isEmpty() && !surname.isEmpty() && !strSalary.isEmpty()) {
                val doubleSalary = strSalary.toDouble()
                customerViewModel.findCustomerById(id).observe(this, Observer { result ->
                    var customer = result
                    if (customer != null) {
                        customer.firstName = name
                        customer.lastName = surname
                        customer.salary = doubleSalary
                        customerViewModel.update(customer)
                        binding.textViewUpdate.text =
                            "Update was successful for ID: " + customer.uid
                    } else {
                        binding.textViewUpdate.text = "Id does not exist"
                    }
                })
            }
        }
    }

}