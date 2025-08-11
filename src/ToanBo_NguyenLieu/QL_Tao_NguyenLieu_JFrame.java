/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ToanBo_NguyenLieu;

import ToanBo_NguyenLieu.NguyenLieu;
import javax.swing.table.DefaultTableModel;
import ToanBo_NguyenLieu.QL_NguyenLieu;
import java.awt.Image;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.Color;
/**
 *
 * @author ADMIN
 */
public class QL_Tao_NguyenLieu_JFrame extends javax.swing.JFrame {

    DefaultTableModel TableModel;
    QL_NguyenLieu qlnl = new QL_NguyenLieu();
    int Index = -1;
    String PathAnh = null;

    /**
     * Creates new form QL_Tao_NguyenLieu_JFrame
     */
    public QL_Tao_NguyenLieu_JFrame() {
        initComponents();
        Initable();
        FillToTable();
        LocalDate ngayHienTai = LocalDate.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Hiển thị vào textField
        txt_NgayNhap.setText(ngayHienTai.format(dinhDang));
        txt_NgayNhap.setEnabled(false);
    }

    public void Initable() {
        TableModel = new DefaultTableModel();
        String[] cols = {"Mã NL", "Tên NL", "Số Lượng", "Đơn Vị Tính", "Giá Nhập", "Ngày Nhập", "Ảnh NL"};
        TableModel.setColumnIdentifiers(cols);
        tbl_NguyenLieu.setModel(TableModel);
    }

    // Hiển Thị Tất Cả
    public void FillToTable() {
        TableModel.setRowCount(0);
        for (NguyenLieu nl : qlnl.Get_All()) {
            TableModel.addRow(qlnl.GetRow(nl));
        }
    }

    public void LamMoi_NL() {
        txt_MaNL.setText("");
        txt_TenNL.setText("");
        txt_SoLuong.setText("");
        txt_DonViTinh.setText("");
        txt_GiaNhap.setText("");
        LocalDate ngayHienTai = LocalDate.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Hiển thị vào textField
        txt_NgayNhap.setText(ngayHienTai.format(dinhDang));
        lb_HienAnh.setIcon(null);      // Xóa hình ảnh đang hiển thị
        lb_HienAnh.setText("Null");    // Ghi lại chữ nếu muốn
        PathAnh = null;
    }

