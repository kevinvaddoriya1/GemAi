package com.techbuddys.appui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.techbuddys.appui.R;
import com.techbuddys.appui.manager.ApiManager;
import com.techbuddys.appui.model.RegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword;
    Button btnSignUp;

    TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtLogin = findViewById(R.id.txtLogin);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (name.isEmpty()) {
                    edtName.setError("Please Enter Username");
                    edtName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    edtEmail.setError("Please Enter Email");
                    edtEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    edtPassword.setError("Please Enter Password");
                    edtPassword.requestFocus();
                    return;
                }
                processData(name, email, password);
            }
        });
    }

    private void processData(final String name, final String email, final String password) {
        Call<RegisterModel> call = ApiManager.getInstance().apiInterface.setUserData(name, email, password);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(@NonNull Call<RegisterModel> call, @NonNull Response<RegisterModel> response) {

                if (response.isSuccessful()) {
                    RegisterModel model = response.body();
                    if (model.isError()) {
                        Toast.makeText(SignUpActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable t) {
                Log.e("onFailure", "Something Want Wrong : " + t.getMessage());
                edtEmail.setText("");
                edtName.setText("");
                edtPassword.setText("");
            }
        });
    }

}