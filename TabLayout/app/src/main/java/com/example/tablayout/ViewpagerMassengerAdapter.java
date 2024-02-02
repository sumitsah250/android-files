package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewpagerMassengerAdapter extends FragmentPagerAdapter {
    public ViewpagerMassengerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       if(position == 0){
           return new Fragment1();
       } else if (position ==1) {
           return new Fragment2();
       }
       else {
           return new Fragment3();
       }
    }

    @Override
    public int getCount() {
        return 3; // o of tabes
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "charts";
        } else if (position == 1) {
            return  "status";
        }
        else {
            return "calls";
        }

    }
}
