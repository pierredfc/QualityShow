package videolibrary.street.quality.qualityshow.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.fragments.ProfileAboutFragment;
import videolibrary.street.quality.qualityshow.fragments.ProfileMoviesFragment;
import videolibrary.street.quality.qualityshow.fragments.ProfileShowsFragment;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.ui.utils.DrawerMenuUtils;


/**
 * Created by EdouardEtudiant on 08/11/2015.
 */
public class ProfileActivity extends AppCompatActivity implements ClickListener {

    private Toolbar toolbar;
    private DrawerMenuUtils drawer;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");

        drawer = new DrawerMenuUtils(savedInstanceState, this, toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFrag(ProfileShowsFragment.newInstance(), "My shows");
        viewPagerAdapter.addFrag(ProfileMoviesFragment.newInstance(), "My movies");
        viewPagerAdapter.addFrag(ProfileAboutFragment.newInstance(), "About me");
        viewPager.setAdapter(viewPagerAdapter);

    }


    @Override
    public void onItemClick(Object item) {
        Intent intent = new Intent(this, ShowActivity.class);

        if (item instanceof Serie) {
            intent.putExtra("isMovie", false);
            if(QualityShowApplication.getUserHelper() != null){
                intent.putExtra("isSearch", false);
                intent.putExtra("show", (int)((Serie) item).getId());
            }else {
                intent.putExtra("show", (Serie) item);
            }
        } else {
            intent.putExtra("isMovie", true);
            if(QualityShowApplication.getUserHelper() != null){
                intent.putExtra("isSearch", false);
                intent.putExtra("show", (int)((Film) item).getId());
            } else {
                intent.putExtra("show", (Film) item);
            }
        }

        startActivity(intent);
    }


    static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private static int NB_TABS = 3;
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}




