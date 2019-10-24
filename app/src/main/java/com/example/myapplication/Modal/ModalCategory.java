package com.example.myapplication.Modal;

import java.io.Serializable;

public class ModalCategory implements Serializable {
    public String MaTheLoai,TenTheLoai,ViTriTheLoai,sttTl;

    public ModalCategory(String maTheLoai, String tenTheLoai, String viTriTheLoai, String sttTl) {
        MaTheLoai = maTheLoai;
        TenTheLoai = tenTheLoai;
        ViTriTheLoai = viTriTheLoai;
        this.sttTl = sttTl;
    }

    public String getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    public String getViTriTheLoai() {
        return ViTriTheLoai;
    }

    public void setViTriTheLoai(String viTriTheLoai) {
        ViTriTheLoai = viTriTheLoai;
    }

    public String getSttTl() {
        return sttTl;
    }

    public void setSttTl(String sttTl) {
        this.sttTl = sttTl;
    }
}
