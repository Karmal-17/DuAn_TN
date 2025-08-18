/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_TaiKhoan;

import DBConnect.MyConnection;
import ToanBo_TaiKhoan.Tai_Khoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class QL_TaiKhoan {

    MyConnection conn;

    public QL_TaiKhoan() {
        conn = new MyConnection();
    }

    public List<Tai_Khoan> Get_All() {
        List<Tai_Khoan> List_TK = new ArrayList<>(); //  Tạo một danh sách rỗng kiểu Tai_Khoan để chứa tất cả tài khoản đọc từ database.
        String SQL = "SELECT * FROM TAIKHOAN"; //  Lấy toàn bộ dòng dữ liệu từ bảng TAIKHOAN.
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_TK = rs.getString(1);
                String Ten_TK = rs.getString(2);
                String SDT_TK = rs.getString(3);
                String Email_TK = rs.getString(4);
                String DiaChi_TK = rs.getString(5);
                String VaiTro_TK = rs.getString(6);
                Date Ngay_DK_TK = rs.getDate(7);
                String Anh_TK = rs.getString(8);
                boolean TrangThai_TK = rs.getBoolean(9);
                Tai_Khoan tk = new Tai_Khoan(Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, VaiTro_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK);
                List_TK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_TK;
    }

    public Object[] GetRow(Tai_Khoan tk) {
        String Ma_TK = tk.getMa_TK();
        String Ten_TK = tk.getTen_TK();
        String SDT_TK = tk.getSDT_TK();
        String Email_TK = tk.getEmail_TK();
        String DiaChi_TK = tk.getDiaChi_TK();
        String VaiTro_TK = tk.getVaiTro_TK();
        Date Ngay_DK_TK = tk.getNgay_DK_TK();
        String Anh_TK = tk.getAnh_TK();

        // Chuyển boolean sang chuỗi mô tả
        String TrangThai_TK = tk.getTrangThai_TK() ? "Đang Hoạt Động" : "Không Hoạt Động";

        Object[] obj = new Object[]{Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, VaiTro_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK};
        return obj;
    }

    // Hàm Thêm Dữ Liệu Vào Tài Khoản
    public int Them_TK(Tai_Khoan tk) {
        String SQL = "INSERT INTO TAIKHOAN  "
                + "VALUES (  ?  ,   ?  ,   ?  ,   ?  ,   ?  ,   ?  ,   ?  ,   ?  ,   ?   )";
        // Có Hai Cách Giải Quyết Vấn Đề Về Thời Gian Tạo Này
        // Thứ Nhất Là Dùng Luôn Câu Lệnh SQL Là GETDATE() Còn Cái Này Thì Khả Năng Là Không Nhìn Thấy
        // Hai Là Dùng Code Java Thì Dài Ròng Hơn Nhưng Lại Có Lợi Là Nhìn Thấy Được Ở Ô Thời Gian
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, tk.getMa_TK());
            pstm.setString(2, tk.getTen_TK());
            pstm.setString(3, tk.getSDT_TK());
            pstm.setString(4, tk.getEmail_TK());
            pstm.setString(5, tk.getDiaChi_TK());
            pstm.setString(6, tk.getVaiTro_TK());
            pstm.setDate(7, tk.getNgay_DK_TK());
            pstm.setString(8, tk.getAnh_TK());
            pstm.setBoolean(9, tk.getTrangThai_TK());
            if (pstm.executeUpdate() > 0) {
                System.out.println("Them Tai Khoan. Connect");
                return 1;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // Hàm Xoá Tài Khoản
    public int Xoa_TK(String TheoMa) {
        String SQL = "DELETE FROM TAIKHOAN WHERE MA_TK = ?";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, TheoMa);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Xoa Tai Khoan. Connect");
                return 1;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // Hàm Sửa Dữ Liệu Tài Khoản
    public int Sua_TK(Tai_Khoan tk, String TheoMa) {
        String SQL = "UPDATE TAIKHOAN SET MA_TK =   ?  ,\n"
                + "                       TENTAIKHOAN =  ?  ,\n"
                + "			  SDT =   ?  ,\n"
                + "			  EMAIL =   ? ,\n"
                + "			  DIACHI =   ?  ,\n"
                + "			  VAITRO =   ?  ,\n"
                + "			  NGAYDANGKY =   ?  ,\n"
                + "			  ANH_TK =   ?  ,\n"
                + "			  TRANGTHAI =  ?  \n"
                + "			  WHERE MA_TK =   ?  ";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, tk.getMa_TK());
            pstm.setString(2, tk.getTen_TK());
            pstm.setString(3, tk.getSDT_TK());
            pstm.setString(4, tk.getEmail_TK());
            pstm.setString(5, tk.getDiaChi_TK());
            pstm.setString(6, tk.getVaiTro_TK());
            pstm.setDate(7, tk.getNgay_DK_TK());
            pstm.setString(8, tk.getAnh_TK());
            pstm.setBoolean(9, tk.getTrangThai_TK());
            pstm.setString(10, TheoMa);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Sua Du Lieu Tai Khoan. Connect");
                return 1;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int Sua_TK_NhanVien(Tai_Khoan_8_O tk, String TheoMa) {
        String SQL = "UPDATE TAIKHOAN SET MA_TK =   ?  ,\n"
                + "                       TENTAIKHOAN =  ?  ,\n"
                + "			  SDT =   ?  ,\n"
                + "			  EMAIL =   ? ,\n"
                + "			  DIACHI =   ?  ,\n"
                + "			  NGAYDANGKY =   ?  ,\n"
                + "			  ANH_TK =   ?  ,\n"
                + "			  TRANGTHAI =  ?  \n"
                + "			  WHERE MA_TK =   ?  ";
        try {
            Connection Connect = conn.DBConnect();
            PreparedStatement pstm = Connect.prepareStatement(SQL);
            pstm.setString(1, tk.getMa_TK());
            pstm.setString(2, tk.getTen_TK());
            pstm.setString(3, tk.getSDT_TK());
            pstm.setString(4, tk.getEmail_TK());
            pstm.setString(5, tk.getDiaChi_TK());
            pstm.setDate(6, tk.getNgay_DK_TK());
            pstm.setString(7, tk.getAnh_TK());
            pstm.setBoolean(8, tk.isTrangThai_TK());
            pstm.setString(9, TheoMa);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Sua Du Lieu Tai Khoan. Connect");
                return 1;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // Hàm Tìm Kiếm Tài Khoản Theo SDT
    public List<Tai_Khoan> TimKiem_Theo_SDT(String Theo_SDT) {
        List<Tai_Khoan> List_TK = new ArrayList<>();
        String SQL = "SELECT * FROM TAIKHOAN";
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_TK = rs.getString(1);
                String Ten_TK = rs.getString(2);
                String SDT_TK = rs.getString(3);
                String Email_TK = rs.getString(4);
                String DiaChi_TK = rs.getString(5);
                String VaiTro_TK = rs.getString(5);
                Date Ngay_DK_TK = rs.getDate(6);
                String Anh_TK = rs.getString(7);
                boolean TrangThai_TK = rs.getBoolean(8);
                Tai_Khoan tk = new Tai_Khoan(Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, VaiTro_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK);
                if (SDT_TK.equalsIgnoreCase(Theo_SDT)) {
                    System.out.println("Tim Kiem Tai Khoan Theo SDT. Connect");
                    List_TK.add(tk);
                }
            }
        } catch (Exception e) {
        }
        return List_TK;
    }

    // Hàm Tìm Kiếm Tài KHoản Theo  Tên
    public List<Tai_Khoan> TimKiem_Theo_Ten(String TheoTen) {
        List<Tai_Khoan> List_TK = new ArrayList<>();
        String SQL = "SELECT * FROM TAIKHOAN";
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_TK = rs.getString(1);
                String Ten_TK = rs.getString(2);
                String SDT_TK = rs.getString(3);
                String Email_TK = rs.getString(4);
                String DiaChi_TK = rs.getString(5);
                String VaiTro_TK = rs.getString(5);
                Date Ngay_DK_TK = rs.getDate(6);
                String Anh_TK = rs.getString(7);
                boolean TrangThai_TK = rs.getBoolean(8);
                Tai_Khoan tk = new Tai_Khoan(Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, VaiTro_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK);
                if (Ten_TK.equalsIgnoreCase(TheoTen)) {
                    System.out.println("Tim Kiem Tai Khoan Theo Ten Tai Khoan. Connect");
                    List_TK.add(tk);
                }
            }
        } catch (Exception e) {
        }
        return List_TK;
    }

    // Hàm Tìm Kiếm Số Điện Thoại Theo Mã Tài Khoản
    public List<Tai_Khoan> TimKiem_Theo_MaTK(String TheoMa) {
        List<Tai_Khoan> List_TK = new ArrayList<>();
        String SQL = "SELECT * FROM TAIKHOAN";
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_TK = rs.getString(1);
                String Ten_TK = rs.getString(2);
                String SDT_TK = rs.getString(3);
                String Email_TK = rs.getString(4);
                String DiaChi_TK = rs.getString(5);
                String VaiTro_TK = rs.getString(5);
                Date Ngay_DK_TK = rs.getDate(6);
                String Anh_TK = rs.getString(7);
                boolean TrangThai_TK = rs.getBoolean(8);
                Tai_Khoan tk = new Tai_Khoan(Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, VaiTro_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK);
                if (Ma_TK.equals(TheoMa)) {
                    System.out.println("Tim Kiem Tai Khoan Theo Ma Tai Khoan. Connect");
                    List_TK.add(tk);
                }
            }
        } catch (Exception e) {
        }
        return List_TK;
    }

    // Tất Cả Tài Khoản Với Vai Trò Là Người Quán Lý
    public List<Tai_Khoan_8_O> Get_All_QuanLy() {
        List<Tai_Khoan_8_O> List_TK = new ArrayList<>(); //  Tạo một danh sách rỗng kiểu Tai_Khoan để chứa tất cả tài khoản đọc từ database.
        String SQL = "SELECT MA_TK , TENTAIKHOAN , SDT , EMAIL , DIACHI , NGAYDANGKY , ANH_TK , TRANGTHAI FROM TAIKHOAN WHERE VAITRO LIKE N'Quản Lý'"; //  Lấy toàn bộ dòng dữ liệu từ bảng TAIKHOAN.
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_TK = rs.getString("MA_TK");
                String Ten_TK = rs.getString("TENTAIKHOAN");
                String SDT_TK = rs.getString("SDT");
                String Email_TK = rs.getString("EMAIL");
                String DiaChi_TK = rs.getString("DIACHI");
                Date Ngay_DK_TK = rs.getDate("NGAYDANGKY");
                String Anh_TK = rs.getString("ANH_TK");
                boolean TrangThai_TK = rs.getBoolean("TRANGTHAI");
                Tai_Khoan_8_O tk = new Tai_Khoan_8_O(Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK);
                List_TK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_TK;
    }

    public Object[] GetRow_QuanLy(Tai_Khoan_8_O tk) {
        String Ma_TK = tk.getMa_TK();
        String Ten_TK = tk.getTen_TK();
        String SDT_TK = tk.getSDT_TK();
        String Email_TK = tk.getEmail_TK();
        String DiaChi_TK = tk.getDiaChi_TK();
        Date Ngay_DK_TK = tk.getNgay_DK_TK();
        String Anh_TK = tk.getAnh_TK();

        // Chuyển boolean sang chuỗi mô tả
        String TrangThai_TK = tk.isTrangThai_TK() ? "Đang Hoạt Động" : "Không Hoạt Động";

        Object[] obj = new Object[]{Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK};
        return obj;
    }

    // Tất Cả Tài Khoản Với Vai Trò Là Người Nhân Viên
    public List<Tai_Khoan_8_O> Get_All_NhanVien() {
        List<Tai_Khoan_8_O> List_TK = new ArrayList<>(); //  Tạo một danh sách rỗng kiểu Tai_Khoan để chứa tất cả tài khoản đọc từ database.
        String SQL = "SELECT MA_TK , TENTAIKHOAN , SDT , EMAIL , DIACHI , NGAYDANGKY , ANH_TK , TRANGTHAI FROM TAIKHOAN WHERE VAITRO LIKE N'Nhân Viên'"; //  Lấy toàn bộ dòng dữ liệu từ bảng TAIKHOAN.
        try {
            Connection connect = conn.DBConnect(); // 
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()) {
                String Ma_TK = rs.getString("MA_TK");
                String Ten_TK = rs.getString("TENTAIKHOAN");
                String SDT_TK = rs.getString("SDT");
                String Email_TK = rs.getString("EMAIL");
                String DiaChi_TK = rs.getString("DIACHI");
                Date Ngay_DK_TK = rs.getDate("NGAYDANGKY");
                String Anh_TK = rs.getString("ANH_TK");
                boolean TrangThai_TK = rs.getBoolean("TRANGTHAI");
                Tai_Khoan_8_O tk = new Tai_Khoan_8_O(Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK);
                List_TK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra file/log view
        }
        return List_TK;
    }

    public Object[] GetRow_NhanVien(Tai_Khoan_8_O tk) {
        String Ma_TK = tk.getMa_TK();
        String Ten_TK = tk.getTen_TK();
        String SDT_TK = tk.getSDT_TK();
        String Email_TK = tk.getEmail_TK();
        String DiaChi_TK = tk.getDiaChi_TK();
        Date Ngay_DK_TK = tk.getNgay_DK_TK();
        String Anh_TK = tk.getAnh_TK();

        // Chuyển boolean sang chuỗi mô tả
        String TrangThai_TK = tk.isTrangThai_TK() ? "Đang Hoạt Động" : "Không Hoạt Động";

        Object[] obj = new Object[]{Ma_TK, Ten_TK, SDT_TK, Email_TK, DiaChi_TK, Ngay_DK_TK, Anh_TK, TrangThai_TK};
        return obj;
    }

    public List<Tai_Khoan> TimKiem_TaiKhoan(String tuKhoa) {
        List<Tai_Khoan> ketQua = new ArrayList<>();
        String SQL = "SELECT MA_TK, TENTAIKHOAN, SDT, EMAIL, DIACHI, VAITRO, NGAYDANGKY, ANH_TK, TRANGTHAI "
                + "FROM TAIKHOAN WHERE SDT LIKE ? OR TENTAIKHOAN LIKE ?";

        try {
            Connection connect = conn.DBConnect();
            PreparedStatement pstm = connect.prepareStatement(SQL);
            String keyword = "%" + tuKhoa + "%";
            pstm.setString(1, keyword);
            pstm.setString(2, keyword);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Tai_Khoan tk = new Tai_Khoan();
                tk.setMa_TK(rs.getString("MA_TK"));
                tk.setTen_TK(rs.getString("TENTAIKHOAN"));
                tk.setSDT_TK(rs.getString("SDT"));
                tk.setEmail_TK(rs.getString("EMAIL"));
                tk.setDiaChi_TK(rs.getString("DIACHI"));
                tk.setVaiTro_TK(rs.getString("VAITRO"));
                tk.setNgay_DK_TK(rs.getDate("NGAYDANGKY"));
                tk.setAnh_TK(rs.getString("ANH_TK"));
                tk.setTrangThai_TK(rs.getBoolean("TRANGTHAI"));

                ketQua.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QL_TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ketQua;
    }

    public List<Tai_Khoan> TimKiem_TheoVaiTro(String vaiTro) {
        List<Tai_Khoan> ketQua = new ArrayList<>();
        String SQL = "SELECT MA_TK, TENTAIKHOAN, SDT, EMAIL, DIACHI, VAITRO, NGAYDANGKY, ANH_TK, TRANGTHAI "
                + "FROM TAIKHOAN WHERE VAITRO = ?";

        try (
                Connection connect = conn.DBConnect(); PreparedStatement pstm = connect.prepareStatement(SQL)) {
            pstm.setString(1, vaiTro);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Tai_Khoan tk = new Tai_Khoan();
                tk.setMa_TK(rs.getString("MA_TK"));
                tk.setTen_TK(rs.getString("TENTAIKHOAN"));
                tk.setSDT_TK(rs.getString("SDT"));
                tk.setEmail_TK(rs.getString("EMAIL"));
                tk.setDiaChi_TK(rs.getString("DIACHI"));
                tk.setVaiTro_TK(rs.getString("VAITRO"));
                tk.setNgay_DK_TK(rs.getDate("NGAYDANGKY"));
                tk.setAnh_TK(rs.getString("ANH_TK"));
                tk.setTrangThai_TK(rs.getBoolean("TRANGTHAI"));

                ketQua.add(tk);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    // Lấy Ảnh Của Tài Khoản Để Cho Vào Cái Phần Ảnh Trên Trang Chủ Của Tài Khoản
    public String layDuongDanAnhTaiKhoan(String maTK) {
        String sql = "SELECT ANH_TK FROM TAIKHOAN WHERE MA_TK = ?";
        try {
            Connection connection = conn.DBConnect();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maTK);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("ANH_TK"); // Trả về đường dẫn ảnh
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi lấy đường dẫn ảnh: " + e.getMessage());
        }
        return null;
    }

    // Lấy Thông Tin Tài Khoản Khi Mình Đăng Nhập
    public Tai_Khoan layThongTinTaiKhoan(String maTK) {
        String sql = "SELECT TENTAIKHOAN , SDT , EMAIL , DIACHI , VAITRO , NGAYDANGKY , ANH_TK , TRANGTHAI FROM TAIKHOAN WHERE MA_TK = ?";
        try {
            Connection connection = conn.DBConnect();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maTK);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String tenTK = rs.getString("TENTAIKHOAN");
                String sdt = rs.getString("SDT");
                String email = rs.getString("EMAIL");
                String diaChi = rs.getString("DIACHI");
                String vaiTro = rs.getString("VAITRO");
                Date ngayDK = rs.getDate("NGAYDANGKY");
                String anhTK = rs.getString("ANH_TK");
                boolean trangThai = rs.getBoolean("TRANGTHAI");

                return new Tai_Khoan(maTK, tenTK, sdt, email, diaChi, vaiTro, ngayDK, anhTK, trangThai);
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi lấy thông tin tài khoản: " + e.getMessage());
        }
        return null;
    }

    public int CapNhat_ThongtinCaNhan(Tai_Khoan tk, String Ma_Cu) {
        String sql = "UPDATE TAIKHOAN SET \n"
                + "       MA_TK = ?, TENTAIKHOAN = ?, SDT = ?, EMAIL = ?, DIACHI = ?, \n"
                + "       VAITRO = ?, NGAYDANGKY = ?, ANH_TK = ?, TRANGTHAI = ? \n"
                + "       WHERE MA_TK = ?";
        try {
            Connection connection = conn.DBConnect();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tk.getMa_TK());
            ps.setString(2, tk.getTen_TK());
            ps.setString(3, tk.getSDT_TK());
            ps.setString(4, tk.getEmail_TK());
            ps.setString(5, tk.getDiaChi_TK());
            ps.setString(6, tk.getVaiTro_TK());
            ps.setDate(7, tk.getNgay_DK_TK());
            ps.setString(8, tk.getAnh_TK());
            ps.setBoolean(9, tk.getTrangThai_TK());
            ps.setString(10, Ma_Cu); // Mã tài khoản cũ để xác định dòng cần sửa

            if (ps.executeUpdate() > 0) {
                return 1;
            }
            // Trả về số dòng bị ảnh hưởng (1 nếu thành công)
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi cập nhật tài khoản: " + e.getMessage());
        }
        return 0;
    }

    // Lấy Tên Tài Khoản Qua Mã Tài Khoản
    public static String getTenTaiKhoanTheoMa(String maTK) {
        String tenTaiKhoan = null;
        String sql = "SELECT TENTAIKHOAN FROM TAIKHOAN WHERE MA_TK = ?";

        try  {
            MyConnection conn;
            conn = new MyConnection();
            Connection connection = conn.DBConnect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maTK);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tenTaiKhoan = rs.getString("TENTAIKHOAN");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log lỗi
        }

        return tenTaiKhoan;
    }

}
