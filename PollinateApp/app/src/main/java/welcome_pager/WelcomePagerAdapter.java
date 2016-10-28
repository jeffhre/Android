package welcome_pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhrebena on 12/7/15.
 */
public class WelcomePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFrags;
    private String mCurrUser;

    public WelcomePagerAdapter(FragmentManager manager, String email) {
        super(manager);

        mCurrUser = email;

        mFrags = new ArrayList<Fragment>();

        mFrags.add(new WelcomePagerFragment1());
        mFrags.add(new WelcomePagerFragment2());

        Fragment wpf3 = new WelcomePagerFragment3();
        Bundle b = new Bundle();
        b.putString("KEY_EMAIL", mCurrUser);
        wpf3.setArguments(b);
        mFrags.add(wpf3);


    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = mFrags.get(position);
        return f;
    }

    @Override
    public int getCount() {
        return mFrags.size();
    }
}
