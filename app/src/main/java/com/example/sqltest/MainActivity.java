package com.example.sqltest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sqltest.Activities.ChiTietNhaHang;
import com.example.sqltest.Activities.ThemMoiNhaHang;
import com.example.sqltest.Adapter.RestaurantAdapter;
import com.example.sqltest.Database.SQLiteConnect;
import com.example.sqltest.Models.Restaurant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Cac thu vien de su dung thoi gian
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SQLiteConnect sqLiteConnect;
    ListView lvRestaurant;
    ArrayList<Restaurant> listNH;
    RestaurantAdapter NHAdapter;
    private static final int RESULT_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickrequestPermisson();
        // Ánh xạ
        lvRestaurant = findViewById(R.id.lvRestaurant);
        listNH = new ArrayList<>();
        sqLiteConnect = new SQLiteConnect(getBaseContext(),getString(R.string.db_name),null,1);

        String query = "CREATE TABLE IF NOT EXISTS Restaurant(" +
                "key integer primary key autoincrement unique, " +
                "MaNhaHang varchar not null, " +
                "TenNhaHang varchar not null, " +
                "DiaChiNhaHang varchar, " +
                "MoTaNhaHang varchar, " +
                "OpenHour varchar, " +
                "CloseHour varchar, " +
                "SoDienThoai varchar not null, " +
                "CommitTime string, " +
                "Url string );";
        sqLiteConnect.queryData(query);

//        query = "INSERT INTO Restaurant(MaNhaHang, TenNhaHang, DiaChiNhaHang, " +
//                "MoTaNhaHang, OpenHour , CloseHour,SoDienThoai,CommitTime) " +
//                "VALUES ('NH001','Nhà hàng này víp','50A ngõ 9 đào tấn'," +
//                "'Không có mô tả','8','24','0379273403','2023-10-31 15:15:15')";
//
//        sqLiteConnect.queryData(query);

//        query = "DROP TABLE RESTAURANT";
//        sqLiteConnect.queryData(query);

        RestaurantAdapter NHAdapter = new RestaurantAdapter(MainActivity.this, R.layout.lv_nhahang, listNH);
        lvRestaurant.setAdapter(NHAdapter);

        loadNhaHang();

        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent xemChiTiet = new Intent(MainActivity.this, ChiTietNhaHang.class);
                Bundle data = new Bundle();
                Restaurant nhaHang = NHAdapter.getListNH().get(i);
                data.putSerializable("nhaHang", nhaHang);
                xemChiTiet.putExtras(data);
                startActivity(xemChiTiet);
            }
        });
    }


    private void  clickrequestPermisson()
    {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Acces", Toast.LENGTH_SHORT).show();
        }else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,RESULT_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RESULT_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Cho phep", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "K Cho phep", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void loadNhaHang(){
        String query = "SELECT * FROM Restaurant";
        Cursor data = sqLiteConnect.getData(query);
        listNH.clear();
        while (data.moveToNext()){
            try {
            int key = data.getInt(0);
            String MNH = data.getString(1);
            String TNH = data.getString(2);
            String DCNH = data.getString(3);
            String MTNH = data.getString(4);
            String OH = data.getString(5);
            String CH = data.getString(6);
            String SDT = data.getString(7);
            String CT = data.getString(8);
            String pic = data.getString(9);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date CommitT = dateFormat.parse(CT);
            Restaurant Res = new Restaurant(key,MNH,TNH,DCNH,MTNH,OH,CH,SDT,CT,pic);
            listNH.add(Res);
            } catch (Exception e){
                Log.d("Lỗi đọc dữ liệu",e.toString());
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        NHAdapter = new RestaurantAdapter(MainActivity.this, R.layout.lv_nhahang, listNH);
        lvRestaurant.setAdapter(NHAdapter);
    }

    ActivityResultLauncher addNewRestaurant = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        loadNhaHang();
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);

        MenuItem menuThemMoiNhaHang = menu.findItem(R.id.ThemMoiNhaHang);
        menuThemMoiNhaHang.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent themMoiNhaHang = new Intent(getBaseContext(), ThemMoiNhaHang.class);
                addNewRestaurant.launch(themMoiNhaHang);
                return false;
            }
        });

        MenuItem menuSearch = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                NHAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                NHAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == 123){
            loadNhaHang();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}