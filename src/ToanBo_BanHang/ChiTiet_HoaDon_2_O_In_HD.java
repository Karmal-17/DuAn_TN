/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_BanHang;

import ToanBo_SanPham.SanPham_3_O_In_HD;

/**
 *
 * @author ADMIN
 */
public class ChiTiet_HoaDon_2_O_In_HD {
    private SanPham_3_O_In_HD SanPham;
    private int SoLuong;

    public ChiTiet_HoaDon_2_O_In_HD() {
    }

    public ChiTiet_HoaDon_2_O_In_HD(SanPham_3_O_In_HD SanPham, int SoLuong) {
        this.SanPham = SanPham;
        this.SoLuong = SoLuong;
    }

    public SanPham_3_O_In_HD getSanPham() {
        return SanPham;
    }

    public void setSanPham(SanPham_3_O_In_HD SanPham) {
        this.SanPham = SanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

   
}

