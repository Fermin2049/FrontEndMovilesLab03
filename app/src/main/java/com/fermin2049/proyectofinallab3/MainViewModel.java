package com.fermin2049.proyectofinallab3;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainViewModel extends AndroidViewModel {

    private final String[] tabTitles = {"Home", "Profile", "Property", "Contract", "Tenant", "Pay", "Logout"};
    private final int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_profile,
            R.drawable.ic_property,
            R.drawable.ic_contract,
            R.drawable.ic_tenant,
            R.drawable.ic_pay,
            R.drawable.ic_logout
    };

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public ViewPagerAdapter getViewPagerAdapter(MainActivity activity) {
        return new ViewPagerAdapter(activity);
    }

    public void setupTabIcons(TabLayout tabLayout, ViewPager2 viewPager) {
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position < tabTitles.length && position < tabIcons.length) {
                    tab.setText(tabTitles[position]);
                    Drawable icon = ContextCompat.getDrawable(getApplication(), tabIcons[position]);
                    if (icon != null) {
                        icon.setTintList(null); // Eliminar cualquier tinte aplicado al Drawable
                        tab.setIcon(icon);
                    }
                }
            }
        }).attach();
    }

    public void onPageSelected(int position) {
        Log.d("MainViewModel", "Página seleccionada: " + position);
    }
}
