package com.fermin2049.proyectofinallab3.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mostrar el diálogo de confirmación cuando se acceda a esta vista
        showExitConfirmationDialog();

        return root;
    }

    private void showExitConfirmationDialog() {
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmación de salida");
        builder.setMessage("¿Está seguro de que desea salir?");

        // Opción "Sí"
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Salir de la aplicación
                getActivity().finishAffinity(); // Cierra todas las actividades
            }
        });

        // Opción "No"
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirigir al HomeFragment
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_home);
            }
        });

        // Mostrar el cuadro de diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}