package com.fermin2049.proyectofinallab3.login;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.ShakeDetector;
import com.fermin2049.proyectofinallab3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel lvm;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lvm = new ViewModelProvider(this).get(LoginViewModel.class);
        lvm.setContext(this);

        // Initialize ShakeDetector and SensorManager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        mShakeDetector.setOnShakeListener(() -> {
            // Automatically fill in the login credentials on shake
            binding.etLoginEmail.setText("fermin2049@gmail.com");
            binding.etLoginPassword.setText("123");
            lvm.llamarLogin("fermin2049@gmail.com", "123");
        });

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().hide();
        }

        // Login button click listener
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etLoginEmail.getText().toString();
            String password = binding.etLoginPassword.getText().toString();
            lvm.llamarLogin(email, password);
        });

        // Registration button click listener
        binding.btnSignup.setOnClickListener(v -> {
            String dni = binding.etSignupDNI.getText().toString();
            String apellido = binding.etSignupApellido.getText().toString();
            String nombre = binding.etSignupNombre.getText().toString();
            String telefono = binding.etSignupTelefono.getText().toString();
            String email = binding.etSignupEmail.getText().toString();
            String password = binding.etSignupPassword.getText().toString();
            lvm.llamarRegistro(dni, apellido, nombre, telefono, email, password);
        });

        lvm.limpiarCampos.observe(this, limpiar -> limpiarCamposRegistro());

        binding.tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
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
