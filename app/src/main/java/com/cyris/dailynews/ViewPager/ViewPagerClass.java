package com.cyris.dailynews.ViewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cyris.dailynews.Fragments.BuisnessFragment;
import com.cyris.dailynews.Fragments.EntertainmentFragment;
import com.cyris.dailynews.Fragments.FlipEffect;
import com.cyris.dailynews.Fragments.HealthFragment;
import com.cyris.dailynews.Fragments.ScienceFragment;
import com.cyris.dailynews.Fragments.SportsFrgment;
import com.cyris.dailynews.Fragments.TechFragment;

public class ViewPagerClass extends FragmentStatePagerAdapter {
    public ViewPagerClass(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            //case 0:return new SwipeEffect();
            case 0:return new FlipEffect();
            case 1:return new HealthFragment();
            case 2:return new EntertainmentFragment();
            case 3:return new TechFragment();
            case 4:return new SportsFrgment();
            case 5:return new BuisnessFragment();
            case 6:return new ScienceFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
           // case 0:return "SwipePage";
            case 0:return "All";
            case 1:return "Health";
            case 2:return "Entertainment";
            case 3:return "Tech";
            case 4:return "Sports";
            case 5:return "Buisness";
            case 6:return "Science";
        }
        return super.getPageTitle(position);
    }


}
