package ToanBo_BanHang;

import ToanBo_KhachHang.KhachHang_3_O_In_HD;
import ToanBo_TaiKhoan.Tai_Khoan_3_O_In_HD;
import java.time.LocalDateTime;
import java.util.List;

public class Hoa_Don_Cho_In_HD {

    private String Ma_HD;
    private LocalDateTime ThoiGian_In_HD;
    private KhachHang_3_O_In_HD khachHang;
    private List<ChiTiet_HoaDon_2_O_In_HD> chiTietHoaDon;
    private float GiamGia;
    private Tai_Khoan_3_O_In_HD taiKhoan;
    private String diaChi;
    private String SoDienThoaiLienHe;
    private String LinkAnh_Logo;

    // ✅ Các thuộc tính bổ sung để lưu trữ nếu cần
    private int tongSoLuongSanPham;
    private double tongTien;
    private double thanhTienSauGiam;

    public Hoa_Don_Cho_In_HD() {
    }

    public Hoa_Don_Cho_In_HD(String Ma_HD, LocalDateTime ThoiGian_In_HD, KhachHang_3_O_In_HD Khach_Hang,
            List<ChiTiet_HoaDon_2_O_In_HD> ChiTiet_HD_In_HD, float GiamGia, Tai_Khoan_3_O_In_HD TaiKhoan,
            String DiaChi, String SoDienThoai_LienHe, String LinkAnh_Logo) {
        this.Ma_HD = Ma_HD;
        this.ThoiGian_In_HD = ThoiGian_In_HD;
        this.khachHang = Khach_Hang;
        this.chiTietHoaDon = ChiTiet_HD_In_HD;
        this.GiamGia = GiamGia;
        this.taiKhoan = TaiKhoan;
        this.diaChi = DiaChi;
        this.SoDienThoaiLienHe = SoDienThoai_LienHe;
        this.LinkAnh_Logo = LinkAnh_Logo;
    }

    // Getters & Setters
    public String getMa_HD() {
        return Ma_HD;
    }

    public void setMa_HD(String Ma_HD) {
        this.Ma_HD = Ma_HD;
    }

    public LocalDateTime getThoiGianIn() {
        return ThoiGian_In_HD;
    }

    public void setThoiGian_In_HD(LocalDateTime ThoiGianIn_HD) {
        this.ThoiGian_In_HD = ThoiGianIn_HD;
    }

    public KhachHang_3_O_In_HD getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang_3_O_In_HD khachHang) {
        this.khachHang = khachHang;
    }

    public List<ChiTiet_HoaDon_2_O_In_HD> getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(List<ChiTiet_HoaDon_2_O_In_HD> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

    public float getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(float GiamGia) {
        this.GiamGia = GiamGia;
    }

    public Tai_Khoan_3_O_In_HD getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(Tai_Khoan_3_O_In_HD taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoaiLienHe() {
        return SoDienThoaiLienHe;
    }

    public void setSoDienThoaiLienHe(String soDienThoaiLienHe) {
        this.SoDienThoaiLienHe = soDienThoaiLienHe;
    }

    public String getLinkAnhLogo() {
        return LinkAnh_Logo;
    }

    public void setLinkAnhLogo(String linkAnhLogo) {
        this.LinkAnh_Logo = linkAnhLogo;
    }

    // ✅ Tổng số lượng sản phẩm (tính động)
    public int getTongSoLuongSanPham() {
        return chiTietHoaDon.stream().mapToInt(ChiTiet_HoaDon_2_O_In_HD::getSoLuong).sum();
    }

    // ✅ Setter nếu muốn lưu riêng
    public void setTongSoLuongSanPham(int tongSoLuongSanPham) {
        this.tongSoLuongSanPham = tongSoLuongSanPham;
    }

    // ✅ Tổng tiền trước giảm giá (tính động)
    public double getTongTien() {
        return chiTietHoaDon.stream().mapToDouble(ChiTiet_HoaDon_2_O_In_HD::getThanhTien).sum();
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    // ✅ Thành tiền sau giảm giá (tính động)
    public double getThanhTienSauGiam() {
        return getTongTien() - GiamGia;
    }

    public void setThanhTienSauGiam(double thanhTienSauGiam) {
        this.thanhTienSauGiam = thanhTienSauGiam;
    }
}
