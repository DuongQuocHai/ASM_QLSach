package com.example.myapplication.Modal;

public class ModalBill {
    String maBill, ngayBill,maSach ;
    int id,soLuongSachMua;
    float thanhTienBill;

    public ModalBill(String maBill, String ngayBill, String maSach, int id, int soLuongSachMua, float thanhTienBill) {
        this.maBill = maBill;
        this.ngayBill = ngayBill;
        this.maSach = maSach;
        this.id = id;
        this.soLuongSachMua = soLuongSachMua;
        this.thanhTienBill = thanhTienBill;
    }

    public String getMaBill() {
        return maBill;
    }

    public void setMaBill(String maBill) {
        this.maBill = maBill;
    }

    public String getNgayBill() {
        return ngayBill;
    }

    public void setNgayBill(String ngayBill) {
        this.ngayBill = ngayBill;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuongSachMua() {
        return soLuongSachMua;
    }

    public void setSoLuongSachMua(int soLuongSachMua) {
        this.soLuongSachMua = soLuongSachMua;
    }

    public float getThanhTienBill() {
        return thanhTienBill;
    }

    public void setThanhTienBill(float thanhTienBill) {
        this.thanhTienBill = thanhTienBill;
    }
}
