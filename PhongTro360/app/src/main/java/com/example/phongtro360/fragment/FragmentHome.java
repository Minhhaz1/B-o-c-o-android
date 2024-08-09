package com.example.phongtro360.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phongtro360.AddActivity;
import com.example.phongtro360.IntroActivity;
import com.example.phongtro360.R;
import com.example.phongtro360.adapter.PhongChoThueAdapter;
import com.example.phongtro360.model.News;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentHome extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =1 ;
    private FloatingActionButton btnAdd;
    private RecyclerView recThue,recGhep;
    FirebaseDatabase database = FirebaseDatabase.getInstance();;
    public FragmentHome() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home2, container, false);
        btnAdd = view.findViewById(R.id.btnAdd);
        recGhep = view.findViewById(R.id.recGhep);
        recThue = view.findViewById(R.id.recThue);
        initPhongChoThue();
        initPhongGhep();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser == null){
                    Toast.makeText(getActivity(), "Cần phải đăng nhập để dùng chức năng", Toast.LENGTH_SHORT).
                            show();
                }
                else{
                    Intent intent = new Intent(getActivity(), AddActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private void initPhongGhep() {
        DatabaseReference myRef = database.getReference("news");
        ArrayList<News> list = new ArrayList<>();
        myRef.orderByChild("loaiTin").equalTo("Tìm người ở ghép").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Chuyển đổi dữ liệu từ DataSnapshot thành đối tượng News
                    News news = snapshot.getValue(News.class);
                    if (news != null) {
                        // Thêm đối tượng News vào danh sách
                        list.add(news);
                    }
                }
                if(list.size()>0){
                    recGhep.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                    RecyclerView.Adapter adapter = new PhongChoThueAdapter(list);
                    recGhep.setAdapter(adapter);
                }
                News news = list.get(0);
                Log.d("sss",news.getDiaChi());
                for (News food : list)

//                    News news = list.get(1);
                    Log.d("FoodItem", "Title: " + food.getDiaChi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Cập nhật giao diện người dùng hoặc thực hiện các hành động khác với danh sách
            // Ví dụ: Cập nhật adapter cho RecyclerView
            // phongChoThueAdapter.notifyDataSetChanged();

        });
    }

    private void initPhongChoThue() {
        DatabaseReference myRef = database.getReference("news");
        ArrayList<News> list = new ArrayList<>();
        myRef.orderByChild("loaiTin").equalTo("Cho thuê").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Chuyển đổi dữ liệu từ DataSnapshot thành đối tượng News
                    News news = snapshot.getValue(News.class);
                    if (news != null) {
                        // Thêm đối tượng News vào danh sách
                        list.add(news);
                    }
                }
                if(list.size()>0){
                    recThue.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                    RecyclerView.Adapter adapter = new PhongChoThueAdapter(list);
                    recThue.setAdapter(adapter);
                }
                News news = list.get(0);
                Log.d("sss",news.getDiaChi());
                for (News food : list)

//                    News news = list.get(1);
                    Log.d("Foodfdfdftem", "fdfdfdfdfdffdffdfdf: " + food.getDiaChi());
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Cập nhật giao diện người dùng hoặc thực hiện các hành động khác với danh sách
                // Ví dụ: Cập nhật adapter cho RecyclerView
                // phongChoThueAdapter.notifyDataSetChanged();

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPhongChoThue();
            } else {
                Toast.makeText(getActivity(), "Quyền truy cập bộ nhớ bị từ chối.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}