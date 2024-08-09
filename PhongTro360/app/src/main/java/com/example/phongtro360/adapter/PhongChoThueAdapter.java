package com.example.phongtro360.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.phongtro360.DetailActivity;
import com.example.phongtro360.R;
import com.example.phongtro360.model.News;

import java.util.ArrayList;

public class PhongChoThueAdapter extends RecyclerView.Adapter<PhongChoThueAdapter.viewholder> {

    ArrayList<News> items;
    Context context;

    public PhongChoThueAdapter(ArrayList<News> items) {
        this.items = items;
    }

    @NonNull
    @Override

    public PhongChoThueAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.phong_cho_thue,parent,false);
        return  new viewholder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        // Hiển thị thông tin khác
        holder.tvPrice.setText(Integer.toString(items.get(position).getGiaPhong()));
        holder.tvDiaChi.setText(items.get(position).getDiaChi());
        holder.tvDienTich.setText(Double.toString(items.get(position).getDienTich()) + "m2");

        // Kiểm tra danh sách ảnh không rỗng trước khi truy cập
        if (items.get(position).getImageUris() != null && !items.get(position).getImageUris().isEmpty()) {
            // Lấy đường dẫn của ảnh đầu tiên
            String firstImageUri = items.get(position).getImageUris().get(0);
            Log.d("URI","SS" + firstImageUri);
            // Hiển thị ảnh đầu tiên vào ImageView pic
            Glide.with(context)
                    .load(firstImageUri)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.pic);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object",  items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void updateData(ArrayList<News> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tvPrice,tvDiaChi,tvDienTich;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvDienTich = itemView.findViewById(R.id.tvDienTich);
            pic = itemView.findViewById(R.id.pic);

        }
    }
}
