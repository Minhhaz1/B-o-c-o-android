package com.example.phongtro360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phongtro360.R;
import com.example.phongtro360.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends BaseActivity {
    Button btnSignUp;
    TextView tvBack;
    EditText edName,edMail,edSdt,edPass,edRePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        tvBack.setOnClickListener(view -> {
            finish();
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =edMail.getText().toString();
                String name =edName.getText().toString();
                String sdt =edSdt.getText().toString();
                String pass = edPass.getText().toString();
                String rePass = edRePass.getText().toString();
                if(pass.length()<6){
                    Toast.makeText(SignUpActivity.this, "Your password must be 6 character", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!rePass.equals(pass)){
                    Toast.makeText(SignUpActivity.this, "Password không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid(); // Lấy User ID từ Firebase Authentication
                                String email = user.getEmail(); // Lấy email người dùng

                                // Tạo một đối tượng người dùng để lưu vào Realtime Database
                                User newUser = new User(userId, name, sdt,pass,email);

                                // Lưu vào Realtime Database

                                DatabaseReference usersRef = database.getReference("users");
                                usersRef.child(userId).setValue(newUser);

                                // Chuyển đến MainActivity
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            // Đăng ký thành công
                            Log.i("Unil", "onComplete: Registration successful");
                            startActivity(new Intent(SignUpActivity.this, IntroActivity.class));
                        } else {
                            // Đăng ký thất bại
                            Log.i("Unil", "Failure: " + task.getException());

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // Người dùng đã tồn tại
                                Log.i("Unil", "Failure: Email already exists");
                                Toast.makeText(SignUpActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Lỗi khác
                                Toast.makeText(SignUpActivity.this, "Auth Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        btnSignUp = findViewById(R.id.btnSignUp);
        edName = findViewById(R.id.edName);
        edMail = findViewById(R.id.edMail);
        edSdt = findViewById(R.id.edSdt);
        edPass = findViewById(R.id.edPass);
        edRePass = findViewById(R.id.edRePass);
        tvBack = findViewById(R.id.tvBack);
    }
}