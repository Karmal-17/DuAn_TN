/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_SanPham;

/**
 *
 * @author ADMIN
 */
public class SanPham_3_O {

    private String Ten_SP;

    private float DonGia_SP;
    private int SoLuong_SP;

    public SanPham_3_O() {
    }

    public SanPham_3_O(String Ten_SP, float DonGia_SP, int SoLuong_SP) {
        this.Ten_SP = Ten_SP;
        this.DonGia_SP = DonGia_SP;
        this.SoLuong_SP = SoLuong_SP;
    }

    public int getSoLuong_SP() {
        return SoLuong_SP;
    }

    public void setSoLuong_SP(int SoLuong_SP) {
        this.SoLuong_SP = SoLuong_SP;
    }

    public String getTen_SP() {
        return Ten_SP;
    }

    public void setTen_SP(String Ten_SP) {
        this.Ten_SP = Ten_SP;
    }

    public float getDonGia_SP() {
        return DonGia_SP;
    }

    public void setDonGia_SP(float DonGia_SP) {
        this.DonGia_SP = DonGia_SP;
    }

}
