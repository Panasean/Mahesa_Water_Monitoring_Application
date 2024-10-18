package com.example.mahesa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    private EditText Email;
    private EditText Password;
    private TextView textViewRegister;
    private Button btnLogin;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_profile");

        btnLogin = findViewById(R.id.btnLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_Email = Email.getText().toString();
                final String txt_Password = Password.getText().toString();

                if (TextUtils.isEmpty(txt_Email) || TextUtils.isEmpty(txt_Password)){
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    // Buat query untuk mencari data pengguna berdasarkan email
                    Query query = databaseReference.orderByChild("user_gmail").equalTo(txt_Email);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Data dengan email yang sesuai ditemukan
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String storedPassword = snapshot.child("user_password").getValue(String.class);

                                    // Periksa apakah password cocok
                                    if (txt_Password.equals(storedPassword)) {
                                        // Password cocok, pengguna berhasil login
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                }
                                // Password tidak cocok
                                Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                            } else {
                                // Tidak ada data dengan email yang sesuai
                                Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Penanganan kesalahan jika ada
                            Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
