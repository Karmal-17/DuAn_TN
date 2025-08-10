/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_BanHang;
import java.sql.*;
/**
 *
 * @author ADMIN
 */
public class NganHang {
    private String So_TaiKhoan;
    private String Ten_NganHang;
    private String Ten_Chu_TK;
    private String Trang_Thai;
    private Date NgayTao;

    public NganHang() {
    }

    public NganHang(String So_TaiKhoan, String Ten_NganHang, String Ten_Chu_TK, String Trang_Thai, Date NgayTao) {
        this.So_TaiKhoan = So_TaiKhoan;
        this.Ten_NganHang = Ten_NganHang;
        this.Ten_Chu_TK = Ten_Chu_TK;
        this.Trang_Thai = Trang_Thai;
        this.NgayTao = NgayTao;
    }

    public String getSo_TaiKhoan() {
        return So_TaiKhoan;
    }

    public void setSo_TaiKhoan(String So_TaiKhoan) {
        this.So_TaiKhoan = So_TaiKhoan;
    }

    public String getTen_NganHang() {
        return Ten_NganHang;
    }

    public void setTen_NganHang(String Ten_NganHang) {
        this.Ten_NganHang = Ten_NganHang;
    }

    public String getTen_Chu_TK() {
        return Ten_Chu_TK;
    }

    public void setTen_Chu_TK(String Ten_Chu_TK) {
        this.Ten_Chu_TK = Ten_Chu_TK;
    }

    public String getTrang_Thai() {
        return Trang_Thai;
    }

    public void setTrang_Thai(String Trang_Thai) {
        this.Trang_Thai = Trang_Thai;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

}