    public void Them_NL() {
        String Ma_NL = txt_MaNL.getText();
        // ❌ Không để trống
        if (Ma_NL.isEmpty()) {
            txt_MaNL.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "⛔ Mã Nguyên Liệu không được để trống!");
            return;
        }
        // ✅ Kiểm tra độ dài
        if (Ma_NL.length() < 3) {
            txt_MaNL.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "⚠️ Mã Nguyên Liệu phải có ít nhất 3 ký tự!");
            return;
        }
        if (Ma_NL.length() > 20) {
            txt_MaNL.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "⚠️ Mã Nguyên Liệu không được vượt quá 20 ký tự!");
            return;
        }
        for (NguyenLieu cnl : qlnl.Get_All()) {
            if (cnl.getMa_NL().equalsIgnoreCase(Ma_NL)) {
                txt_MaNL.setBackground(java.awt.Color.red);
                JOptionPane.showMessageDialog(this,
                        "❌ Mã Nguyên Liệu Này Đã Tồn Tại! Vui Lòng Nhập Mã Nguyên Liệu  Khách.",
                        "Trùng mã",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        txt_MaNL.setBackground(java.awt.Color.WHITE);

        String Ten_NL = txt_TenNL.getText();
        // Kiểm Tra Để Trống
        if (Ten_NL.isEmpty()) {
            txt_TenNL.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "⛔ Tên Nguyên Liệu không được để trống!");
            return;
        }
        // ✅ Kiểm tra độ dài
        if (Ten_NL.trim().length() < 5) {
            txt_TenNL.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "⚠️ Tên Nguyên Liệu phải có ít nhất 5 ký tự!");
            return;
        }

        if (Ten_NL.length() > 100) {
            txt_MaNL.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "⚠️ Tên Nguyên Liệu không được vượt quá 100 ký tự!");
            return;
        }
        txt_TenNL.setBackground(java.awt.Color.WHITE);

        int Soluong_NL;
        String slText = txt_SoLuong.getText().trim();

        // ?️ Kiểm tra trống
        if (slText.isEmpty()) {
            txt_SoLuong.setBackground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Số Lượng Nguyên Liệu Không Được Để Trống.");
            return;
        }

        // 🛡️ Kiểm tra chỉ chứa số nguyên dương (không chữ, không ký tự đặc biệt, không 0)
        if (!slText.matches("\\d+")) {
            txt_SoLuong.setBackground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Số Lượng Phải Là Số Nguyên Dương Không Chứa Ký Tự Đặc Biệt!");
            return;
        }
        try {
            Soluong_NL = Integer.parseInt(slText);
        } catch (Exception e) {
            txt_SoLuong.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "❌ Số Lượng Nguyên Liệu Phải Là Số Nguyên!");
            return;
        }

        if (Soluong_NL <= 0) {
            txt_SoLuong.setBackground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Số Lượng Nguyên Liệu Phải Lớn Hơn 0!");
            return;
        }
        if (Soluong_NL > 10000) {
            txt_SoLuong.setBackground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Bạn Đã Nhập Số Lượng Nguyên Liệu Vượt Quá 10000!");
            return;
        }

        txt_SoLuong.setBackground(java.awt.Color.WHITE); // ✅ Đúng chuẩn ➜ reset màu

        String DonViTinh_NL = txt_DonViTinh.getText();
        if (DonViTinh_NL.isEmpty()) {
            txt_DonViTinh.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "Phần Đon Vị Tính Của Nguyên Liệu Vui Lòng Không Để Trống.");
            return;
        }
        txt_DonViTinh.setBackground(java.awt.Color.WHITE);

        float GiaNhap_NL = Float.valueOf(txt_GiaNhap.getText());
        if (txt_GiaNhap.getText().isEmpty()) {
            txt_DonViTinh.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "Phần Giá Nhập Của Nguyên Liệu Vui Lòng Không Được Để Trống.");
            return;
        }

        if (GiaNhap_NL <= 0) {
            txt_GiaNhap.setBackground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Giá Nhập Nguyên Liệu Phải Lớn Hơn 0!");
            return;
        }
        DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(txt_NgayNhap.getText(), DinhDang);
        Date NgayDK = Date.valueOf(localDate);
        String Anh_NL = PathAnh;
        if (PathAnh.isEmpty()) {
            lb_HienAnh.setBackground(java.awt.Color.red);
            JOptionPane.showMessageDialog(this, "Ảnh Nguyên Liệu Không ĐƯợc Để Trống.");
            return;
        }
        lb_HienAnh.setBackground(java.awt.Color.WHITE);

        NguyenLieu nl = new NguyenLieu(Ma_NL, Ten_NL, DonViTinh_NL, Soluong_NL, GiaNhap_NL, NgayDK, Anh_NL);
        int ReSult = qlnl.Them_NL(nl);
        if (ReSult == 1) {
            JOptionPane.showMessageDialog(this, "Thêm Thông Tin Nguyên Liệu:"
                    + "\n Mã Nguyên Liệu: " + Ma_NL
                    + "\n Tên Nguyên Liệu: " + Ten_NL
                    + "\n Số Lượng Nhập Vào: " + Soluong_NL
                    + "\n Đơn Vị Tính: " + DonViTinh_NL
                    + "\n Giá Nhập Nguyên Liệu: " + GiaNhap_NL
                    + "\n Thành Công.");
            FillToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm Nguyên Liệu Thất Bại.");
            return;
        }
    }

    public void Sua_NL() {
        int index = tbl_NguyenLieu.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "⛔ Vui lòng chọn nguyên liệu để sửa.");
            return;
        }

        // 🧠 Mã nguyên liệu
        String Ma_NL = txt_MaNL.getText().trim();
        if (Ma_NL.isEmpty() || Ma_NL.length() < 3 || Ma_NL.length() > 20) {
            txt_MaNL.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Mã nguyên liệu phải từ 3 đến 20 ký tự.");
            return;
        }
        txt_MaNL.setBackground(Color.WHITE);

        // 🧠 Tên nguyên liệu
        String Ten_NL = txt_TenNL.getText().trim();
        if (Ten_NL.isEmpty() || Ten_NL.length() < 5 || Ten_NL.length() > 100) {
            txt_TenNL.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Tên nguyên liệu phải từ 5 đến 100 ký tự.");
            return;
        }
        txt_TenNL.setBackground(Color.WHITE);

        // 🧠 Số lượng
        String slText = txt_SoLuong.getText().trim();
        int SoLuong_NL;
        if (slText.isEmpty() || !slText.matches("\\d+")) {
            txt_SoLuong.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Số lượng phải là số nguyên dương.");
            return;
        }
        SoLuong_NL = Integer.parseInt(slText);
        if (SoLuong_NL <= 0 || SoLuong_NL > 10000) {
            txt_SoLuong.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Số lượng phải từ 1 đến 10.000.");
            return;
        }
        txt_SoLuong.setBackground(Color.WHITE);

        // 🧠 Đơn vị tính
        String DonViTinh_NL = txt_DonViTinh.getText().trim();
        if (DonViTinh_NL.isEmpty()) {
            txt_DonViTinh.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Đơn vị tính không được để trống.");
            return;
        }
        txt_DonViTinh.setBackground(Color.WHITE);

        // 🧠 Giá nhập
        String giaNhapStr = txt_GiaNhap.getText().trim();
        float GiaNhap_NL;
        if (giaNhapStr.isEmpty()) {
            txt_GiaNhap.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Giá nhập không được để trống.");
            return;
        }
        try {
            GiaNhap_NL = Float.parseFloat(giaNhapStr);
            if (GiaNhap_NL <= 0) {
                txt_GiaNhap.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "❌ Giá nhập phải lớn hơn 0.");
                return;
            }
        } catch (NumberFormatException e) {
            txt_GiaNhap.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Giá nhập không hợp lệ.");
            return;
        }
        txt_GiaNhap.setBackground(Color.WHITE);

        // 🧠 Ngày nhập
        String chuoiNgay = txt_NgayNhap.getText().trim();
        Date NgayNhap_NL;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(chuoiNgay, formatter);
            NgayNhap_NL = Date.valueOf(localDate);
        } catch (Exception e) {
            txt_NgayNhap.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Ngày nhập không hợp lệ. Định dạng cần là yyyy-MM-dd.");
            return;
        }
        txt_NgayNhap.setBackground(Color.WHITE);

        // 🧠 Ảnh nguyên liệu
        if (PathAnh == null || PathAnh.isEmpty()) {
            lb_HienAnh.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "❌ Vui lòng chọn ảnh nguyên liệu.");
            return;
        }
        lb_HienAnh.setBackground(Color.WHITE);

        // 🏗️ Tạo đối tượng và xử lý cập nhật
        NguyenLieu nl = new NguyenLieu(Ma_NL, Ten_NL, DonViTinh_NL, SoLuong_NL, GiaNhap_NL, NgayNhap_NL, PathAnh);
        String MaCu = qlnl.Get_All().get(index).getMa_NL();
        int result = qlnl.Sua_NL(nl, MaCu);

        if (result == 1) {
            FillToTable();
            JOptionPane.showMessageDialog(this,
                    "✅ Sửa nguyên liệu thành công!\n"
                    + "• Mã cũ: " + MaCu + "\n"
                    + "• Mã mới: " + Ma_NL + "\n"
                    + "• Tên mới: " + Ten_NL);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Sửa thất bại. Vui lòng kiểm tra lại!");
        }
    }

    public void Xoa_NL() {
        Index = tbl_NguyenLieu.getSelectedRow();
        if (Index >= 0) {
            String TheoMa = qlnl.Get_All().get(Index).getMa_NL();
            String Ten = qlnl.Get_All().get(Index).getTen_NL();
            int Choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Xoá Nguyên Liệu:"
                    + "\n Mã Nguyên Liệu: " + TheoMa
                    + "\n Tên Nguyên Liệu: " + Ten, "Xác Nhận Xoá Nguyên Liệu.", JOptionPane.YES_NO_OPTION);
            if (Choice == JOptionPane.YES_OPTION) {
                int Result = qlnl.Xoa_NL(TheoMa);
                if (Result == 1) {
                    FillToTable();
                    JOptionPane.showMessageDialog(this, "Xoá Nguyên Liệu:"
                            + "\n Mã Nguyên Liệu: " + TheoMa
                            + "\n Tên Nguyên Liệu: " + Ten
                            + "\n Thành Công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá Nguyên Liệu Thất Bại.");
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Thông Tin Nguyên Liệu Ở Trong Bảng.");
            return;
        }
    }

    public void ShowDetail() {
        int Index = tbl_NguyenLieu.getSelectedRow();
        if (Index < 0) {
            return;
        }

        NguyenLieu nl = qlnl.Get_All().get(Index);

        // ✏️ Gán dữ liệu cơ bản vào các ô
        txt_MaNL.setText(nl.getMa_NL());
        txt_TenNL.setText(nl.getTen_NL());
        txt_SoLuong.setText(String.valueOf(nl.getSoLuongTon_NL()));
        txt_DonViTinh.setText(nl.getDonViTinh_NL());
        txt_GiaNhap.setText(String.valueOf(nl.getGiaNhap_NL()));

        // 📅 Format ngày nhập
        Date ngayNhap = nl.getNgayNhap_NL();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txt_NgayNhap.setText(ngayNhap != null ? sdf.format(ngayNhap) : "Chưa có");

        // 🖼️ Hiển thị ảnh nguyên liệu
        String path = nl.getAnh_NL();
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(
                        lb_HienAnh.getWidth(),
                        lb_HienAnh.getHeight(),
                        Image.SCALE_SMOOTH
                );
                lb_HienAnh.setIcon(new ImageIcon(img));
                lb_HienAnh.setText("");
            } else {
                lb_HienAnh.setIcon(null);
                lb_HienAnh.setText("Ảnh không tồn tại");
            }
        } else {
            lb_HienAnh.setIcon(null);
            lb_HienAnh.setText("Chưa có ảnh");
        }

        // ✅ Reset màu các ô nhập
        JTextField[] fields = {
            txt_MaNL, txt_TenNL, txt_SoLuong,
            txt_DonViTinh, txt_GiaNhap, txt_NgayNhap
        };
        for (JTextField f : fields) {
            f.setBackground(java.awt.Color.WHITE);
        }
    }

    // Xuất Nguyên Liệu Bằng File Excel
    public void xuatBangNguyenLieu(List<NguyenLieu> danhSach, String duongDanFile) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Bảng Nguyen Liệu");

            // 🎯 Dòng tiêu đề
            Row tieuDe = sheet.createRow(0);
            Cell cellTieuDe = tieuDe.createCell(0);
            cellTieuDe.setCellValue("Bảng Nguyên Liệu");

            // 👉 Gộp tiêu đề qua 7 cột
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            // 📋 Header (dòng 1)
            Row header = sheet.createRow(1);
            String[] cot = {"Mã NL", "Tên NL", "Đơn Vị Tính", "Số Lượng", "Giá Nhập", "Ngày Nhập", "Ảnh NL"};
            for (int i = 0; i < cot.length; i++) {
                header.createCell(i).setCellValue(cot[i]);
            }

            // 📦 Dữ liệu bắt đầu từ dòng 2
            int rowNum = 2;
            for (NguyenLieu nl : danhSach) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nl.getMa_NL());
                row.createCell(1).setCellValue(nl.getTen_NL());
                row.createCell(2).setCellValue(nl.getSoLuongTon_NL());
                row.createCell(3).setCellValue(nl.getDonViTinh_NL());
                row.createCell(4).setCellValue(nl.getGiaNhap_NL());
                row.createCell(5).setCellValue(nl.getNgayNhap_NL().toString());
                row.createCell(6).setCellValue(nl.getAnh_NL()); // Đường dẫn hoặc tên ảnh
            }

            // 💾 Lưu ra file Excel
            try (FileOutputStream fos = new FileOutputStream(duongDanFile)) {
                workbook.write(fos);
            }

            JOptionPane.showMessageDialog(null,
                    "✅ Đã xuất file Excel thành công!\nTên: Bảng Nguyen Liệu",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Xuất Excel lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hiện Dữ Liệu
    public void showToTable(List<NguyenLieu> ds) {
        DefaultTableModel model = (DefaultTableModel) tbl_NguyenLieu.getModel();
        model.setRowCount(0);

        for (NguyenLieu nl : ds) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ngayNhapStr = (nl.getNgayNhap_NL() != null)
                    ? sdf.format(nl.getNgayNhap_NL())
                    : "Chưa có";

            String anhNL = (nl.getAnh_NL() != null) ? nl.getAnh_NL() : "Không có ảnh";

            model.addRow(new Object[]{
                nl.getMa_NL(), nl.getTen_NL(), nl.getSoLuongTon_NL(), nl.getDonViTinh_NL(),
                nl.getGiaNhap_NL(), ngayNhapStr, anhNL
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_MaNL = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_TenNL = new javax.swing.JTextField();
        txt_SoLuong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_DonViTinh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_GiaNhap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_NgayNhap = new javax.swing.JTextField();
        ChuaAnh_Panel = new javax.swing.JPanel();
        lb_HienAnh = new javax.swing.JLabel();
        btn_ChonAnh = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_NguyenLieu = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btn_LamMoi = new javax.swing.JButton();
        btn_ThemNL = new javax.swing.JButton();
        btn_SuaNL = new javax.swing.JButton();
        btn_XoaNL = new javax.swing.JButton();
        btn_XuatExcel = new javax.swing.JButton();
        btn_NhapExcel = new javax.swing.JButton();
        btn_DongTrang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin Nguyên Liệu"));

        jLabel1.setText("Mã NL :");

        txt_MaNL.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setText("Tên NL :");

        txt_TenNL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_SoLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Số Lượng :");

        txt_DonViTinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Đơn Vị Tính :");

        jLabel5.setText("Giá Nhập :");

        txt_GiaNhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("Ngày Nhập :");

        txt_NgayNhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ChuaAnh_Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lb_HienAnh.setText("jLabel7");

        javax.swing.GroupLayout ChuaAnh_PanelLayout = new javax.swing.GroupLayout(ChuaAnh_Panel);
        ChuaAnh_Panel.setLayout(ChuaAnh_PanelLayout);
        ChuaAnh_PanelLayout.setHorizontalGroup(
            ChuaAnh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChuaAnh_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_HienAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );
        ChuaAnh_PanelLayout.setVerticalGroup(
            ChuaAnh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChuaAnh_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_HienAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_ChonAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/ChonAnh_NguyenLieu.png"))); // NOI18N
        btn_ChonAnh.setText("Choose Ảnh");
        btn_ChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChonAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_MaNL, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(txt_TenNL)
                    .addComponent(txt_SoLuong)
                    .addComponent(txt_DonViTinh)
                    .addComponent(txt_GiaNhap)
                    .addComponent(txt_NgayNhap))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChuaAnh_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ChonAnh))
                .addGap(28, 28, 28))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ChuaAnh_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_ChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_MaNL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_TenNL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_DonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_GiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_NgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Nguyên Liệu"));

        tbl_NguyenLieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        tbl_NguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NguyenLieuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_NguyenLieu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng Chính"));

        btn_LamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LamMoi_NguyenLieu.png"))); // NOI18N
        btn_LamMoi.setText("Làm Mới");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        btn_ThemNL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Them_NguyenLieu.png"))); // NOI18N
        btn_ThemNL.setText("Thêm NL");
        btn_ThemNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNLActionPerformed(evt);
            }
        });

        btn_SuaNL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Sua_NguyenLieu.png"))); // NOI18N
        btn_SuaNL.setText("Sửa NL");
        btn_SuaNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaNLActionPerformed(evt);
            }
        });

        btn_XoaNL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Xoa_NguyenLieu.png"))); // NOI18N
        btn_XoaNL.setText("Xoá NL");
        btn_XoaNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaNLActionPerformed(evt);
            }
        });

        btn_XuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/XuatFileExcel_NguyenLieu.png"))); // NOI18N
        btn_XuatExcel.setText("Xuất File Excel");
        btn_XuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XuatExcelActionPerformed(evt);
            }
        });

        btn_NhapExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/NhapFlieExcel_NguyenLieu.png"))); // NOI18N
        btn_NhapExcel.setText("Nhập File Excel");
        btn_NhapExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NhapExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_NhapExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_XuatExcel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(btn_ThemNL, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                    .addComponent(btn_LamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_XoaNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_SuaNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(9, 9, 9))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_SuaNL, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btn_LamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_ThemNL, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btn_XoaNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_NhapExcel)
                .addGap(18, 18, 18)
                .addComponent(btn_XuatExcel)
                .addContainerGap())
        );

        btn_DongTrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Dong_Trang.png"))); // NOI18N
        btn_DongTrang.setText("Đóng Trang");
        btn_DongTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DongTrangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_DongTrang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_DongTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChonAnhActionPerformed
        // TODO add your handling code here:
        lb_HienAnh.setText("");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            PathAnh = file.getAbsolutePath(); // Gán lại cho biến toàn cục

            // Xử lý scale ảnh về đúng kích thước của lb_UpAnh
            ImageIcon icon = new ImageIcon(PathAnh);
            Image img = icon.getImage().getScaledInstance(lb_HienAnh.getWidth(), lb_HienAnh.getHeight(), Image.SCALE_SMOOTH);
            lb_HienAnh.setIcon(new ImageIcon(img));
            lb_HienAnh.setText(""); // Ẩn chữ nếu đang hiện "Null"
        }
    }//GEN-LAST:event_btn_ChonAnhActionPerformed

    private void tbl_NguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NguyenLieuMouseClicked
        // TODO add your handling code here:
        ShowDetail();
    }//GEN-LAST:event_tbl_NguyenLieuMouseClicked

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiActionPerformed
        // TODO add your handling code here:
        LamMoi_NL();
        FillToTable();
    }//GEN-LAST:event_btn_LamMoiActionPerformed

    private void btn_ThemNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNLActionPerformed
        // TODO add your handling code here:
        Them_NL();
        FillToTable();
    }//GEN-LAST:event_btn_ThemNLActionPerformed

    private void btn_SuaNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaNLActionPerformed
        // TODO add your handling code here:
        Sua_NL();
        FillToTable();
    }//GEN-LAST:event_btn_SuaNLActionPerformed

    private void btn_XoaNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaNLActionPerformed
        // TODO add your handling code here:
        Xoa_NL();
        FillToTable();
    }//GEN-LAST:event_btn_XoaNLActionPerformed

    private void btn_XuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XuatExcelActionPerformed
        // TODO add your handling code here:
        List<NguyenLieu> danhSachNguyenLieu = qlnl.Get_All();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu Excel");
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Nếu người dùng không nhập đuôi .xlsx thì tự thêm:
            String duongDan = file.getAbsolutePath();
            if (!duongDan.endsWith(".xlsx")) {
                duongDan += ".xlsx";
            }

            xuatBangNguyenLieu(danhSachNguyenLieu, duongDan);
        }
    }//GEN-LAST:event_btn_XuatExcelActionPerformed

    private void btn_NhapExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NhapExcelActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file Excel để nhập nguyên liệu");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

        int ketQua = chooser.showOpenDialog(null);
        if (ketQua == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            List<NguyenLieu> danhSach = qlnl.importTuExcel(file); // DAO đã xử lý lưu + kiểm tra lỗi

            showToTable(danhSach); // Hiển thị lên bảng nếu cần
            JOptionPane.showMessageDialog(this,
                    "✅ Nhập Excel thành công: " + danhSach.size() + " nguyên liệu!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_NhapExcelActionPerformed

    private void btn_DongTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DongTrangActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_DongTrangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QL_Tao_NguyenLieu_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QL_Tao_NguyenLieu_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QL_Tao_NguyenLieu_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QL_Tao_NguyenLieu_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QL_Tao_NguyenLieu_JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChuaAnh_Panel;
    private javax.swing.JButton btn_ChonAnh;
    private javax.swing.JButton btn_DongTrang;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_NhapExcel;
    private javax.swing.JButton btn_SuaNL;
    private javax.swing.JButton btn_ThemNL;
    private javax.swing.JButton btn_XoaNL;
    private javax.swing.JButton btn_XuatExcel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_HienAnh;
    private javax.swing.JTable tbl_NguyenLieu;
    private javax.swing.JTextField txt_DonViTinh;
    private javax.swing.JTextField txt_GiaNhap;
    private javax.swing.JTextField txt_MaNL;
    private javax.swing.JTextField txt_NgayNhap;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JTextField txt_TenNL;
    // End of variables declaration//GEN-END:variables
}
