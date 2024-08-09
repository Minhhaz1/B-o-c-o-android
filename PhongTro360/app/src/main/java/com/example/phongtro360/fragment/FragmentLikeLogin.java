package com.example.phongtro360.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phongtro360.R;
import com.example.phongtro360.adapter.LikeAdapter;
import com.example.phongtro360.adapter.PhongChoThueAdapter;
import com.example.phongtro360.model.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentLikeLogin extends Fragment {
    private RecyclerView recLike;
    FirebaseDatabase database = FirebaseDatabase.getInstance();;
    public FragmentLikeLogin() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_like_login, container, false);
        recLike = view.findViewById(R.id.recLike);
        recLike.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        initPhongLike();
        return view;
    }

    private void initPhongLike() {
        DatabaseReference myRef = database.getReference("news");
        ArrayList<News> list = new ArrayList<>();
        myRef.orderByChild("like").equalTo(true).addValueEventListener(new ValueEventListener() {
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
                    recLike.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                    RecyclerView.Adapter adapter = new LikeAdapter(list);
                    recLike.setAdapter(adapter);
                }


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
}