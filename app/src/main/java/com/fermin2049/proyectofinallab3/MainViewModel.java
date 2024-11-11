package com.fermin2049.proyectofinallab3;

import android.app.Application;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
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
    private NavController navController;

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

    public void setupNavController(MainActivity activity) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    public void setupTabLayout(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home).setTag(R.id.nav_home));
        tabLayout.addTab(tabLayout.newTab().setText("Perfil").setIcon(R.drawable.ic_profile).setTag(R.id.nav_profile));
        tabLayout.addTab(tabLayout.newTab().setText("Inmuebles").setIcon(R.drawable.ic_property).setTag(R.id.propertyFragment));
        tabLayout.addTab(tabLayout.newTab().setText("Contratos").setIcon(R.drawable.ic_contract).setTag(R.id.nav_contract));
        tabLayout.addTab(tabLayout.newTab().setText("Inquilinos").setIcon(R.drawable.ic_tenant).setTag(R.id.nav_tenant));
        tabLayout.addTab(tabLayout.newTab().setText("Pagos").setIcon(R.drawable.ic_pay).setTag(R.id.nav_pay));
        tabLayout.addTab(tabLayout.newTab().setText("Logout").setIcon(R.drawable.ic_logout).setTag(R.id.nav_logout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Object tag = tab.getTag();
                if (tag instanceof Integer) {
                    int navId = (Integer) tag;
                    if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() != navId) {
                        navController.navigate(navId);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}