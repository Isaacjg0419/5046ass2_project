package com.example.eventlistenerjavaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eventlistenerjavaapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Get a reference to the root view
        View view = binding.getRoot();
        // The root view needs to be passed as an input to setContentView()
        setContentView(view);
        // setContentView(R.layout.activity_main);
        //Button reverseButton = findViewById(R.id.reverse_button);
        binding.reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editMessage);
                String builder = new StringBuilder(editText.getText()).reverse().toString();
                editText.setText(builder);
            }
        });
        //Button clearButton= findViewById(R.id.clear_button);
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText editText =findViewById(R.id.editMessage);
                binding.editMessage.setText("");
            }
        });
    }


}