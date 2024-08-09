package com.example.phongtro360.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class News implements Serializable {
    private static int id = 0;
    private String loaiTin;
    private String loaiPhong;
    private int giaPhong;
    private double dienTich;
    private boolean like = false;
    private List<String> tienIch;
    private String diaChi;
    private String tieuDe;
    private String lienHe;
    private String sdt;
    private String moTa;
    private List<String> imageUris;

    public News() {
    }

    public News( String loaiTin, String loaiPhong, int giaPhong, double dienTich, List<String> tienIch, String diaChi, String tieuDe,  String moTa, List<String> imageUris) {

        this.loaiTin = loaiTin;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.dienTich = dienTich;
        this.tienIch = tienIch;
        this.diaChi = diaChi;
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.imageUris = imageUris;
        this.id = getId()+1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiTin() {
        return loaiTin;
    }

    public void setLoaiTin(String loaiTin) {
        this.loaiTin = loaiTin;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        this.giaPhong = giaPhong;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public List<String> getTienIch() {
        return tienIch;
    }

    public void setTienIch(List<String> tienIch) {
        this.tienIch = tienIch;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getLienHe() {
        return lienHe;
    }

    public void setLienHe(String lienHe) {
        this.lienHe = lienHe;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public List<String> getImageUris() {
        return imageUris;
    }

    public void setImageUris(List<String> imageUris) {
        this.imageUris = imageUris;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                "like"+ like +
                ", loaiTin='" + loaiTin + '\'' +
                ", loaiPhong='" + loaiPhong + '\'' +
                ", giaPhong=" + giaPhong +
                ", dienTich=" + dienTich +
                ", selectedTienIch=" + tienIch +
                ", diaChi='" + diaChi + '\'' +
                ", tieuDe='" + tieuDe + '\'' +
                ", moTa='" + moTa + '\'' +
                ", uriImage='" + imageUris + '\'' +
                '}';
    }
}
