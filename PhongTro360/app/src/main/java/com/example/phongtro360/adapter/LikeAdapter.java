package com.example.phongtro360.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.phongtro360.DetailActivity;
import com.example.phongtro360.R;
import com.example.phongtro360.model.News;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.viewholder> {

    ArrayList<News> items;
    Context context;

    @NonNull
    @Override
    public LikeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.like,parent,false);
        return new  viewholder(inflater);
    }
    public LikeAdapter(ArrayList<News> items) {
        this.items = items;
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        // Hiển thị thông tin khác
        holder.tvPrice.setText(Integer.toString(items.get(position).getGiaPhong()));
        holder.tvDiaChi.setText(items.get(position).getDiaChi());
        holder.tvTieuDe.setText(items.get(position).getTieuDe());
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

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tvPrice,tvDiaChi,tvDienTich,tvTieuDe;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvDienTich = itemView.findViewById(R.id.tvDienTich);
            pic = itemView.findViewById(R.id.pic);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);

        }
    }
}
