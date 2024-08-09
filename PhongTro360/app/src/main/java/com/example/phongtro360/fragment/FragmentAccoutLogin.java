package com.example.phongtro360.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phongtro360.IntroActivity;
import com.example.phongtro360.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentAccoutLogin extends Fragment {

        EditText edPhongDaDang,edNoiO,edDangXuat;
        TextView tvName;

    public FragmentAccoutLogin() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accout_login, container, false);
        edDangXuat = view.findViewById(R.id.edLogout);
        tvName = view.findViewById(R.id.tvName);
        edDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), IntroActivity.class);
                startActivity(intent);
            }
        });
        // Lấy ID người dùng từ Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Lấy thông tin người dùng từ Firebase Realtime Database
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("users").child(userId);
            database.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userName = dataSnapshot.getValue(String.class);

                    // Cập nhật TextView với tên người dùng
                    TextView tvName = view.findViewById(R.id.tvName);
                    if (userName != null) {
                        tvName.setText(userName);
                    } else {
                        tvName.setText("Tên người dùng không có sẵn");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý lỗi
                    Log.w("DatabaseError", "loadUserName:onCancelled", databaseError.toException());
                }
            });
        }

        return view;
    }
}