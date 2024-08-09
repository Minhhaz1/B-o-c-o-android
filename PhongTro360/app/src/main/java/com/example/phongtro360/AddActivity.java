package com.example.phongtro360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phongtro360.model.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity {
    private EditText edPrice,edDienTich,edTinh,edHuyen,edPhuong,edNha,edTieuDe,edLienHe,edSdt,edMoTa;
    private Button btnAdd;
    private GridLayout imageGrid;
    private TextView tvThemAnh;
    private CheckBox checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6,checkbox7,checkbox8;
    private DatabaseReference databaseReference;

    private static final int PICK_IMAGE_REQUEST = 1;
    List<String> uriImage = new ArrayList<>();
    private ArrayList<Uri> imageUris = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        edPrice.setText("0");
        edDienTich.setText("0");
        getDataFromDatabase();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroupLoaiTin = findViewById(R.id.radioGroupLoaiTin);
                RadioGroup radioGroupLoaiPhong = findViewById(R.id.radioGroupLoaiPhong);
                int loaiTinChon = radioGroupLoaiTin.getCheckedRadioButtonId();
                int loaiPhongChon = radioGroupLoaiPhong.getCheckedRadioButtonId();
                RadioButton selectedRadioButtonTin = findViewById(loaiTinChon);
                RadioButton selectedRadioButtonPhong = findViewById(loaiPhongChon);
                String loaiTin = selectedRadioButtonTin.getText().toString();
                String loaiPhong = selectedRadioButtonPhong.getText().toString();
                int giaPhong = Integer.parseInt(edPrice.getText().toString());
                double  dienTich = Double.parseDouble(edDienTich.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference();
                getDataFromDatabase();
                List<String> selectedTienIch = new ArrayList<>();

                if (checkbox1.isChecked()) {
                    selectedTienIch.add(checkbox1.getText().toString());
                }

                if (checkbox2.isChecked()) {
                    selectedTienIch.add(checkbox2.getText().toString());
                }

                if (checkbox3.isChecked()) {
                    selectedTienIch.add(checkbox3.getText().toString());
                }

                if (checkbox4.isChecked()) {
                    selectedTienIch.add(checkbox4.getText().toString());
                }

                if (checkbox5.isChecked()) {
                    selectedTienIch.add(checkbox5.getText().toString());
                }

                if (checkbox6.isChecked()) {
                    selectedTienIch.add(checkbox6.getText().toString());
                }

                if (checkbox7.isChecked()) {
                    selectedTienIch.add(checkbox7.getText().toString());
                }

                if (checkbox8.isChecked()) {
                    selectedTienIch.add(checkbox8.getText().toString());
                }
                String tinh = edTinh.getText().toString();
                String huyen = edHuyen.getText().toString();
                String phuong = edPhuong.getText().toString();
                String nha = edNha.getText().toString();
                String diaChi = nha + ',' + phuong + ',' + huyen + ',' + tinh;
                String tieuDe = edTieuDe.getText().toString();
                String moTa = edMoTa.getText().toString();
                News news = new News(loaiTin,loaiPhong,giaPhong,dienTich,selectedTienIch,diaChi,tieuDe,moTa,uriImage);
                String  id = Integer.toString(news.getId());
                String newsString = news.toString();
                Log.d("NewsDetails", newsString);
                DatabaseReference usersRef = database.getReference("news");
                usersRef.child(id).setValue(news);
                startActivity(new Intent(AddActivity.this,IntroActivity.class));
            }
        });



        tvThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

//    private void addNewsToDatabase(News news) {
//        // Tạo một tham chiếu mới với ID tự động
//        DatabaseReference newRef = databaseReference.push();
//
//        // Lưu đối tượng News vào cơ sở dữ liệu với khóa (ID) tự động
//        newRef.setValue(news).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    // Thêm thành công
//                    Log.d("Database", "News added successfully.");
//                } else {
//                    // Thêm không thành công
//                    Log.w("Database", "Failed to add news.", task.getException());
//                }
//            }
//        });
//    }

    private void getDataFromDatabase() {
        // Thay 'users' và 'yourUserId' bằng tên nút và ID người dùng thực tế trong Firebase của bạn
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        // Lấy thông tin người dùng từ Firebase Realtime Database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users").child(userId);
        database.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.getValue(String.class);

                // Cập nhật TextView với tên người dùng
                EditText edName = findViewById(R.id.edLienHe);
                if (userName != null) {
                    edName.setText(userName);
                } else {
                    edName.setText("Tên người dùng không có sẵn");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi
                Log.w("DatabaseError", "loadUserName:onCancelled", databaseError.toException());
            }
        });
        database.child("sdt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.getValue(String.class);

                // Cập nhật TextView với tên người dùng
                EditText edSdt = findViewById(R.id.edSdt);
                if (userName != null) {
                    edSdt.setText(userName);
                } else {
                    edSdt.setText("Tên người dùng không có sẵn");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi
                Log.w("DatabaseError", "loadUserName:onCancelled", databaseError.toException());
            }
        });
    }

    private void initView() {
        imageGrid = findViewById(R.id.image_grid);
        tvThemAnh = findViewById(R.id.tvThemAnh);
        edPrice = findViewById(R.id.edPrice);
        edDienTich = findViewById(R.id.edDienTich);
        edTinh = findViewById(R.id.edTinh);
        edHuyen = findViewById(R.id.edHuyen);
        edPhuong = findViewById(R.id.edPhuong);
        edNha = findViewById(R.id.edNha);
        edTieuDe = findViewById(R.id.edTieuDe);
        edLienHe = findViewById(R.id.edLienHe);
        edSdt = findViewById(R.id.edSdt);
        edMoTa = findViewById(R.id.edMoTa);
        btnAdd = findViewById(R.id.btnAdd);
        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox3 = findViewById(R.id.checkbox3);
        checkbox4 = findViewById(R.id.checkbox4);
        checkbox5 = findViewById(R.id.checkbox5);
        checkbox6 = findViewById(R.id.checkbox6);
        checkbox7 = findViewById(R.id.checkbox7);
        checkbox8 = findViewById(R.id.checkbox8);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            if (imageUris.size() < 6) {
                String imageUriString = imageUri.toString();
                uriImage.add(imageUriString);
                imageUris.add(imageUri);
                addImageToGrid(imageUri);
            } else {
                Toast.makeText(this, "Tối đa 6 ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void addImageToGrid(Uri imageUri) {
        // Tạo ImageView
        ImageView imageView = new ImageView(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 500; // Độ rộng của hình ảnh
        params.height = 500; // Độ cao của hình ảnh
        params.setMargins(4, 4, 4, 4); // Khoảng cách giữa các ảnh
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Tải ảnh bằng Glide hoặc Picasso
        Glide.with(this).load(imageUri).into(imageView);

        // Thêm ImageView vào GridLayout
        imageGrid.addView(imageView);
    }
}