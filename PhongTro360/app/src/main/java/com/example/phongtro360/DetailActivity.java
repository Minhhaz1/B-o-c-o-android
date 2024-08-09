package com.example.phongtro360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.phongtro360.model.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends BaseActivity {
    TextView tvTieuDe, tvDiaChi,tvSdt,tvDienTich,tvMota,tvPrice;
    ImageView imgBack,imgLike,pic;
    private News object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        getIntentExtra();
        tvTieuDe.setText(object.getTieuDe());
        tvDiaChi.setText(object.getDiaChi());
        tvSdt.setText(object.getSdt());
        tvDienTich.setText(Double.toString(object.getDienTich()));
        tvPrice.setText(Integer.toString(object.getGiaPhong())+"/Tháng");
        tvMota.setText(object.getMoTa());
        String firstImageUri = object.getImageUris().get(0);
        Log.d("URI","SS" + firstImageUri);
        // Hiển thị ảnh đầu tiên vào ImageView pic
        Glide.with(DetailActivity.this)
                .load(firstImageUri)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(pic);
        imgBack.setOnClickListener(view -> finish());
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cập nhật trạng thái like trong object
                object.setLike(true);

                // Lấy reference đến Firebase Realtime Database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("news").child(Integer.toString(object.getId()));

                // Cập nhật giá trị like trên Firebase
                databaseReference.child("like").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Hiển thị thông báo khi cập nhật thành công
                            Toast.makeText(DetailActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_LONG).show();
                        } else {
                            // Xử lý lỗi khi cập nhật thất bại
                            Toast.makeText(DetailActivity.this, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void getIntentExtra() {
        object = (News) getIntent().getSerializableExtra("object");
    }

    private void initView() {
        tvTieuDe = findViewById(R.id.tvTieuDe);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvSdt = findViewById(R.id.tvSdt);
        tvDienTich = findViewById(R.id.tvDienTich);
        tvMota = findViewById(R.id.tvMoTa);
        tvPrice = findViewById(R.id.tvPrice);
        imgBack = findViewById(R.id.imgBack);
        imgBack = findViewById(R.id.imgBack);
        imgLike = findViewById(R.id.imgLike);
        pic = findViewById(R.id.pic);
    }
}