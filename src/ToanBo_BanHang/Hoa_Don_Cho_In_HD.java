/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_BanHang;

import ToanBo_KhachHang.KhachHang_3_O_In_HD;
import ToanBo_TaiKhoan.Tai_Khoan_3_O_In_HD;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Hoa_Don_Cho_In_HD {

    private String Ma_HoaDon;
    private LocalDateTime ThoiGian_In_HD;
    private KhachHang_3_O_In_HD KhachHang_In_HD;
    private List<ChiTietHoaDon> ChiTietHoaDon_In_HD;
    private float Giam_Gia;
    private Tai_Khoan_3_O_In_HD TaiKhoan_In_HD;
    private String DiaChi;
    private String SoDienThoai_LienHe;
    private String LinkAnh_Logo;

    public Hoa_Don_Cho_In_HD() {
    }

    public Hoa_Don_Cho_In_HD(String Ma_HoaDon, LocalDateTime ThoiGian_In_HD, KhachHang_3_O_In_HD KhachHang_In_HD, List<ChiTietHoaDon> ChiTietHoaDon_In_HD, float Giam_Gia, Tai_Khoan_3_O_In_HD TaiKhoan_In_HD, String DiaChi, String SoDienThoai_LienHe, String LinkAnh_Logo) {
        this.Ma_HoaDon = Ma_HoaDon;
        this.ThoiGian_In_HD = ThoiGian_In_HD;
        this.KhachHang_In_HD = KhachHang_In_HD;
        this.ChiTietHoaDon_In_HD = ChiTietHoaDon_In_HD;
        this.Giam_Gia = Giam_Gia;
        this.TaiKhoan_In_HD = TaiKhoan_In_HD;
        this.DiaChi = DiaChi;
        this.SoDienThoai_LienHe = SoDienThoai_LienHe;
        this.LinkAnh_Logo = LinkAnh_Logo;
    }

    // Getters
    public String getMa_HoaDon() {
        return Ma_HoaDon;
    }

    public void setMa_HoaDon(String Ma_HoaDon) {
        this.Ma_HoaDon = Ma_HoaDon;
    }

    public LocalDateTime getThoiGian_In_HD() {
        return ThoiGian_In_HD;
    }

    public void setThoiGian_In_HD(LocalDateTime ThoiGian_In_HD) {
        this.ThoiGian_In_HD = ThoiGian_In_HD;
    }

    public KhachHang_3_O_In_HD getKhachHang_In_HD() {
        return KhachHang_In_HD;
    }

    public void setKhachHang_In_HD(KhachHang_3_O_In_HD KhachHang_In_HD) {
        this.KhachHang_In_HD = KhachHang_In_HD;
    }

    public List<ChiTietHoaDon> getChiTietHoaDon_In_HD() {
        return ChiTietHoaDon_In_HD;
    }

    public void setChiTietHoaDon_In_HD(List<ChiTietHoaDon> ChiTietHoaDon_In_HD) {
        this.ChiTietHoaDon_In_HD = ChiTietHoaDon_In_HD;
    }

    public float getGiam_Gia() {
        return Giam_Gia;
    }

    public void setGiam_Gia(float Giam_Gia) {
        this.Giam_Gia = Giam_Gia;
    }

    public Tai_Khoan_3_O_In_HD getTaiKhoan_In_HD() {
        return TaiKhoan_In_HD;
    }

    public void setTaiKhoan_In_HD(Tai_Khoan_3_O_In_HD TaiKhoan_In_HD) {
        this.TaiKhoan_In_HD = TaiKhoan_In_HD;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSoDienThoai_LienHe() {
        return SoDienThoai_LienHe;
    }

    public void setSoDienThoai_LienHe(String SoDienThoai_LienHe) {
        this.SoDienThoai_LienHe = SoDienThoai_LienHe;
    }

    public String getLinkAnh_Logo() {
        return LinkAnh_Logo;
    }

    public void setLinkAnh_Logo(String LinkAnh_Logo) {
        this.LinkAnh_Logo = LinkAnh_Logo;
    }
}
