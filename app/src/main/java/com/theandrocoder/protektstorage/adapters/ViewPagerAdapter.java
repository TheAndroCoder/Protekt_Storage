package com.theandrocoder.protektstorage.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.theandrocoder.protektstorage.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments=null;
    private List<String> titles=null;
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments=new ArrayList<>();
        titles=new ArrayList<>();
        //super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void add(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }



    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setListener(MainActivity mainActivity) {

    }
}
