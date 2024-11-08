package com.fermin2049.proyectofinallab3.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.fermin2049.proyectofinallab3.databinding.FragmentProfileBinding;
import com.fermin2049.proyectofinallab3.models.Propietario;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private ActivityResultLauncher<Intent> arl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Uri uri = result.getData().getData();
            requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            profileViewModel.recibirFoto(uri);
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel.getPropietario().observe(getViewLifecycleOwner(), this::actualizarVistaConPropietario);
        profileViewModel.getUriMutable().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                Glide.with(this)
                     .load(uri)
                     .into(binding.ivFotoPerfil);
            }
        });
        binding.btnSeleccionarImagen.setOnClickListener(v -> abrirGaleria());
        binding.btnActualizar.setOnClickListener(v -> actualizarPerfil());

        profileViewModel.fetchPropietarioDetails(requireContext());
    }

    private void actualizarPerfil() {
        Propietario propietario = profileViewModel.getPropietario().getValue();
        propietario.setNombre(binding.etNombre.getText().toString());
        propietario.setApellido(binding.etApellido.getText().toString());
        propietario.setDni(binding.etDni.getText().toString());
        propietario.setTelefono(binding.etTelefono.getText().toString());
        propietario.setEmail(binding.etEmail.getText().toString());

        String currentPassword = binding.etPasswordActual.getText().toString();
        String newPassword = binding.etPasswordNueva.getText().toString();

        if (!currentPassword.isEmpty() && !newPassword.isEmpty()) {
            propietario.setCurrentPassword(currentPassword);
            propietario.setPassword(newPassword);
        } else {
            propietario.setCurrentPassword("");
            propietario.setPassword("");
        }

        Uri fotoUri = profileViewModel.getUriMutable().getValue();
        profileViewModel.actualizarPropietario(propietario, fotoUri, requireContext());
    }

    private void actualizarVistaConPropietario(Propietario propietario) {
        binding.etNombre.setText(propietario.getNombre());
        binding.etApellido.setText(propietario.getApellido());
        binding.etDni.setText(propietario.getDni());
        binding.etTelefono.setText(propietario.getTelefono());
        binding.etEmail.setText(propietario.getEmail());

        Uri fotoUri = Uri.parse(propietario.getFotoPerfil());
        Glide.with(this)
             .load(fotoUri)
             .into(binding.ivFotoPerfil);
        profileViewModel.recibirFoto(fotoUri);
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        arl.launch(intent);
    }
}