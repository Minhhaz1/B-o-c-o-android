package com.example.phongtro360.model;

import java.util.List;

public class Room {
    private int id;
    private String loaiPhong,diaChi;
    private double price,dienTich;
    private List<String> tienIch;

    public Room() {
    }

    public Room(int id, String loaiPhong, String diaChi, double price, double dienTich, List<String> tienIch) {
        this.id = id;
        this.loaiPhong = loaiPhong;
        this.diaChi = diaChi;
        this.price = price;
        this.dienTich = dienTich;
        this.tienIch = tienIch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
