package com.fermin2049.proyectofinallab3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fermin2049.proyectofinallab3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel lvm;
    private ViewFlipper viewFlipper;
    private Button btnToSignup, btnToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lvm = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(LoginViewModel.class);

        viewFlipper = findViewById(R.id.viewFlipper);
        btnToSignup = findViewById(R.id.btnToSignup);
        btnToLogin = findViewById(R.id.btnToLogin);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().hide();
        }

        // Configurar animaciones del ViewFlipper
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right));

        // Manejar el botón para mostrar la vista de registro
        btnToSignup.setOnClickListener(v -> viewFlipper.setDisplayedChild(0));

        // Manejar el botón para mostrar la vista de login
        btnToLogin.setOnClickListener(v -> viewFlipper.setDisplayedChild(1));

        // Manejar el login
        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etLoginEmail.getText().toString();
            String password = binding.etLoginPassword.getText().toString();
            Log.d("LoginActivity", "Attempting login with username: " + username);
            lvm.llamarLogin(username, password);
        });

        // Configurar el botón de registro (si es necesario)
        binding.btnSignup.setOnClickListener(v -> {
            String username = binding.etSignupUsername.getText().toString();
            String email = binding.etSignupEmail.getText().toString();
            String password = binding.etSignupPassword.getText().toString();
            // Lógica para manejar el registro (ejemplo: lvm.llamarRegistro(username, email, password))
        });
    }
}
