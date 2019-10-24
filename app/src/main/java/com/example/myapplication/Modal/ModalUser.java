package com.example.myapplication.Modal;

import java.io.Serializable;

public class ModalUser implements Serializable {
    public String MaUser,TenUser,ViTriUser,TenDNUser,PassUser,DiaChiUser,EmaiUser,SDTUser;
    public byte[] HinhUser;

    public ModalUser(String maUser, String tenUser, String viTriUser, String tenDNUser, String passUser, String diaChiUser, String emaiUser, String SDTUser, byte[] hinhUser) {
        MaUser = maUser;
        TenUser = tenUser;
        ViTriUser = viTriUser;
        TenDNUser = tenDNUser;
        PassUser = passUser;
        DiaChiUser = diaChiUser;
        EmaiUser = emaiUser;
        this.SDTUser = SDTUser;
        HinhUser = hinhUser;
    }

    public String getMaUser() {
        return MaUser;
    }

    public void setMaUser(String maUser) {
        MaUser = maUser;
    }

    public String getTenUser() {
        return TenUser;
    }

    public void setTenUser(String tenUser) {
        TenUser = tenUser;
    }

    public String getViTriUser() {
        return ViTriUser;
    }

    public void setViTriUser(String viTriUser) {
        ViTriUser = viTriUser;
    }

    public String getTenDNUser() {
        return TenDNUser;
    }

    public void setTenDNUser(String tenDNUser) {
        TenDNUser = tenDNUser;
    }

    public String getPassUser() {
        return PassUser;
    }

    public void setPassUser(String passUser) {
        PassUser = passUser;
    }

    public String getDiaChiUser() {
        return DiaChiUser;
    }

    public void setDiaChiUser(String diaChiUser) {
        DiaChiUser = diaChiUser;
    }

    public String getEmaiUser() {
        return EmaiUser;
    }

    public void setEmaiUser(String emaiUser) {
        EmaiUser = emaiUser;
    }

    public String getSDTUser() {
        return SDTUser;
    }

    public void setSDTUser(String SDTUser) {
        this.SDTUser = SDTUser;
    }

    public byte[] getHinhUser() {
        return HinhUser;
    }

    public void setHinhUser(byte[] hinhUser) {
        HinhUser = hinhUser;
    }
}
