package com.fermin2049.proyectofinallab3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.menu_home);
                            tab.setIcon(R.drawable.ic_home);
                            break;
                        case 1:
                            tab.setText(R.string.menu_profile);
                            tab.setIcon(R.drawable.ic_profile);
                            break;
                        case 2:
                            tab.setText(R.string.menu_property);
                            tab.setIcon(R.drawable.ic_property);
                            break;
                        case 3:
                            tab.setText(R.string.menu_contract);
                            tab.setIcon(R.drawable.ic_contract);
                            break;
                        case 4:
                            tab.setText(R.string.menu_tenant);
                            tab.setIcon(R.drawable.ic_tenant);
                            break;
                        case 5:
                            tab.setText(R.string.menu_pay);
                            tab.setIcon(R.drawable.ic_pay);
                            break;
                        case 6:
                            tab.setText(R.string.menu_logout);
                            tab.setIcon(R.drawable.ic_logout);
                            break;
                    }
                }).attach();

        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_property,
                R.id.nav_contract, R.id.nav_tenant, R.id.nav_logout, R.id.nav_pay)
                .setOpenableLayout(findViewById(R.id.drawer_layout))
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}