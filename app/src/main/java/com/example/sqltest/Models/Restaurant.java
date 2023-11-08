package com.example.sqltest.Models;

import java.io.Serializable;
import java.util.Date;

public class Restaurant implements Serializable {
    int Key;
    String MaNH;
    String TenNH;
    String DiaChiNH;
    String MoTaNH;
    String OpenHour,CloseHour;
    String SoDienThoai;
    String CommitTime;
    String Url;

    public Restaurant(){}

    public Restaurant(int key, String maNH, String tenNH, String diaChiNH, String moTaNH, String openHour, String closeHour,String soDienThoai, String commitTime, String url) {
        Key = key;
        MaNH = maNH;
        TenNH = tenNH;
        DiaChiNH = diaChiNH;
        MoTaNH = moTaNH;
        OpenHour = openHour;
        CloseHour = closeHour;
        CommitTime = commitTime;
        SoDienThoai = soDienThoai;
        Url = url;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public int getKey() {
        return Key;
    }

    public String getMaNH() {
        return MaNH;
    }

    public String getTenNH() {
        return TenNH;
    }

    public String getDiaChiNH() {
        return DiaChiNH;
    }

    public String getMoTaNH() {
        return MoTaNH;
    }

    public String getOpenHour() {
        return OpenHour;
    }

    public String getCloseHour() {
        return CloseHour;
    }

    public void setKey(int key) {
        Key = key;
    }

    public void setMaNH(String maNH) {
        MaNH = maNH;
    }

    public void setTenNH(String tenNH) {
        TenNH = tenNH;
    }

    public void setDiaChiNH(String diaChiNH) {
        DiaChiNH = diaChiNH;
    }

    public void setMoTaNH(String moTaNH) {
        MoTaNH = moTaNH;
    }

    public void setOpenHour(String openHour) {
        OpenHour = openHour;
    }

    public void setCloseHour(String closeHour) {
        CloseHour = closeHour;
    }

    public String getCommitTime() {
        return CommitTime;
    }

    public void setCommitTime(String commitTime) {
        this.CommitTime = commitTime;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
