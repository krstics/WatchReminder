package com.krstics.watchreminder.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.krstics.watchreminder.Fragments.FragmentFour;
import com.krstics.watchreminder.Fragments.FragmentOne;
import com.krstics.watchreminder.Fragments.FragmentThree;
import com.krstics.watchreminder.Fragments.FragmentTwo;

import java.util.HashMap;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private SparseArray<Fragment> registeredFragments;
    private FragmentManager fragmentManager;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        fragmentManager = fm;
        this.mNumOfTabs = NumOfTabs;
        registeredFragments = new SparseArray<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getFragment(int position){
        return registeredFragments.get(position);
    }

    @Override
    public Fragment getItem(int position) {

        Log.e("Pager Adapter", Integer.toString(position));
        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            case 3:
                return new FragmentFour();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}