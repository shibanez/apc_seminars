package apc.edu.ph.apcseminarmi151;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathew on 11/12/2017.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentNameList = new ArrayList<>();

    public void addFragment(Fragment fragment, String name){
        fragmentList.add(fragment);
        fragmentNameList.add(name);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentNameList.get(position);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
