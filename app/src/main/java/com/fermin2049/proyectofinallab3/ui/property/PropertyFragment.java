package com.fermin2049.proyectofinallab3.ui.property;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.fermin2049.proyectofinallab3.databinding.FragmentPropertyBinding;


public class PropertyFragment extends Fragment {

    private FragmentPropertyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PropertyViewModel propertyViewModel =
                new ViewModelProvider(this).get(PropertyViewModel.class);

        binding = FragmentPropertyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        propertyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}