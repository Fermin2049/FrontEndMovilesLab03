package com.fermin2049.proyectofinallab3;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import com.fermin2049.proyectofinallab3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Configurar NavController
        viewModel.setupNavController(this);

        // Configurar el TabLayout con NavController
        viewModel.setupTabLayout(binding.tabLayout);
    }
}