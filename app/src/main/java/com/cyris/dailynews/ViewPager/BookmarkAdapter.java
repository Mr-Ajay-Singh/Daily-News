package com.cyris.dailynews.ViewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cyris.dailynews.Fragments.BookmarekShowPage;

public class BookmarkAdapter extends FragmentStatePagerAdapter {
    public BookmarkAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new BookmarekShowPage();
    }

    @Override
    public int getCount() {
        return 1;
    }



}
