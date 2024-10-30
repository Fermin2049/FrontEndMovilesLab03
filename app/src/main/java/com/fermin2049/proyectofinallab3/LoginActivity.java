package com.fermin2049.proyectofinallab3;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fermin2049.proyectofinallab3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel lvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lvm = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(LoginViewModel.class);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().hide();
        }

        // Manejar el login
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etLoginEmail.getText().toString();
            String password = binding.etLoginPassword.getText().toString();
            Log.d("LoginActivity", "Attempting login with email: " + email);
            lvm.llamarLogin(email, password);
        });

        // Configurar el botón de registro
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.etSignupEmail.getText().toString();
            String password = binding.etSignupPassword.getText().toString();
            // Lógica para manejar el registro (ejemplo: lvm.llamarRegistro(username, email, password))
        });
    }
}