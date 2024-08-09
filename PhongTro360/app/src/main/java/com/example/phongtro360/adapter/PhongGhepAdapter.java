package com.example.phongtro360.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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

public class PhongGhepAdapter extends RecyclerView.Adapter<PhongGhepAdapter.viewholder> {

    ArrayList<News> items;
    Context context;

    public PhongGhepAdapter(ArrayList<News> items) {
        this.items = items;
    }

    @NonNull
    @Override

    public PhongGhepAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        // Kiểm tra nếu danh sách ImageUris không rỗng
        if (items.get(position).getImageUris() != null && !items.get(position).getImageUris().isEmpty()) {
            String firstImageUri = items.get(position).getImageUris().get(0);
            Log.d("sss", firstImageUri);

            // Sử dụng MediaStore để lấy đường dẫn thực
            try {
                Uri imageUri = Uri.parse(firstImageUri);
                String imagePath = getRealPathFromUri(holder.itemView.getContext(), imageUri);
                if (imagePath != null) {
                    // Hiển thị ảnh đầu tiên vào ImageView pic với đường dẫn thực
                    Glide.with(holder.itemView.getContext())
                            .load(imagePath)
                             // Ảnh hiển thị khi tải ảnh thật thất bại
                            .into(holder.pic);
                } else {
                    Log.d("sss", "Failed to get real path from URI");
                }
            } catch (Exception e) {
                Log.e("sss", "Error loading image", e);
            }
        } else {
            Log.d("sss", "No image URI available");
            // Nếu không có ảnh, hiển thị ảnh mặc định

        }


    holder.itemView.setOnClickListener(view -> {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("object", items.get(position));
        holder.itemView.getContext().startActivity(intent);
    });
    }

    // Hàm để lấy đường dẫn thực từ URI
    private String getRealPathFromUri(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return items.size();
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
