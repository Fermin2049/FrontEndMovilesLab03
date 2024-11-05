package com.fermin2049.proyectofinallab3.login;

import android.content.Intent;
import android.os.Bundle;
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
        lvm.setContext(this); // Set the activity context

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().hide();
        }

        // Manejar el login
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etLoginEmail.getText().toString();
            String password = binding.etLoginPassword.getText().toString();
            lvm.llamarLogin(email, password);
        });

        // Configurar el botón de registro
        binding.btnSignup.setOnClickListener(v -> {
            String dni = binding.etSignupDNI.getText().toString();
            String apellido = binding.etSignupApellido.getText().toString();
            String nombre = binding.etSignupNombre.getText().toString();
            String telefono = binding.etSignupTelefono.getText().toString();
            String email = binding.etSignupEmail.getText().toString();
            String password = binding.etSignupPassword.getText().toString();
            lvm.llamarRegistro(dni, apellido, nombre, telefono, email, password);
        });

        // Observar el resultado del registro
        lvm.limpiarCampos.observe(this, limpiar -> limpiarCamposRegistro());

        // Abrir RecoverPasswordActivity al hacer clic en "¿Olvidaste tu contraseña?"
        binding.tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void limpiarCamposRegistro() {
        binding.etSignupDNI.setText("");
        binding.etSignupApellido.setText("");
        binding.etSignupNombre.setText("");
        binding.etSignupTelefono.setText("");
        binding.etSignupEmail.setText("");
        binding.etSignupPassword.setText("");
    }
}