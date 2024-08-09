package com.example.phongtro360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.phongtro360.R;
import com.example.phongtro360.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IntroActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        navigationView = findViewById(R.id.nav);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: navigationView.getMenu().findItem(R.id.mHome);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.mSearch);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.mLike);
                        break;
                    case 3: navigationView.getMenu().findItem(R.id.mAccount);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mHome: viewPager.setCurrentItem(0);
                        break;
                    case R.id.mSearch: viewPager.setCurrentItem(1);
                        break;
                    case R.id.mLike: viewPager.setCurrentItem(2);
                        break;
                    case R.id.mAccount: viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }
}