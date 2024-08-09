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
import android.widget.Button;
import android.widget.EditText;

import com.example.phongtro360.R;
import com.example.phongtro360.adapter.PhongChoThueAdapter;
import com.example.phongtro360.model.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {

    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recSearchResults;
    private PhongChoThueAdapter adapter;
    private ArrayList<News> newsList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        etSearch = view.findViewById(R.id.edSearch);

        recSearchResults = view.findViewById(R.id.recSearchResults);





        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            performSearch();
            return true;
        });

        return view;
    }

    private void performSearch() {
        String searchQuery = etSearch.getText().toString().trim();
        if (!searchQuery.isEmpty()) {
            DatabaseReference myRef = database.getReference("news");
            myRef.orderByChild("diaChi").startAt(searchQuery).endAt(searchQuery + "\uf8ff").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    newsList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        News news = snapshot.getValue(News.class);
                        if (news != null) {
                            newsList.add(news);
                        }
                    }
                    Log.d("FragmentSearch", "Data received: " + newsList.size() + " items");
                    adapter = new PhongChoThueAdapter(newsList);
                    recSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recSearchResults.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FragmentSearch", "Database error: " + databaseError.getMessage());
                }
            });
        }
    }
}
