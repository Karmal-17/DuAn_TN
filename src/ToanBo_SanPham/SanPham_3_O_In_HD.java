/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_SanPham;

/**
 *
 * @author ADMIN
 */
public class SanPham_3_O_In_HD {
    private String Ma_SanPham;
    private String Ten_SanPham;
    private double DonGia_SanPham;

    public SanPham_3_O_In_HD() {
    }

    public SanPham_3_O_In_HD(String Ma_SanPham, String Ten_SanPham, double DonGia_SanPham) {
        this.Ma_SanPham = Ma_SanPham;
        this.Ten_SanPham = Ten_SanPham;
        this.DonGia_SanPham = DonGia_SanPham;
    }

    public String getMa_SanPham() {
        return Ma_SanPham;
    }

    public void setMa_SanPham(String Ma_SanPham) {
        this.Ma_SanPham = Ma_SanPham;
    }

    public String getTen_SanPham() {
        return Ten_SanPham;
    }

    public void setTen_SanPham(String Ten_SanPham) {
        this.Ten_SanPham = Ten_SanPham;
    }

    public double getDonGia_SanPham() {
        return DonGia_SanPham;
    }

    public void setDonGia_SanPham(double DonGia_SanPham) {
        this.DonGia_SanPham = DonGia_SanPham;
    }

}
