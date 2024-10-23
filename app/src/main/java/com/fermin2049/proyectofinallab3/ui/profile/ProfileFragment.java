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
import com.fermin2049.proyectofinallab3.models.Property;

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

        profileViewModel.getPropietario().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario != null) {
                tvNombre.setText(propietario.getNombre());
                tvApellido.setText(propietario.getApellido());
                tvDni.setText(propietario.getDni());
                tvTelefono.setText(propietario.getTelefono());
                tvEmail.setText(propietario.getEmail());
                Glide.with(this).load(propietario.getFotoPerfil()).into(ivFotoPerfil);
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