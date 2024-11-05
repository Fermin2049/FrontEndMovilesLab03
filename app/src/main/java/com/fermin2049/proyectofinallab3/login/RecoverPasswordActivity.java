package com.fermin2049.proyectofinallab3.login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.fermin2049.proyectofinallab3.databinding.ActivityRecoverPasswordBinding;

public class RecoverPasswordActivity extends AppCompatActivity {
    private RecoverPasswordViewModel rpvm;
    private ActivityRecoverPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecoverPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rpvm = new ViewModelProvider(this).get(RecoverPasswordViewModel.class);

        binding.btnEnviar.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            rpvm.solicitarRestablecimiento(email);
        });

        rpvm.observarSolicitudEnviada(this);
    }
}