package com.example.sqltest.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sqltest.Database.SQLiteConnect;
import com.example.sqltest.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class ThemMoiNhaHang extends AppCompatActivity {

    EditText edtMNH,edtTNH,edtDCNH,edtMTNH,edtSDT;
    TimePicker tpOpenHour, tpCloseHour;
    Button btnXN, btnTL, btnChonAnh;
    ImageView ivPic;
    Uri anh;

    ActivityResultLauncher chonAnhLancher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                    ivPic.setImageURI(o);
                    anh =o;
                    Toast.makeText(ThemMoiNhaHang.this, o+"", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_moi_nha_hang);

        // Ánh xạ sang
        edtMNH = findViewById(R.id.edtMNH);
        edtTNH = findViewById(R.id.edtTNH);
        edtDCNH = findViewById(R.id.edtDCNH);
        edtMTNH = findViewById(R.id.edtMTNH);
        edtSDT = findViewById(R.id.edtSDT);
        tpOpenHour = findViewById(R.id.tpOpenHour);
        tpCloseHour = findViewById(R.id.tpCloseHour);
        btnXN = findViewById(R.id.btnXN);
        btnTL = findViewById(R.id.btnTL);
        ivPic = findViewById(R.id.ivPic);
        btnChonAnh = findViewById(R.id.btnChonAnh);

        btnChonAnh.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                chonAnhLancher.launch("image/*");
            }
        });


        // Phần code
        btnTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String MNH = edtMNH.getText().toString().trim();
                    String TNH = edtTNH.getText().toString().trim();
                    String DCNH = edtDCNH.getText().toString().trim();
                    String MTNH = edtMTNH.getText().toString().trim();
                    String OH = tpOpenHour.getHour() + ":" + tpOpenHour.getMinute();
                    String CH = tpCloseHour.getHour() + ":" + tpCloseHour.getMinute();
                    String SDT = edtSDT.getText().toString().trim();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                    String CommitTime = new Date("Y/M/d H:m:s").toString().trim();
//                    Date CommitTime = dateFormat.parse(new Date().toString());
                    String CommitTime = new Date().toString().trim();
                    Bitmap photo = MediaStore.Images.Media.getBitmap(ThemMoiNhaHang.this.getContentResolver(),anh);
                    String query = "INSERT INTO Restaurant(MaNhaHang, TenNhaHang, DiaChiNhaHang, " +
                            "MoTaNhaHang, OpenHour, CloseHour,SoDienThoai,CommitTime,Url) " +
                            "VALUES ('" + MNH + "','" + TNH + "','"+DCNH+"'," +
                            "'"+MTNH+"','"+OH+"','"+CH+"','"+SDT+"','"+CommitTime+"','"+anh+"')";
                    SQLiteConnect sqLiteConnect = new SQLiteConnect(getBaseContext(), getString(R.string.db_name),null,1);
                    sqLiteConnect.queryData(query);
                    Toast.makeText(ThemMoiNhaHang.this, "Thêm " + MNH + " thành công ~", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } catch (Exception e){
                    Log.d("Lỗi insert cơ sở dữ liệu", e.toString());
                    Toast.makeText(ThemMoiNhaHang.this, "Lỗi insert Cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}