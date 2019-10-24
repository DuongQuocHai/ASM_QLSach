package com.example.myapplication.Modal;

public class ModalAddBill {
    float thanhtien,dongia;
    int soluong;
    String masach;

    public ModalAddBill(float thanhtien, float dongia, int soluong, String masach) {
        this.thanhtien = thanhtien;
        this.dongia = dongia;
        this.soluong = soluong;
        this.masach = masach;
    }

    public float getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(float thanhtien) {
        this.thanhtien = thanhtien;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }
}
