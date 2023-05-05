package com.example.eventlistenerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.eventlistenerkotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        //constant variables declared using val (its value is not supposed to change) like final in java
        val view = binding.root
        setContentView(view)
        binding.reverseButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Local variables are typically declared and initialized at the same time, in which case the type of the
                ///variable builder here is inferred to be the type of the expression it is initialized with
                var builder = StringBuilder(binding.editMessage.getText()).reverse().toString()
                binding.editMessage.setText(builder)
            }
        })
        binding.clearButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                binding.editMessage.setText("")
            }
        })
    }

}