package com.fermin2049.proyectofinallab3.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.fermin2049.proyectofinallab3.databinding.FragmentProfileBinding;
import com.fermin2049.proyectofinallab3.models.Propietario;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                if (propietario != null) {
                    Log.d(TAG, "Propietario data: " + propietario.toString());
                    binding.etNombre.setText(propietario.getNombre());
                    binding.etApellido.setText(propietario.getApellido());
                    binding.etDni.setText(propietario.getDni());
                    binding.etTelefono.setText(propietario.getTelefono());
                    binding.etEmail.setText(propietario.getEmail());

                    if (propietario.getFotoPerfil() != null && !propietario.getFotoPerfil().isEmpty()) {
                        Log.d(TAG, "Loading image from URL: " + propietario.getFotoPerfil());
                        Glide.with(ProfileFragment.this)
                                .load(propietario.getFotoPerfil())
                                .into(binding.ivFotoPerfil);
                    } else {
                        Log.d(TAG, "No profile image found.");
                    }
                }
            }
        });

        profileViewModel.fetchPropietarioDetails(getContext());

        binding.btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Log.d(TAG, "Image selected: " + imageUri.toString());
            // Handle image selection and update profile
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}