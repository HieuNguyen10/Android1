package com.example.sqltest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqltest.Models.Restaurant;
import com.example.sqltest.R;

public class ChiTietNhaHang extends AppCompatActivity {
    TextView tvMNH,tvTNH,tvMTNH,tvDCNH, tvSDT, tvGMC;
    Button btnTL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nha_hang);

        // Đoạn ánh xạ ~
        tvMNH = findViewById(R.id.tvMNH);
        tvTNH = findViewById(R.id.tvTNH);
        tvDCNH = findViewById(R.id.tvDCNH);
        tvMTNH = findViewById(R.id.tvMTNH);
        tvSDT = findViewById(R.id.tvSDT);
        tvGMC = findViewById(R.id.tvGMC);
        btnTL = findViewById(R.id.btnTL);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        Restaurant Res = (Restaurant) data.get("nhaHang");

        tvMNH.setText(tvMNH.getText() + Res.getMaNH());
        tvTNH.setText(tvTNH.getText() + Res.getTenNH());
        tvDCNH.setText(tvDCNH.getText() + Res.getDiaChiNH());
        tvMTNH.setText(tvMTNH.getText() + Res.getMoTaNH());
        tvSDT.setText(tvSDT.getText() + Res.getSoDienThoai());
        tvGMC.setText(tvGMC.getText() + String.valueOf(Res.getOpenHour()) + " - " + String.valueOf(Res.getCloseHour()));

        btnTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}