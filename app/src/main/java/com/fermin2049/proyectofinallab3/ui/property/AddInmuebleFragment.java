package com.fermin2049.proyectofinallab3.ui.property;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fermin2049.proyectofinallab3.databinding.FragmentAddInmuebleBinding;

public class AddInmuebleFragment extends Fragment {

    private FragmentAddInmuebleBinding binding;
    private AddInmuebleViewModel addInmuebleViewModel;
    private static final int PICK_IMAGE_REQUEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addInmuebleViewModel = new ViewModelProvider(this).get(AddInmuebleViewModel.class);

        binding.buttonSelectImage.setOnClickListener(v -> openFileChooser());
        binding.buttonAddInmueble.setOnClickListener(v -> addInmueble());

        addInmuebleViewModel.getImageUri().observe(getViewLifecycleOwner(), uri -> {

                binding.imageViewInmueble.setImageURI(uri);

        });

        return root;
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            addInmuebleViewModel.setImageUri(imageUri);
        }
    }

    private void addInmueble() {
        String direccion = binding.editTextDireccion.getText().toString();
        String uso = binding.editTextUso.getText().toString();
        String tipo = binding.editTextTipo.getText().toString();
        int ambientes = Integer.parseInt(binding.editTextAmbientes.getText().toString());
        double precio = Double.parseDouble(binding.editTextPrecio.getText().toString());
        String estado = binding.editTextEstado.getText().toString();

        addInmuebleViewModel.addInmueble(direccion, uso, tipo, ambientes, precio, estado, getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}