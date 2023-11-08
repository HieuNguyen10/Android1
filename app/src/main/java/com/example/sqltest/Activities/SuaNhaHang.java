package com.example.sqltest.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sqltest.Database.SQLiteConnect;
import com.example.sqltest.Models.Restaurant;
import com.example.sqltest.R;

import java.util.Date;

public class SuaNhaHang extends AppCompatActivity {
    EditText edtMNH,edtTNH,edtDCNH,edtMTNH,edtSDT;
    TimePicker tpOpenHour, tpCloseHour;
    Button btnLTD,btnHB,btnChonAnh;
    ImageView ivPic;
    String anh ="";

    ActivityResultLauncher chonAnhLancher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                    ivPic.setImageURI(o);
                    anh += o;
                    Toast.makeText(SuaNhaHang.this, o+"", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nha_hang);

        // Ánh xạ
        edtMNH = findViewById(R.id.edtMNH);
        edtTNH = findViewById(R.id.edtTNH);
        edtDCNH = findViewById(R.id.edtDCNH);
        edtMTNH = findViewById(R.id.edtMTNH);
        edtSDT = findViewById(R.id.edtSDT);
        tpOpenHour = findViewById(R.id.tpOpenHour);
        tpCloseHour = findViewById(R.id.tpCloseHour);
        btnLTD = findViewById(R.id.btnLTD);
        btnHB = findViewById(R.id.btnHB);
        ivPic = findViewById(R.id.ivPic);
        btnChonAnh = findViewById(R.id.btnChonAnh);

        // Phần valid
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        Restaurant Res = (Restaurant) data.get("NH");

        edtMNH.setText(Res.getMaNH());
        edtTNH.setText(Res.getTenNH());
        edtDCNH.setText(Res.getDiaChiNH());
        edtMTNH.setText(Res.getMoTaNH());
        edtSDT.setText(Res.getSoDienThoai());
//        ivPic.setImageURI(Uri.parse(Res.getUrl()));
        String OpenHour = Res.getOpenHour().toString().trim();

        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnhLancher.launch("image/*");
            }
        });
        int HourOpen = 0;
        int MinuteOpen = 0;
        for (int i = 0; i < OpenHour.length(); i++){
            if (OpenHour.charAt(i) == ':'){
                HourOpen = Integer.parseInt(OpenHour.substring(0,i));
                MinuteOpen = Integer.parseInt(OpenHour.substring(i+1,OpenHour.length()));
            }
        }

        tpOpenHour.setHour(HourOpen);
        tpOpenHour.setMinute(MinuteOpen);

        String CloseHour = Res.getCloseHour().toString().trim();
        int HourClose = 0;
        int MinuteClose = 0;
        for (int i = 0; i < CloseHour.length(); i++){
            if (CloseHour.charAt(i) == ':'){
                HourClose = Integer.parseInt(CloseHour.substring(0,i));
                MinuteClose = Integer.parseInt(CloseHour.substring(i+1,CloseHour.length()));
            }
        }

        tpCloseHour.setHour(HourClose);
        tpCloseHour.setMinute(MinuteClose);

        btnHB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLTD.setOnClickListener(new View.OnClickListener() {
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
                    String CommitTime = new Date().toString().trim();

                    String query = "Update Restaurant " +
                            "set MaNhaHang = '" + MNH + "', " +
                            "TenNhaHang = '" + TNH + "'," +
                            "DiaChiNhaHang = '" + DCNH + "'," +
                            "MoTaNhaHang = '" + MTNH + "'," +
                            "OpenHour = '" + OH +"'," +
                            "CloseHour = '" + CH + "'," +
                            "SoDienThoai = '" + SDT + "'," +
                            "CommitTime = '" + CommitTime + "'," +
                            "Url = '" + anh + "'" +
                            "WHERE key = '" + Res.getKey() +"';";
                    SQLiteConnect sqLiteConnect = new SQLiteConnect(getBaseContext(),getString(R.string.db_name),null,1);
                    sqLiteConnect.queryData(query);

                    Toast.makeText(SuaNhaHang.this, "Sửa nhà hàng " + MNH + " - " + TNH , Toast.LENGTH_SHORT).show();
                    setResult(123);
                    finish();
                } catch (Exception e){
                    Log.d("Lỗi update CSDL", e.toString());
                    Toast.makeText(getBaseContext(), "Lỗi update CSDL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}