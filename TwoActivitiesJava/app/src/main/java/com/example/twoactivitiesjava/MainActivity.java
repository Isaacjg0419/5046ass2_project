package com.example.twoactivitiesjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.twoactivitiesjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



    binding.startButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,
                    SecondActivity.class);
            startActivity(intent);
        } });
    }
}

