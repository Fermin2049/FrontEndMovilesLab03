package com.fermin2049.proyectofinallab3.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.fermin2049.proyectofinallab3.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView tvNombre = binding.tvNombre;
        final TextView tvApellido = binding.tvApellido;
        final TextView tvDni = binding.tvDni;
        final TextView tvTelefono = binding.tvTelefono;
        final TextView tvEmail = binding.tvEmail;
        final ImageView ivFotoPerfil = binding.ivFotoPerfil;

        profileViewModel.getPropietario().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                tvNombre.setText(profile.getNombre());
                tvApellido.setText(profile.getApellido());
                tvDni.setText(profile.getDni());
                tvTelefono.setText(profile.getTelefono());
                tvEmail.setText(profile.getEmail());
                Glide.with(this).load(profile.getFotoPerfil()).into(ivFotoPerfil);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}