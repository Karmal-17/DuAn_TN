/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_ThongKe;

import DBConnect.MyConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class QL_DoanhThu {

    MyConnection conn;

    public QL_DoanhThu() {
        conn = new MyConnection();
    }

    public float layTongDoanhThuHomNay() {
        float tongDoanhThu = 0f;

        String sql = "SELECT SUM(THANHTIEN) AS DOANHTU_HOMNAY "
                + "FROM THANHTOAN "
                + "WHERE CAST(NGAYTT AS DATE) = CAST(GETDATE() AS DATE)";

        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tongDoanhThu = rs.getFloat("DOANHTU_HOMNAY");
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi lấy doanh thu hôm nay: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_DoanhThu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tongDoanhThu;
    }

    public int layTongSanPhamTheoNgay(Date NgayChon) {
        int Tong_SoLuong = 0;
        Connection conect = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conect = conn.DBConnect(); // Lấy kết nối từ helper

            String sql = "SELECT SUM(SOLUONG) FROM CTHOADON  \n"
                    + "             JOIN HOADON ON CTHOADON.MA_HD = HOADON.MA_HD \n"
                    + "             WHERE CAST(HOADON.NGAYLAP AS DATE) = ?";
            stmt = conect.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(NgayChon.getTime()));

            rs = stmt.executeQuery();
            if (rs.next()) {
                Tong_SoLuong = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Log lỗi hoặc xử lý exception riêng
        } finally {
        }
        return Tong_SoLuong;
    }

    public List<SanPhamBanChay> laySanPhamBanChayTheoThoiGian(Date ngayBatDau, Date ngayKetThuc) {
        List<SanPhamBanChay> danhSachSP = new ArrayList<>();
        String sql = "SELECT \n"
                + "    sp.TENSP,\n"
                + "    SUM(cthd.SOLUONG) AS SoLuong,\n"
                + "    SUM(cthd.SOLUONG * sp.DONGIA) AS ThanhTien,\n"
                + "    sp.HINHANH AS AnhSP\n"
                + "FROM SANPHAM sp\n"
                + "JOIN CTHOADON cthd ON sp.MA_SP = cthd.MA_SP\n"
                + "JOIN HOADON hd ON cthd.MA_HD = hd.MA_HD\n"
                + "WHERE hd.NGAYLAP BETWEEN ? AND ?\n"
                + "GROUP BY sp.TENSP, sp.HINHANH\n"
                + "ORDER BY SUM(cthd.SOLUONG) DESC";

        try {
            Connection conect = conn.DBConnect();
            PreparedStatement pstmt = conect.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(ngayBatDau.getTime()));
            pstmt.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SanPhamBanChay sp = new SanPhamBanChay();
                sp.setTen_SP(rs.getString("TENSP"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setThanhTien(rs.getFloat("ThanhTien"));
                sp.setAnh_SP(rs.getString("AnhSP")); // Nếu ảnh là dạng URL/text
                danhSachSP.add(sp);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_DoanhThu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return danhSachSP;
    }

    public float tinhTongLoiNhuanTrongNgayHomNay() {
        float tongLoiNhuan = 0;
        String sql = "SELECT SUM(cthd.SOLUONG * (sp.DONGIA * 0.3)) AS LoiNhuanTrongNgay\n"
                + "                FROM SANPHAM sp\n"
                + "                JOIN CTHOADON cthd ON sp.MA_SP = cthd.MA_SP\n"
                + "                JOIN HOADON hd ON cthd.MA_HD = hd.MA_HD\n"
                + "                WHERE CONVERT(DATE, hd.NGAYLAP) = CONVERT(DATE, GETDATE())";

        try {
            Connection conect = conn.DBConnect();
            PreparedStatement stmt = conect.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                float giaBan = rs.getInt("LoiNhuanTrongNgay");
                tongLoiNhuan += giaBan; // 20% mỗi sản phẩm
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
        }

        return tongLoiNhuan;
    }

    public List<BieuDo_3_O> layThongKeTheoNgay(Date Ngay_BD, Date Ngay_KT) {
        List<BieuDo_3_O> danhSach = new ArrayList<>();
        String sql = """
        SELECT 
            FORMAT(hd.NGAYLAP, 'dd-MM') AS NGAY,
            SUM(hd.TONGTIEN) AS DOANHTHU,
            SUM(cthd.SOLUONG * (sp.DONGIA * 0.3)) AS LOINHUAN
        FROM HOADON hd
        JOIN CTHOADON cthd ON hd.MA_HD = cthd.MA_HD
        JOIN SANPHAM sp ON cthd.MA_SP = sp.MA_SP
        WHERE hd.NGAYLAP BETWEEN  ?  AND  ?
        GROUP BY FORMAT(hd.NGAYLAP, 'dd-MM')
        ORDER BY MIN(hd.NGAYLAP) ASC
    """;

        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(Ngay_BD.getTime()));
            ps.setDate(2, new java.sql.Date(Ngay_KT.getTime()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String Ngay = rs.getString("NGAY");
                double Doanh_Thu = rs.getDouble("DOANHTHU");
                double Loi_Nhuan = rs.getDouble("LOINHUAN");

                danhSach.add(new BieuDo_3_O(Ngay, Doanh_Thu, Loi_Nhuan));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_DoanhThu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return danhSach;
    }
}
