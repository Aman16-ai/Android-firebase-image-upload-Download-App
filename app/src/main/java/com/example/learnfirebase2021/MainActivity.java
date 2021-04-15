package com.example.learnfirebase2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText email_et;
    EditText pass_et;
    Button btn;
    Button btn_signin;
    private FirebaseAuth mAuth;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            startActivity(new Intent(MainActivity.this,UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            String name = mAuth.getUid();
        }

      
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_et = findViewById(R.id.et_email);
        pass_et = findViewById(R.id.et_pass);
        btn = findViewById(R.id.btn);
        btn_signin = findViewById(R.id.btnsignin);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = email_et.getText().toString();
                String pass_txt = pass_et.getText().toString();
                if(email_txt == " " && pass_txt == " ") {
                    Toast.makeText(MainActivity.this, "Please enter the email and password for login", Toast.LENGTH_SHORT).show();
                    
                }
                else if(pass_txt.length() < 4) {
                    Toast.makeText(MainActivity.this, "Length of password must be greater than 4", Toast.LENGTH_SHORT).show();
                    
                }
                else {
                    UserAuthentication(email_txt,pass_txt);
                }
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = email_et.getText().toString();
                String pass_txt = pass_et.getText().toString();
                if(email_txt == " " && pass_txt == " ") {
                    Toast.makeText(MainActivity.this, "Please enter the email and password for login", Toast.LENGTH_SHORT).show();

                }
                else if(pass_txt.length() < 4) {
                    Toast.makeText(MainActivity.this, "Length of password must be greater than 4", Toast.LENGTH_SHORT).show();

                }
                else {
                    EnterUser(email_txt,pass_txt);
                }
            }
        });

    }

    private void  UserAuthentication(String email_txt, String pass_txt) {
        mAuth.createUserWithEmailAndPassword(email_txt, pass_txt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserActivity.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login falid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void EnterUser(String email,String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserActivity.class));
                        }
                    }
                });
    }
}