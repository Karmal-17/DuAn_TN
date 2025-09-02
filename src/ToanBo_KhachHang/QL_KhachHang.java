/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_KhachHang;

import DBConnect.MyConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class QL_KhachHang {

    MyConnection conn;

    public QL_KhachHang() {
        conn = new MyConnection();
    }

//    private String Ma_KH;
//    private String Ten_KH;
//    private String SDT_KH;
//    private String Email_KH;
//    private Date NgayTao_KH;
//    private String Loai_KH;
    public List<KhachHang> Get_All() {
        List<KhachHang> List_KH = new ArrayList<>();
        String SQL = "SELECT * FROM KHACHHANG";

        try {
            Connection connect = conn.DBConnect();
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);

            DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // dùng định dạng Việt Nam

            while (rs.next()) {
                String Ma_KH = rs.getString(1);
                String Ten_KH = rs.getString(2);
                String SDT_KH = rs.getString(3);
                String Email_KH = rs.getString(4);

                // Đọc ngày dưới dạng chuỗi
                String ngayTaoStr = rs.getString(5);
                Date NgayTao_KH = null;
                try {
                    LocalDate localDate = LocalDate.parse(ngayTaoStr, DinhDang);
                    NgayTao_KH = Date.valueOf(localDate);
                } catch (Exception ex) {
                    System.out.println("⚠️ Ngày sai định dạng tại mã KH = " + Ma_KH + ": " + ngayTaoStr);
                    NgayTao_KH = Date.valueOf(LocalDate.now());
                }

                String Loai_KH = rs.getString(6);
                int DiemTichLuy = rs.getInt(7);

                KhachHang kh = new KhachHang(Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, Loai_KH, DiemTichLuy);
                List_KH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List_KH;
    }

    public Object[] GetRow(KhachHang kh) {
        String Ma_KH = kh.getMa_KH();
        String Ten_KH = kh.getTen_KH();
        String SDT_KH = kh.getSDT_KH();
        String Email_KH = kh.getEmail_KH();
        Date NgayTao_KH = kh.getNgayTao_KH();
        String Loai_KH = kh.getLoai_KH();
        int DiemTichLuy = kh.getDiemTichLuy();

        Object[] obj = new Object[]{Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, Loai_KH, DiemTichLuy};
        return obj;
    }

    // Hàm Thêm Dữ Liệu Vào Tài Khoản
    public int Them_KH(KhachHang kh) {
        String SQL = "INSERT INTO KHACHHANG  VALUES\n"
                + "(  ?  ,   ?   ,   ?   ,  ?  ,  ?  , ?)";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, kh.getMa_KH());
            pstm.setString(2, kh.getTen_KH());
            pstm.setString(3, kh.getSDT_KH());
            pstm.setString(4, kh.getEmail_KH());
            pstm.setDate(5, kh.getNgayTao_KH());
            pstm.setString(6, kh.getLoai_KH());
            pstm.setInt(7, kh.getDiemTichLuy());
            if (pstm.executeUpdate() > 0) {
                System.out.println("Them Khach Hang. Connect");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Hàm Xoá Tài Khoản
    public int Xoa_KH(String TheoMa) {
        String SQL = "DELETE FROM KHACHHANG WHERE MA_KH = ? ";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, TheoMa);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Xoa Khach Hang. Connect");
                return 1;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // Hàm Sửa Dữ Liệu Tài Khoản
    public int Sua_KH(KhachHang kh, String TheoMa) {
        String sql = "UPDATE KHACHHANG SET MA_KH = ?,"
                + " HOTEN = ?, "
                + " SDT = ?, "
                + " EMAIL = ?, "
                + " NGAYTAO = ?,"
                + " LOAI_KH = ?, "
                + " DIEM_TICHLUY = ? "
                + " WHERE MA_KH = ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement pstm = conect.prepareStatement(sql);
            pstm.setString(1, kh.getMa_KH());           // MA_KH
            pstm.setString(2, kh.getTen_KH());          // HOTEN
            pstm.setString(3, kh.getSDT_KH());          // SDT
            pstm.setString(4, kh.getEmail_KH());        // EMAIL
            pstm.setDate(5, kh.getNgayTao_KH());        // NGAYTAO
            pstm.setString(6, kh.getLoai_KH());         // LOAI_KH
            pstm.setInt(7, kh.getDiemTichLuy());        // DIEM_TICHLUY
            pstm.setString(8, TheoMa);                  // WHERE MA_KH = ?

            if (pstm.executeUpdate() > 0) {
                System.out.println("✅ Đã sửa dữ liệu khách hàng.");
                return 1;
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi sửa khách hàng: " + e.getMessage());
        }
        return 0;
    }

    public List<KhachHang_4_O> TimKiemTheo_KhachHang(String tuKhoa) {
        List<KhachHang_4_O> ds = new ArrayList<>();
        String SQL = "SELECT MA_KH, HOTEN, SDT, DIEM_TICHLUY FROM KHACHHANG WHERE MA_KH LIKE ? OR HOTEN LIKE ? OR SDT LIKE ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(SQL);
            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");
            ps.setString(3, "%" + tuKhoa + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHang_4_O kh = new KhachHang_4_O(
                        rs.getString("MA_KH"),
                        rs.getString("HOTEN"),
                        rs.getString("SDT"),
                        rs.getInt("DIEM_TICHLUY")
                );
                ds.add(kh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm KH: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    public KhachHang_4_O timKHTheoMa(String maKH) {
        String sql = "SELECT MA_KH, HOTEN, SDT, DIEM_TICHLUY FROM KHACHHANG WHERE MA_KH = ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(sql);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new KhachHang_4_O(
                        rs.getString("MA_KH"),
                        rs.getString("HOTEN"),
                        rs.getString("SDT"),
                        rs.getInt("DIEM_TICHLUY")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm KH theo mã: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Lọc Khách Hàng Theo Loại KH
    // Khách Hàng Thường
    public List<KhachHang_6_O> Get_All_KhachThuong() {
        List<KhachHang_6_O> List_KH = new ArrayList<>();
        String SQL = "SELECT MA_KH , HOTEN , SDT , EMAIL , NGAYTAO , DIEM_TICHLUY FROM KHACHHANG WHERE LOAI_KH = 'Khách Thường'";

        try {
            Connection connect = conn.DBConnect();
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);

            DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // dùng định dạng Việt Nam

            while (rs.next()) {
                String Ma_KH = rs.getString("MA_KH");
                String Ten_KH = rs.getString("HOTEN");
                String SDT_KH = rs.getString("SDT");
                String Email_KH = rs.getString("EMAIL");

                // Đọc ngày dưới dạng chuỗi
                String ngayTaoStr = rs.getString("NGAYTAO");
                Date NgayTao_KH = null;
                try {
                    LocalDate localDate = LocalDate.parse(ngayTaoStr, DinhDang);
                    NgayTao_KH = Date.valueOf(localDate);
                } catch (Exception ex) {
                    System.out.println("⚠️ Ngày sai định dạng tại mã KH = " + Ma_KH + ": " + ngayTaoStr);
                    NgayTao_KH = Date.valueOf(LocalDate.now());
                }

                int DiemTichLuy = rs.getInt("DIEM_TICHLUY");

                KhachHang_6_O kh = new KhachHang_6_O(Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, DiemTichLuy);
                List_KH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List_KH;
    }

    public Object[] GetRow_KhachThuong(KhachHang_6_O kh) {
        String Ma_KH = kh.getMa_KH();
        String Ten_KH = kh.getTen_KH();
        String SDT_KH = kh.getSDT_KH();
        String Email_KH = kh.getEmail_KH();
        Date NgayTao_KH = kh.getNgayTao_KH();
        int DiemTichLuy = kh.getDiemTichLuy();

        Object[] obj = new Object[]{Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, DiemTichLuy};
        return obj;
    }

    // Khách Hàng VIP
    public List<KhachHang_6_O> Get_All_KhachVIP() {
        List<KhachHang_6_O> List_KH = new ArrayList<>();
        String SQL = "SELECT MA_KH , HOTEN , SDT , EMAIL , NGAYTAO , DIEM_TICHLUY FROM KHACHHANG WHERE LOAI_KH = 'Khách VIP'";

        try {
            Connection connect = conn.DBConnect();
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);

            DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // dùng định dạng Việt Nam

            while (rs.next()) {
                String Ma_KH = rs.getString("MA_KH");
                String Ten_KH = rs.getString("HOTEN");
                String SDT_KH = rs.getString("SDT");
                String Email_KH = rs.getString("EMAIL");

                // Đọc ngày dưới dạng chuỗi
                String ngayTaoStr = rs.getString("NGAYTAO");
                Date NgayTao_KH = null;
                try {
                    LocalDate localDate = LocalDate.parse(ngayTaoStr, DinhDang);
                    NgayTao_KH = Date.valueOf(localDate);
                } catch (Exception ex) {
                    System.out.println("⚠️ Ngày sai định dạng tại mã KH = " + Ma_KH + ": " + ngayTaoStr);
                    NgayTao_KH = Date.valueOf(LocalDate.now());
                }

                int DiemTichLuy = rs.getInt("DIEM_TICHLUY");

                KhachHang_6_O kh = new KhachHang_6_O(Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, DiemTichLuy);
                List_KH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List_KH;
    }

    public Object[] GetRow_KhachVIP(KhachHang_6_O kh) {
        String Ma_KH = kh.getMa_KH();
        String Ten_KH = kh.getTen_KH();
        String SDT_KH = kh.getSDT_KH();
        String Email_KH = kh.getEmail_KH();
        Date NgayTao_KH = kh.getNgayTao_KH();
        int DiemTichLuy = kh.getDiemTichLuy();

        Object[] obj = new Object[]{Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, DiemTichLuy};
        return obj;
    }

    // Khách Hàng Luxury
    public List<KhachHang_6_O> Get_All_KhachLuxury() {
        List<KhachHang_6_O> List_KH = new ArrayList<>();
        String SQL = "SELECT MA_KH , HOTEN , SDT , EMAIL , NGAYTAO , DIEM_TICHLUY FROM KHACHHANG WHERE LOAI_KH = 'Khách Luxury'";

        try {
            Connection connect = conn.DBConnect();
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);

            DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // dùng định dạng Việt Nam

            while (rs.next()) {
                String Ma_KH = rs.getString("MA_KH");
                String Ten_KH = rs.getString("HOTEN");
                String SDT_KH = rs.getString("SDT");
                String Email_KH = rs.getString("EMAIL");

                // Đọc ngày dưới dạng chuỗi
                String ngayTaoStr = rs.getString("NGAYTAO");
                Date NgayTao_KH = null;
                try {
                    LocalDate localDate = LocalDate.parse(ngayTaoStr, DinhDang);
                    NgayTao_KH = Date.valueOf(localDate);
                } catch (Exception ex) {
                    System.out.println("⚠️ Ngày sai định dạng tại mã KH = " + Ma_KH + ": " + ngayTaoStr);
                    NgayTao_KH = Date.valueOf(LocalDate.now());
                }

                int DiemTichLuy = rs.getInt("DIEM_TICHLUY");

                KhachHang_6_O kh = new KhachHang_6_O(Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, DiemTichLuy);
                List_KH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List_KH;
    }

    public Object[] GetRow_KhachLuxury(KhachHang_6_O kh) {
        String Ma_KH = kh.getMa_KH();
        String Ten_KH = kh.getTen_KH();
        String SDT_KH = kh.getSDT_KH();
        String Email_KH = kh.getEmail_KH();
        Date NgayTao_KH = kh.getNgayTao_KH();
        int DiemTichLuy = kh.getDiemTichLuy();

        Object[] obj = new Object[]{Ma_KH, Ten_KH, SDT_KH, Email_KH, NgayTao_KH, DiemTichLuy};
        return obj;
    }

    public int layDiemTichLuy(String maKH) {
        String sql = "SELECT DIEM_TICHLUY FROM KHACHHANG WHERE MA_KH = ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement stmt = conect.prepareStatement(sql);
            stmt.setString(1, maKH);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("DIEM_TICHLUY");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lấy điểm tích lũy: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // mặc định nếu không có
    }

    public String getTenTaiKhoan(String maTK) {
        String tenTaiKhoan = null;
        String sql = "SELECT TENTAIKHOAN FROM TAIKHOAN WHERE MA_TK = ?";

        try {
            Connection connection = conn.DBConnect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maTK);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tenTaiKhoan = rs.getString("TENTAIKHOAN");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tenTaiKhoan;
    }

    public String getSoDienThoaiTaiKhoan(String maTK) {
        String sdt = null;
        String sql = "SELECT SDT FROM TAIKHOAN WHERE MA_TK = ?";

        try {
            Connection connection = conn.DBConnect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maTK);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                sdt = rs.getString("SDT");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sdt;
    }
}
