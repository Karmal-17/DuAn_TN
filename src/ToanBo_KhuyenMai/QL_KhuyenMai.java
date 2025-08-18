/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_KhuyenMai;

import DBConnect.MyConnection;
import ToanBo_KhuyenMai.KhuyenMai;
import ToanBo_SanPham.SanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class QL_KhuyenMai {

    MyConnection conn;

    public QL_KhuyenMai() {
        conn = new MyConnection();
    }

    public List<KhuyenMai> Get_All() {
        List<KhuyenMai> List_KM = new ArrayList<>(); //  Tạo một danh sách rỗng kiểu Nguyên Liệu để chứa tất cả tài khoản đọc từ database.
        String SQL = "SELECT * FROM KHUYENMAI"; //  Lấy toàn bộ dòng dữ liệu từ bảng NGUYENLIEU
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_KM = rs.getString(1);
                String Ten_KM = rs.getString(2);
                String HinhThuc_KM = rs.getString(3);
                String MoTa_KM = rs.getString(4);
                float GiaTri_YeuCau_KM = rs.getFloat(5);
                float GiaTri_KM = rs.getFloat(6);
                Date Ngay_BD_KM = rs.getDate(7);
                Date Ngay_KT_KM = rs.getDate(8);
                boolean TrangThai_KM = rs.getBoolean(9);
                KhuyenMai km = new KhuyenMai(Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, GiaTri_YeuCau_KM, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM, TrangThai_KM);
                List_KM.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_KM;
    }

    public Object[] GetRow(KhuyenMai km) {
        String Ma_KM = km.getMa_KM();
        String Ten_KM = km.getTen_KM();
        String HinhThuc_KM = km.getHinhThuc_KM();
        String MoTa_KM = km.getMoTa_KM();
        float DiemYeuCau_KM = km.getGiaTri_YeuCau_KM();
        float GiaTri_KM = km.getGiaTri_KM();
        Date Ngay_BD_KM = km.getNgay_BD();
        Date Ngay_KT_KM = km.getNgay_KT();
        boolean TrangThai_KM = km.isTrangThai();

        // Chuyển đổi trạng thái từ boolean sang chuỗi mô tả
        String TrangThaiText = TrangThai_KM ? "Đang Hoạt Động" : "Không Hoạt Động";

        Object[] obj = new Object[]{
            Ma_KM, Ten_KM, MoTa_KM, HinhThuc_KM, DiemYeuCau_KM, GiaTri_KM,
            Ngay_BD_KM, Ngay_KT_KM, TrangThaiText
        };

        return obj;
    }

    // Tự Động Nhập 
    public int getSoLuongKhuyenMai() {
        int count = 0;
        try {
            Connection connnect = conn.DBConnect();
            String sql = "SELECT COUNT(*) FROM KhuyenMai";
            Statement stmt = connnect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            connnect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    // Hàm Thêm Dữ Liệu Vào Tài Khoản
    public int Them_KM(KhuyenMai km) {
        String SQL = "INSERT INTO KHUYENMAI \n"
                + "            (MA_KM, TENKM, MOTA, HINHTHUC_KM, GIATRI_YEUCAU_KM, GIATRI, NGAYBATDAU, NGAYKETTHUC, TRANGTHAI)\n"
                + "            VALUES (  ?  ,  ?  ,   ?  ,  ?  ,  ?  ,  ? ,  ?  ,  ? ,  ? )"; // Có Hai Cách Giải Quyết Vấn Đề Về Thời Gian Tạo Này
        // Thứ Nhất Là Dùng Luôn Câu Lệnh SQL Là GETDATE() Còn Cái Này Thì Khả Năng Là Không Nhìn Thấy
        // Hai Là Dùng Code Java Thì Dài Ròng Hơn Nhưng Lại Có Lợi Là Nhìn Thấy Được Ở Ô Thời Gian
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, km.getMa_KM());
            pstm.setString(2, km.getTen_KM());
            pstm.setString(3, km.getMoTa_KM());
            pstm.setString(4, km.getHinhThuc_KM());
            pstm.setFloat(5, km.getGiaTri_YeuCau_KM());
            pstm.setFloat(6, km.getGiaTri_KM());
            pstm.setDate(7, km.getNgay_BD());
            pstm.setDate(8, km.getNgay_KT());
            pstm.setBoolean(11, km.isTrangThai());
            if (pstm.executeUpdate() > 0) {
                System.out.println("Them Khuyen Mai. Connect");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Hàm Xoá Tài Khoản
    public int Xoa_KM(String TheoMa) {
        String SQL = "DELETE FROM KHUYENMAI WHERE MA_KM = ?;";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, TheoMa);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Xoa Khuyen Mai. Connect");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Hàm Sửa Dữ Liệu Tài Khoản
    public int Sua_KM(KhuyenMai km, String TheoMa) {
        String SQL = "UPDATE KHUYENMAI SET\n"
                + "                    MA_KM = ?,\n"
                + "                    TENKM = ?, \n"
                + "                    MOTA = ?,\n"
                + "                    HINHTHUC_KM = ?,\n"
                + "                    GIATRI_YEUCAU_KM = ?, \n"
                + "                    GIATRI = ?,  \n"
                + "                    NGAYBATDAU = ?, \n"
                + "                    NGAYKETTHUC = ?, \n"
                + "                    TRANGTHAI = ?\n"
                + "                    WHERE MA_KM = ?";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, km.getMa_KM());
            pstm.setString(2, km.getTen_KM());
            pstm.setString(3, km.getMoTa_KM());
            pstm.setString(4, km.getHinhThuc_KM());
            pstm.setFloat(5, km.getGiaTri_YeuCau_KM());
            pstm.setFloat(6, km.getGiaTri_KM());
            pstm.setDate(7, km.getNgay_BD());
            pstm.setDate(8, km.getNgay_KT());
            pstm.setBoolean(9, km.isTrangThai());
            pstm.setString(10, TheoMa);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Sua Du Lieu Khuyen Mai. Connect");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lọc Sản Phẩm Theo Thời Gian Bắt Đầu Thời Gian Kết Thúc
    public List<KhuyenMai> Loc_KM(Date ThoiGianBT, Date ThoiGianKT) {
        List<KhuyenMai> List_KM = new ArrayList<>();
        String SQL = "SELECT * FROM KHUYENMAI WHERE NGAYBATDAU >= ? AND NGAYKETTHUC <= ? ";
        try {
            Connection connect = conn.DBConnect(); // 
            PreparedStatement pstm = connect.prepareStatement(SQL);
            pstm.setDate(1, ThoiGianBT);
            pstm.setDate(2, ThoiGianKT);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String Ma_KM = rs.getString(1);
                String Ten_KM = rs.getString(2);
                String HinhThuc_KM = rs.getString(3);
                String MoTa_KM = rs.getString(4);
                float GiaTri_YeuCau_KM = rs.getInt(5);
                float GiaTri_KM = rs.getFloat(6);
                Date Ngay_BD_KM = rs.getDate(7);
                Date Ngay_KT_KM = rs.getDate(8);
                boolean TrangThai_KM = rs.getBoolean(9);

                KhuyenMai km = new KhuyenMai(Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, GiaTri_YeuCau_KM, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM, TrangThai_KM);

                List_KM.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_KM;
    }

    // Tìm Kiếm Theo Mã Khuyến Mãi
//    public List<KhuyenMai> TimKiem_TheoMa(String TheoMa) {
//        List<KhuyenMai> List_TheoMa_KM = new ArrayList<>(); //  
//        String SQL = "SELECT * FROM KHUYENMAI WHERE MA_GIAM LIKE ? ";
//        try {
//            Connection connect = conn.DBConnect(); // 
//            PreparedStatement pstm = connect.prepareStatement(SQL);
//            pstm.setString(1, "%" + TheoMa + "%");
//            ResultSet rs = pstm.executeQuery();
//            while (rs.next()) {
//                String Ma_KM = rs.getString(1);
//                String Ten_KM = rs.getString(2);
//                String HinhThuc_KM = rs.getString(3);
//                String MoTa_KM = rs.getString(4);
//                int DiemYeuCau_KM = rs.getInt(5);
//                float GiaTri_KM = rs.getFloat(6);
//                Date Ngay_BD_KM = rs.getDate(7);
//                Date Ngay_KT_KM = rs.getDate(8);
//                String NgayTrongThang_KM = rs.getString(9);
//                String DieuKien_KM = rs.getString(10);
//                boolean TrangThai_KM = rs.getBoolean(11);
//                KhuyenMai km = new KhuyenMai(Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, DiemYeuCau_KM, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM, NgayTrongThang_KM, DieuKien_KM, TrangThai_KM);
//                List_TheoMa_KM.add(km);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // hoặc log ra file/log view
//        }
//        return List_TheoMa_KM;
//    }
//
//    // Tìm Kiếm Theo Tên Khuyến Mãi
//    public List<KhuyenMai> TimKiem_TheoTen(String TheoTen) {
//        List<KhuyenMai> List_TheoTen_KM = new ArrayList<>(); //  
//        String SQL = "SELECT * FROM KHUYENMAI WHERE TENKM LIKE UPPER(  ?  )"; //  
//        try {
//            Connection connect = conn.DBConnect(); // 
//            PreparedStatement pstm = connect.prepareStatement(SQL);
//            pstm.setString(1, "%" + TheoTen + "%");
//            ResultSet rs = pstm.executeQuery();
//            while (rs.next()) {
//                String Ma_KM = rs.getString(1);
//                String Ten_KM = rs.getString(2);
//                String HinhThuc_KM = rs.getString(3);
//                String MoTa_KM = rs.getString(4);
//                int DiemYeuCau_KM = rs.getInt(5);
//                float GiaTri_KM = rs.getFloat(6);
//                Date Ngay_BD_KM = rs.getDate(7);
//                Date Ngay_KT_KM = rs.getDate(8);
//                String NgayTrongThang_KM = rs.getString(9);
//                String DieuKien_KM = rs.getString(10);
//                boolean TrangThai_KM = rs.getBoolean(11);
//                KhuyenMai km = new KhuyenMai(Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, DiemYeuCau_KM, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM, NgayTrongThang_KM, DieuKien_KM, TrangThai_KM);
//                List_TheoTen_KM.add(km);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // hoặc log ra file/log view
//        }
//        return List_TheoTen_KM;
//    }
    public List<KhuyenMai_2_O> TimKiem_KM(String tuKhoa) {
        List<KhuyenMai_2_O> list = new ArrayList<>();
        String sql = "SELECT MA_KM, TENKM FROM KHUYENMAI WHERE MA_KM LIKE ? OR TENKM LIKE ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new KhuyenMai_2_O(
                        rs.getString("MA_KM"),
                        rs.getString("TENKM")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public KhuyenMai LayChiTietKM(String TheoMa_KM) {
        String sql = "SELECT * FROM KHUYENMAI WHERE MA_KM = ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(sql);
            ps.setString(1, TheoMa_KM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new KhuyenMai(
                        rs.getString("MA_KM"),
                        rs.getString("TENKM"),
                        rs.getString("MOTA"),
                        rs.getString("HINHTHUC_KM"),
                        rs.getFloat("GIATRI_YEUCAU_KM"),
                        rs.getFloat("GIATRI"),
                        rs.getDate("NGAYBATDAU"),
                        rs.getDate("NGAYKETTHUC"),
                        rs.getBoolean("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Lấy 4 ô
    public List<KhuyenMai_4_O> TK_KhuyenMai(String tuKhoa) {
        List<KhuyenMai_4_O> list_KM = new ArrayList<>();
        String SQL = "SELECT MA_KM, TENKM, GIATRI_YEUCAU_KM, GIATRI FROM KHUYENMAI WHERE MA_KM LIKE ? OR TENKM LIKE ?";

        try {
            Connection con = conn.DBConnect();
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String ma = rs.getString("MA_KM");
                String ten = rs.getString("TENKM");
                float GiaTri_YeuCau = rs.getFloat("GIATRI_YEUCAU_KM");
                float giaTri = rs.getFloat("GIATRI");
                KhuyenMai_4_O km_4o = new KhuyenMai_4_O(ma, ten, GiaTri_YeuCau, giaTri);
                list_KM.add(km_4o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_KM;
    }

    public List<KhuyenMai_4_O> getKhuyenMaiTheoMa(String tuKhoa) {
        List<KhuyenMai_4_O> ds = new ArrayList<>();
        String sql = "SELECT MA_KM, TENKM, GIATRI_YEUCAU_KM, GIATRI "
                + "FROM KHUYENMAI "
                + "WHERE MA_KM LIKE ? OR TENKM LIKE ? "
                + "ORDER BY TENKM ASC";

        try  {
            Connection connection = conn.DBConnect();
            PreparedStatement pst = connection.prepareStatement(sql);

            String keyword = "%" + tuKhoa + "%";
            pst.setString(1, keyword);
            pst.setString(2, keyword);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                KhuyenMai_4_O km = new KhuyenMai_4_O(
                        rs.getString("MA_KM"),
                        rs.getString("TENKM"),
                        rs.getFloat("GIATRI_YEUCAU_KM"),
                        rs.getFloat("GIATRI")
                );
                ds.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<KhuyenMai_4_O> LayThongtin_KM() {
        List<KhuyenMai_4_O> List_ds = new ArrayList<>();
        String SQL = "SELECT MA_KM, TEN_KM, GIATRI_YEUCAU_KM, GIATRI FROM KHUYENMAI";

        try {
            Connection con = conn.DBConnect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                String ma = rs.getString("MA_KM");
                String ten = rs.getString("TEN_KM");
                float GiaTri_YeuCau = rs.getFloat("GIATRI_YEUCAU_KM");
                float giaTri = rs.getFloat("GIATRI");

                List_ds.add(new KhuyenMai_4_O(ma, ten, GiaTri_YeuCau, giaTri));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List_ds;
    }

    // Khuyến Mãi 10 Ô Thai Trạng Thái
    public List<KhuyenMai_8_O> Get_All_KM01() {
        List<KhuyenMai_8_O> List_KM = new ArrayList<>(); //  Tạo một danh sách rỗng kiểu Nguyên Liệu để chứa tất cả tài khoản đọc từ database.
        String SQL = "SELECT MA_KM, TENKM, MOTA, HINHTHUC_KM, GIATRI_YEUCAU_KM, GIATRI, NGAYBATDAU, NGAYKETTHUC\n"
                + "FROM KHUYENMAI\n"
                + "WHERE TRANGTHAI = '1'"; //  Lấy toàn bộ dòng dữ liệu từ bảng NGUYENLIEU
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_KM = rs.getString(1);
                String Ten_KM = rs.getString(2);
                String HinhThuc_KM = rs.getString(3);
                String MoTa_KM = rs.getString(4);
                float GiaTri_YeuCau_KM = rs.getFloat(5);
                float GiaTri_KM = rs.getFloat(6);
                Date Ngay_BD_KM = rs.getDate(7);
                Date Ngay_KT_KM = rs.getDate(8);
                KhuyenMai_8_O km = new KhuyenMai_8_O(Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, GiaTri_YeuCau_KM, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM);
                List_KM.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_KM;
    }

    public Object[] GetRow_KM01(KhuyenMai_8_O km) {
        String Ma_KM = km.getMa_KM();
        String Ten_KM = km.getTen_KM();
        String MoTa_KM = km.getMoTa_KM();
        String HinhThuc_KM = km.getHinhThuc_KM();
        float DiemYeuCau_KM = km.getGiaTri_YeuCau_KM();
        float GiaTri_KM = km.getGiaTri_KM();
        Date Ngay_BD_KM = km.getNgay_BD();
        Date Ngay_KT_KM = km.getNgay_KT();
        // Chuyển đổi trạng thái từ boolean sang chuỗi mô tả
        Object[] obj = new Object[]{
            Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, DiemYeuCau_KM, GiaTri_KM,
            Ngay_BD_KM, Ngay_KT_KM
        };

        return obj;
    }

    // Trạng Thái Đang Hoạt Động
    public List<KhuyenMai_8_O> Get_All_KM02() {
        List<KhuyenMai_8_O> List_KM = new ArrayList<>(); //  Tạo một danh sách rỗng kiểu Nguyên Liệu để chứa tất cả tài khoản đọc từ database.
        String SQL = "SELECT MA_KM, TENKM, MOTA, HINHTHUC_KM, GIATRI_YEUCAU_KM, GIATRI, NGAYBATDAU, NGAYKETTHUC\n"
                + "FROM KHUYENMAI\n"
                + "WHERE TRANGTHAI = '0'"; //  Lấy toàn bộ dòng dữ liệu từ bảng NGUYENLIEU
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_KM = rs.getString(1);
                String Ten_KM = rs.getString(2);
                String HinhThuc_KM = rs.getString(3);
                String MoTa_KM = rs.getString(4);
                float GiaTri_YeuCau_KM = rs.getFloat(5);
                float GiaTri_KM = rs.getFloat(6);
                Date Ngay_BD_KM = rs.getDate(7);
                Date Ngay_KT_KM = rs.getDate(8);
                KhuyenMai_8_O km = new KhuyenMai_8_O(Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, GiaTri_YeuCau_KM, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM);
                List_KM.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_KM;
    }

    public Object[] GetRow_KM02(KhuyenMai_8_O km) {
        String Ma_KM = km.getMa_KM();
        String Ten_KM = km.getTen_KM();
        String MoTa_KM = km.getMoTa_KM();
        String HinhThuc_KM = km.getHinhThuc_KM();
        float DiemYeuCau_KM = km.getGiaTri_YeuCau_KM();
        float GiaTri_KM = km.getGiaTri_KM();
        Date Ngay_BD_KM = km.getNgay_BD();
        Date Ngay_KT_KM = km.getNgay_KT();
        // Chuyển đổi trạng thái từ boolean sang chuỗi mô tả
        Object[] obj = new Object[]{
            Ma_KM, Ten_KM, HinhThuc_KM, MoTa_KM, DiemYeuCau_KM, GiaTri_KM,
            Ngay_BD_KM, Ngay_KT_KM
        };

        return obj;
    }

    // Lấy Điều Kiện KM 
    public KhuyenMai LayThongTin_KM(String Ma_KM) {
        String SQL = "SELECT HINHTHUC_KM, GIATRI_YEUCAU_KM, GIATRI FROM KHUYENMAI WHERE MA_KM = ?";
        try {
            Connection connect = conn.DBConnect();
            PreparedStatement pstm = connect.prepareStatement(SQL);
            pstm.setString(1, Ma_KM);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setHinhThuc_KM(rs.getString("HINHTHUC_KM")); // "Điểm tích luỹ" hoặc "Tiền mặt"
                km.setGiaTri_YeuCau_KM(rs.getFloat("GIATRI_YEUCAU_KM"));
                km.setGiaTri_KM(rs.getFloat("GIATRI"));
                return km;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lọc KM
    public List<KhuyenMai> TimKiem_KhuyenMai_PhuHop(int DiemKhach, float TongTien) {
        List<KhuyenMai> danhSachPhuHop = new ArrayList<>();
        String SQL = "SELECT MA_KM , HINHTHUC_KM , GIATRI_YEUCAU_KM , GIATRI_YEUCAU_KM, GIATRI FROM KHUYENMAI";

        try {
            Connection conect = conn.DBConnect();
            PreparedStatement pstm = conect.prepareStatement(SQL);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String hinhThuc = rs.getString("HINHTHUC_KM");
                float DiemYeuCau = rs.getFloat("GIATRI_YEUCAU_KM");
                float TienMatYeuCau = rs.getFloat("GIATRI_YEUCAU_KM");
                float MucGiam = rs.getFloat("GIATRI"); // giả sử đây là số tiền giảm

                boolean hopLe = false;
                if (hinhThuc.equalsIgnoreCase("Điểm Tích Luỹ") && DiemKhach >= DiemYeuCau) {
                    hopLe = true;
                } else if (hinhThuc.equalsIgnoreCase("Tiền Mặt") && TongTien >= TienMatYeuCau) {
                    hopLe = true;
                }
//                 else if (hinhThuc.equalsIgnoreCase("Phần Trăm") && ) {
//                    
//                }

                if (hopLe) {
                    KhuyenMai km = new KhuyenMai();
                    km.setMa_KM(rs.getString("MA_KM"));
                    km.setHinhThuc_KM(hinhThuc);
                    km.setGiaTri_KM(MucGiam);
                    danhSachPhuHop.add(km);
                }
            }
            // Sắp xếp từ khuyến mãi cao nhất xuống
            danhSachPhuHop.sort((km1, km2) -> Float.compare(km2.getGiaTri_KM(), km1.getGiaTri_KM()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSachPhuHop;
    }

    // Cái Phần Khuyến Mãi Theo Trạng Thái 
    public List<KhuyenMai> timKiemKhuyenMaiTheoTrangThai(boolean TrangThai) {
        List<KhuyenMai> danhSachKM = new ArrayList<>();
        String sql = "SELECT * FROM KHUYENMAI WHERE TRANGTHAI = ?";
// MA_KM , TENKM , MOTA , HINHTHUC_KM , DIEM_YEUCAU , GIATRI , NGAYBATDAU , NGAYKETTHUC , NGAYTRONGTHANG , DIEM_YEUCAU , TRANGTHAI
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement stmt = conect.prepareStatement(sql);
            stmt.setBoolean(1, TrangThai);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    KhuyenMai km = new KhuyenMai(
                            rs.getString("MA_KM"),
                            rs.getString("TENKM"),
                            rs.getString("MOTA"),
                            rs.getString("HINHTHUC_KM"),
                            rs.getInt("GIATRI_YEUCAU_KM"),
                            rs.getFloat("GIATRI"),
                            rs.getDate("NGAYBATDAU"),
                            rs.getDate("NGAYKETTHUC"),
                            rs.getBoolean("TRANGTHAI")
                    );
                    danhSachKM.add(km);
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tìm kiếm khuyến mãi theo trạng thái: " + e.getMessage());
            e.printStackTrace();
        }

        return danhSachKM;
    }

    public KhuyenMai layKhuyenMaiTheoMa(String Ma_KM) {
        String sql = "SELECT * FROM KHUYENMAI WHERE MA_KM = ?";
        try {
            Connection conect = conn.DBConnect();
            PreparedStatement stmt = conect.prepareStatement(sql);
            stmt.setString(1, Ma_KM);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new KhuyenMai(
                            rs.getString("MA_KM"),
                            rs.getString("TENKM"),
                            rs.getString("MOTA"),
                            rs.getString("HINHTHUC_KM"),
                            rs.getInt("GIATRI_YEUCAU_KM"),
                            rs.getFloat("GIATRI"),
                            rs.getDate("NGAYBATDAU"),
                            rs.getDate("NGAYKETTHUC"),
                            rs.getBoolean("TRANGTHAI")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy Giá Trị Khuyến Mãi Qua Hàm
    public float LayGiaTri_KhuyenMai(String Ma_KM) {
        float GiaTri_KM = 0f; // Mặc định nếu không tìm thấy mã

        String sql = "SELECT GIATRI FROM KHUYENMAI WHERE MA_KM = ?";

        try {
            Connection conect = conn.DBConnect();
            PreparedStatement ps = conect.prepareStatement(sql);
            ps.setString(1, Ma_KM);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                GiaTri_KM = rs.getFloat("GIATRI");
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi lấy giá trị khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_KhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
        }

        return GiaTri_KM;
    }

    // Lấy Giá Trị Khuyến Mãi Theo Mã Khuyến Mãi Không Dùng Biến
    public static Float getGiaTriKhuyenMaiTheoMa(String maKM) {
        Float giaTri = null;
        String sql = "SELECT GIATRI FROM KHUYENMAI WHERE MA_KM = ?";

        try {
            MyConnection conn;
            conn = new MyConnection();
            Connection connection = conn.DBConnect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maKM);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    giaTri = rs.getFloat("GIATRI");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log lỗi
        }

        return giaTri;
    }

}
