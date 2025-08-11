/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_BanHang;

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
public class QL_NganHang {

    MyConnection conn;

    public QL_NganHang() {
        conn = new MyConnection();
    }

    public List<NganHang> Get_All() {
        List<NganHang> List_NH = new ArrayList<>();
        String SQL = "SELECT * FROM NGANHANG";
        try {
            Connection conect = conn.DBConnect();
            Statement stm = conect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String So_TaiKhoan = rs.getString(1);
                String Ten_NganHang = rs.getString(2);
                String Ten_Chu_TK = rs.getString(3);
                String TrangThai = rs.getString(4);
                Date NgayTao = rs.getDate(5);
                NganHang nh = new NganHang(So_TaiKhoan, Ten_NganHang, Ten_Chu_TK, TrangThai, NgayTao);
                List_NH.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List_NH;
    }

    public Object[] Get_Row(NganHang nh) {
        String So_TaiKhoan = nh.getSo_TaiKhoan();
        String Ten_NganHang = nh.getTen_NganHang();
        String Ten_Chu_TK = nh.getTen_Chu_TK();
        String TrangThai = nh.getTrang_Thai();
        Date NgayTao = nh.getNgayTao();

        Object[] obj = new Object[]{So_TaiKhoan, Ten_NganHang, Ten_Chu_TK, TrangThai, NgayTao};
        return obj;
    }

    // Thêm Ngân Hàng
    public int Them_NH(NganHang nh) {
        String SQL = "INSERT INTO NGANHANG(SOTAIKHOAN_NH, TEN_NH, TEN_CHU_NH, TRANGTHAI, NGAYCAPNHATCUOI) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(SQL);
            ps.setString(1, nh.getSo_TaiKhoan());
            ps.setString(2, nh.getTen_NganHang());
            ps.setString(3, nh.getTen_Chu_TK());
            ps.setString(4, nh.getTrang_Thai());
            ps.setDate(5, new java.sql.Date(nh.getNgayTao().getTime()));
            if (ps.executeUpdate() > 0) {
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Sửa Dữ Liệu ngân Hàng
    public int Sua_NH(NganHang nh, String TheoMa_NH) {
        String SQL = "UPDATE NGANHANG SET SOTAIKHOAN_NH = ?, TEN_NH = ?, TEN_CHU_NH = ?, TRANGTHAI = ?  , NGAYCAPNHATCUOI = ? WHERE SOTAIKHOAN_NH = ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(SQL);
            ps.setString(1, nh.getSo_TaiKhoan());
            ps.setString(2, nh.getTen_NganHang());
            ps.setString(3, nh.getTen_Chu_TK());
            ps.setString(4, nh.getTrang_Thai());
            ps.setDate(5, new java.sql.Date(nh.getNgayTao().getTime()));
            ps.setString(6, TheoMa_NH);
            int result = ps.executeUpdate();
            ps.close();
            conect.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Kiểm Tra Số Tài Khoản Đã Trùng
    public boolean checkTrungSTK_DB(String stk) {
        String sql = "SELECT COUNT(*) FROM NGANHANG WHERE SOTAIKHOAN_NH = ?";
        try {
            Connection connection = conn.DBConnect();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, stk.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Trùng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_NganHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Check Trạng Thái Của Ngân Hàng Không Hoạt Động Hoặc Hoạt Động
    public boolean TonTai_TaiKhoan_HoatDong() {
        String sql = "SELECT COUNT(*) FROM NGANHANG WHERE TRANGTHAI = N'Hoạt Động'";
        try {
            Connection connection = conn.DBConnect();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // true nếu đã tồn tại ít nhất 1 tài khoản hoạt động
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_NganHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
