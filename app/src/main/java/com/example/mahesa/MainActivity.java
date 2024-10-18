package com.example.mahesa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    private ImageView imageViewExit;
    private TextView TextViewNilaiPH;
    private TextView TextViewNilaiSuhu;
    private TextView TextViewNilaiNTU;
    private TextView TextViewNilaiFuzzy;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("data_pembacaan");

        // Initialize the views
        imageViewExit = findViewById(R.id.imageViewExit);
        TextViewNilaiPH = findViewById(R.id.TextViewNilaiPH);
        TextViewNilaiSuhu = findViewById(R.id.TextViewNilaiSuhu);
        TextViewNilaiNTU = findViewById(R.id.TextViewNilaiNTU);
        TextViewNilaiFuzzy = findViewById(R.id.TextViewNilaiFuzzy);

        // Set onClickListener for the exit button
        imageViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // Fetch and display PH value
        databaseReference.child("PH").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nilaiPH = dataSnapshot.getValue(String.class);
                    TextViewNilaiPH.setText(nilaiPH);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Toast.makeText(MainActivity.this, "Failed to load PH value.", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch and display Suhu value
        databaseReference.child("Suhu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nilaiSuhu = dataSnapshot.getValue(String.class);
                    TextViewNilaiSuhu.setText(nilaiSuhu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Toast.makeText(MainActivity.this, "Failed to load Suhu value.", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch and display NTU value
        databaseReference.child("NTU").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nilaiNTU = dataSnapshot.getValue(String.class);
                    TextViewNilaiNTU.setText(nilaiNTU);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Toast.makeText(MainActivity.this, "Failed to load NTU value.", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch and display Fuzzy value
        databaseReference.child("Fuzzy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nilaiFuzzy = dataSnapshot.getValue(String.class);
                    TextViewNilaiFuzzy.setText(nilaiFuzzy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Toast.makeText(MainActivity.this, "Failed to load Fuzzy value.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
