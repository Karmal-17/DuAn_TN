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

    private String Ma_SP;
    private String Ten_SP;
    private float DonGia;

    public SanPham_3_O_In_HD() {
    }

    public SanPham_3_O_In_HD(String Ma_SP, String Ten_SP, float DonGia) {
        this.Ma_SP = Ma_SP;
        this.Ten_SP = Ten_SP;
        this.DonGia = DonGia;
    }

    // Getters
    public String getMa_SP() {
        return Ma_SP;
    }

    public String getTen_SP() {
        return Ten_SP;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setMa_SP(String Ma_SP) {
        this.Ma_SP = Ma_SP;
    }

    public void setTen_SP(String Ten_SP) {
        this.Ten_SP = Ten_SP;
    }

    public void setDonGia(float DonGia) {
        this.DonGia = DonGia;
    }
    
    
}
