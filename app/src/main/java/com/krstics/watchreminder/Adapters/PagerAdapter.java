package com.krstics.watchreminder.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.krstics.watchreminder.Fragments.FragmentOne;
import com.krstics.watchreminder.Fragments.FragmentThree;
import com.krstics.watchreminder.Fragments.FragmentTwo;

import java.util.HashMap;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private HashMap<Integer, String> fragmentTags;
    private FragmentManager fragmentManager;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        fragmentManager = fm;
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if(obj instanceof Fragment)
            fragmentTags.put(position, ((Fragment) obj).getTag());

        return obj;
    }

    public Fragment getFragment(int position){
        String tag = fragmentTags.get(position);
        if(tag == null)
            return null;

        return fragmentManager.findFragmentByTag(tag);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}