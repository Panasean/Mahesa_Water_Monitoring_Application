package com.example.mahesa;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    private Button btnRegister;
    private TextView textViewLogin;

    private EditText Name;
    private EditText Email;
    private EditText Password1;
    private EditText Password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user_profile");

        btnRegister = findViewById(R.id.btnRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password1 = findViewById(R.id.Password1);
        Password2 = findViewById(R.id.Password2);


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values from the EditText fields
                String userName = Name.getText().toString().trim();
                String userGmail = Email.getText().toString().trim();
                String userPassword1 = Password1.getText().toString().trim();
                String userPassword2 = Password2.getText().toString().trim();

                // Check if any of the fields are empty
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userGmail) || TextUtils.isEmpty(userPassword1)
                        || TextUtils.isEmpty(userPassword2)) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!userGmail.contains("@")) {
                    // Check if email contains "@"
                    Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (!userPassword1.equals(userPassword2)) {
                    // Check if Password 1 and Password 2 match
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a HashMap to store user data
                    HashMap<String, String> user_profile = new HashMap<>();
                    user_profile.put("user_name", userName);
                    user_profile.put("user_gmail", userGmail);
                    user_profile.put("user_password", userPassword1); // Save only one password

                    // Push the user data to Firebase Realtime Database under a unique ID
                    databaseReference.push().setValue(user_profile);

                    // Show a toast message to indicate successful data submission
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
