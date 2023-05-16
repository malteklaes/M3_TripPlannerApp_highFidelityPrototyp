package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void addFragment(Fragment fragment, String title) {
        Log.d("malte3", "diese Liste null: " + (fragments == null));
        Log.d("malte3", "diese Liste fragments.size(): " + fragments.size());
        Log.d("malte3", "methode param fragment: " + fragment);
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("malte2" , " im getItem(): position: " + position + " und was: " + fragments.get(position));
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
