package com.example.phongtro360.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.annotation.*;

import com.example.phongtro360.fragment.FragmentAccount;
import com.example.phongtro360.fragment.FragmentAccoutLogin;
import com.example.phongtro360.fragment.FragmentHome;
import com.example.phongtro360.fragment.FragmentLike;
import com.example.phongtro360.fragment.FragmentLikeLogin;
import com.example.phongtro360.fragment.FragmentSearch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private FirebaseUser firebaseUser;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentSearch();
            case 2:
                // Nếu người dùng chưa đăng nhập, hiển thị FragmentLoginPrompt
                return firebaseUser == null ? new FragmentLike() : new FragmentLikeLogin();
            case 3:
                // Nếu người dùng chưa đăng nhập, hiển thị FragmentLoginPrompt
                return firebaseUser == null ? new FragmentAccount() : new FragmentAccoutLogin();
            default:
                return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
