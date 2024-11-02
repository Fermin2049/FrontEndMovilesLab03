package com.fermin2049.proyectofinallab3;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.fermin2049.proyectofinallab3.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializar ViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Configurar adaptador en el ViewPager2 y TabLayout
        binding.viewPager.setAdapter(mainViewModel.getViewPagerAdapter(this));
        mainViewModel.setupTabIcons(binding.tabLayout, binding.viewPager);

        // Registrar el callback para monitorear los cambios de página en ViewPager2
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mainViewModel.onPageSelected(position);
            }
        });
    }
}
