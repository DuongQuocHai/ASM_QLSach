package com.example.myapplication.Modal;

import java.io.Serializable;

public class ModalBook implements Serializable {
    String sttSach,maSach,tenSach,theLoaiSach,tacGiaSach,nhaXuatBanSach,moTaSach;
    float giaNhapSach,giaXuatSach,soLuongSach;
    public byte[] hinhSach;

    public ModalBook(String sttSach, byte[] hinhSach, String maSach, String tenSach, String theLoaiSach, String tacGiaSach, String nhaXuatBanSach, float giaNhapSach, float giaXuatSach, float soLuongSach, String moTaSach) {
        this.sttSach = sttSach;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoaiSach = theLoaiSach;
        this.tacGiaSach = tacGiaSach;
        this.nhaXuatBanSach = nhaXuatBanSach;
        this.moTaSach = moTaSach;
        this.giaNhapSach = giaNhapSach;
        this.giaXuatSach = giaXuatSach;
        this.soLuongSach = soLuongSach;
        this.hinhSach = hinhSach;
    }

    public ModalBook() {

    }

    public String getSttSach() {
        return sttSach;
    }

    public void setSttSach(String sttSach) {
        this.sttSach = sttSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoaiSach() {
        return theLoaiSach;
    }

    public void setTheLoaiSach(String theLoaiSach) {
        this.theLoaiSach = theLoaiSach;
    }

    public String getTacGiaSach() {
        return tacGiaSach;
    }

    public void setTacGiaSach(String tacGiaSach) {
        this.tacGiaSach = tacGiaSach;
    }

    public String getNhaXuatBanSach() {
        return nhaXuatBanSach;
    }

    public void setNhaXuatBanSach(String nhaXuatBanSach) {
        this.nhaXuatBanSach = nhaXuatBanSach;
    }

    public String getMoTaSach() {
        return moTaSach;
    }

    public void setMoTaSach(String moTaSach) {
        this.moTaSach = moTaSach;
    }

    public float getGiaNhapSach() {
        return giaNhapSach;
    }

    public void setGiaNhapSach(float giaNhapSach) {
        this.giaNhapSach = giaNhapSach;
    }

    public float getGiaXuatSach() {
        return giaXuatSach;
    }

    public void setGiaXuatSach(float giaXuatSach) {
        this.giaXuatSach = giaXuatSach;
    }

    public float getSoLuongSach() {
        return soLuongSach;
    }

    public void setSoLuongSach(float soLuongSach) {
        this.soLuongSach = soLuongSach;
    }

    public byte[] getHinhSach() {
        return hinhSach;
    }

    public void setHinhSach(byte[] hinhSach) {
        this.hinhSach = hinhSach;
    }
}
