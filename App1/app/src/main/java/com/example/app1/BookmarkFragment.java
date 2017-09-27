package com.example.app1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Home on 19-07-2017.
 */
public class BookmarkFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookmarks_fragment, container, false);
        getActivity().setTitle("Bookmarks");

        FragmentManager fragmentManager = getFragmentManager();
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager, MainActivity.context);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        String tabTitles[] = new String[]{"Images", "Videos"};
        Context context= MainActivity.context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new Bookmarkedimages();
                case 1:
                    return new Bookmarkedvideos();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

    }
}

