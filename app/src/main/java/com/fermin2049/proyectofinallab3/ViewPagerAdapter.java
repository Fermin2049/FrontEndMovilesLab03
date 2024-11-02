package com.fermin2049.proyectofinallab3;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fermin2049.proyectofinallab3.ui.home.HomeFragment;
import com.fermin2049.proyectofinallab3.ui.profile.ProfileFragment;
import com.fermin2049.proyectofinallab3.ui.property.PropertyFragment;
import com.fermin2049.proyectofinallab3.ui.contract.ContractFragment;
import com.fermin2049.proyectofinallab3.ui.tenant.TenantFragment;
import com.fermin2049.proyectofinallab3.ui.pay.PayFragment;
import com.fermin2049.proyectofinallab3.ui.logout.LogoutFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new PropertyFragment();
            case 3:
                return new ContractFragment();
            case 4:
                return new TenantFragment();
            case 5:
                return new PayFragment();
            case 6:
                return new LogoutFragment();
            default:
                return new HomeFragment(); // Retorna un fragmento predeterminado si la posición no coincide
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
