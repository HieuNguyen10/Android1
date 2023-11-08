package com.example.sqltest.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;

import com.example.sqltest.Activities.SuaNhaHang;
import com.example.sqltest.Database.SQLiteConnect;
import com.example.sqltest.MainActivity;
import com.example.sqltest.Models.Restaurant;
import com.example.sqltest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<Restaurant> listNH,listNHBackUp, listNHFilter;
    public RestaurantAdapter(Activity context, int resource, ArrayList<Restaurant> listNH) {
        super(context,resource);
        this.context = context;
        this.listNH = this.listNHBackUp = listNH;
        this.resource = resource;
    }

    public ArrayList<Restaurant> getListNH() {
        return listNH;
    }

    @Override
    public int getCount() {
        return this.listNH.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View customView = inflater.inflate(resource,null);

        TextView tvTenNhaHang = customView.findViewById(R.id.tvTenNhaHang);
        TextView tvDiaChi = customView.findViewById(R.id.tvDiaChiNhaHang);
        TextView tvSoDienThoai = customView.findViewById(R.id.tvSoDienThoai);
        TextView tvGioMoCua = customView.findViewById(R.id.tvGioMoCua);
        ImageView imgEdit = customView.findViewById(R.id.imgEdit);
        ImageView imgDelete = customView.findViewById(R.id.imgDelete);
        ImageView ivAnh = customView.findViewById(R.id.ivAnhNen);

        Restaurant NH = listNH.get(position);
        tvTenNhaHang.setText(NH.getTenNH());
        tvDiaChi.setText("Địa chỉ : " + NH.getDiaChiNH());
        tvSoDienThoai.setText("Hotline : " + NH.getSoDienThoai());
        tvGioMoCua.setText("Giờ mở cửa : " + NH.getOpenHour() + " - " + NH.getCloseHour());
//        ivAnh.setImageURI(Uri.parse(NH.getUrl()));

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putSerializable("NH",NH);
                Intent suaNhaHang = new Intent(context, SuaNhaHang.class);
                suaNhaHang.putExtras(data);
                context.startActivityForResult(suaNhaHang,123);
            }
        });
        
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa nhà hàng");
                builder.setMessage("Bạn có thật sự muốn xóa " + NH.getMaNH() + " - " + NH.getTenNH() + " ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String query = "DELETE FROM Restaurant WHERE key = '" + NH.getKey() + "'";
                            SQLiteConnect sqLiteConnect = new SQLiteConnect(context,
                                    context.getString(R.string.db_name), null, 1);
                            sqLiteConnect.queryData(query);
                            Toast.makeText(context, "Xóa thành công " + NH.getMaNH() + " - " + NH.getTenNH(), Toast.LENGTH_SHORT).show();
                            ((MainActivity) context).loadNhaHang();
                            dialogInterface.dismiss();
                        } catch (Exception e){
                            Log.d("Lỗi khi xóa nhà hàng !", e.toString());
                            Toast.makeText(context, "Lỗi khi xóa nhà hàng " + NH.getMaNH() + " - " + NH.getTenNH(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });

        return customView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String query = charSequence.toString().trim();
                if (query.length() < 1){
                    listNHFilter = listNHBackUp;
                } else {
                    listNHFilter = new ArrayList<>();
                    for (Restaurant Res : listNHBackUp){
                        if (Res.getTenNH().contains(query) || Res.getDiaChiNH().contains(query)){
                            listNHFilter.add(Res);
                        }
                    }
                }
                filterResults.values = listNHFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listNH = (ArrayList<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
