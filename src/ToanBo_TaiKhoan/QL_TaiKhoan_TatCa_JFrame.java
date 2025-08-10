/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ToanBo_TaiKhoan;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ADMIN
 */
public class QL_TaiKhoan_TatCa_JFrame extends javax.swing.JFrame {

    DefaultTableModel TableModel;
    QL_TaiKhoan qltk = new QL_TaiKhoan();
    int index = -1;
    String PathAnh = null;

    /**
     * Creates new form QL_TaiKhoan_TatCa_JFrame
     */
    public QL_TaiKhoan_TatCa_JFrame() {
        initComponents();
        Initable();
        FillToTable();
        txt_Ngay_DK.setEditable(false);
        LocalDate ngayHienTai = LocalDate.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Hiển thị vào textField
        txt_Ngay_DK.setText(ngayHienTai.format(dinhDang));
    }

    public void Initable() {
        TableModel = new DefaultTableModel();
        String[] cols = {"Mã Tài Khoản", "Tên Tài Khoản", "SĐT", "Email", "Địa Chỉ", "Vai Trò", "Ngày Đăng Ký", "Ảnh", "Trạng Thái"};
        TableModel.setColumnIdentifiers(cols);
        tbl_NhanVien.setModel(TableModel);
    }

    // Hiển Thị Tất Cả
    public void FillToTable() {
        TableModel.setRowCount(0);
        for (Tai_Khoan tk : qltk.Get_All()) {
            TableModel.addRow(qltk.GetRow(tk));
        }
    }

    // Làm Mới Dữ Liệu Tai Khoản
    public void LamMoi() {
        txt_Ma_TK.setText("");
        txt_Ten_TK.setText("");
        txt_SDT_TK.setText("");
        LocalDate ngayHienTai = LocalDate.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Hiển thị vào textField
        txt_Ngay_DK.setText(ngayHienTai.format(dinhDang));
        txt_Email.setText("");
        txt_DiaChi.setText("");
        btg_TrangThai.clearSelection();
        btg_VaiTro.clearSelection();
        lb_UpAnh.setIcon(null);      // Xóa hình ảnh đang hiển thị
        lb_UpAnh.setText("Null");    // Ghi lại chữ nếu muốn
        PathAnh = null;              // Đặt lại biến ảnh (tránh lưu nhầm)
        FillToTable();

        rdo_HoatDong.setSelected(true);
        rdo_QuanLy.setSelected(false);
        rdo_NhanVien.setSelected(false);

        // ✅ Mở lại chức năng sửa vai trò
        rdo_QuanLy.setEnabled(true);
        rdo_NhanVien.setEnabled(true);

        // ✅ Mở lại nút xóa
        btn_XoaDL.setEnabled(true);

    }

    // Thêm Dữ Liệu Tai Khoản
    public void ThemDL_TaiKhoan() {
        // Mã Tài Khoản
        String Ma_TK = txt_Ma_TK.getText();
        if (txt_Ma_TK.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Mã Tài Khoản không được để trống.");
            return;
        }
        // ✅ Kiểm tra độ dài
        if (Ma_TK.length() < 3) {
            JOptionPane.showMessageDialog(this, "⚠️ Mã Tài Khoản phải có ít nhất 3 ký tự!");
            return;
        }
        if (Ma_TK.length() > 15) {
            JOptionPane.showMessageDialog(this, "⚠️ Mã Tài Khoản không được vượt quá 15 ký tự!");
            return;
        }

        // ❌ Kiểm tra trùng mã
        for (Tai_Khoan tk : qltk.Get_All()) {
            if (tk.getMa_TK().equalsIgnoreCase(Ma_TK)) {
                JOptionPane.showMessageDialog(this,
                        "❌ Mã Tài Khoản Này Đã Tồn Tại! Xin Vui Lòng Nhập Mã Tài Khoản Khác Nhé.",
                        "Trùng Mã",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Tên Tài Khoản
        String Ten_TK = txt_Ten_TK.getText();
        if (txt_Ten_TK.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên Tài Khoản không được để trống.");
            return;
        }
        // ✅ Kiểm tra độ dài
        if (Ten_TK.length() < 5) {
            JOptionPane.showMessageDialog(this, "⚠️ Tên Tài Khoản phải có ít nhất 5 ký tự!");
            return;
        }

        // Số Điện Thoại Tài Khoản
        String SDT = txt_SDT_TK.getText();
        if (txt_SDT_TK.getText().trim().isEmpty() || txt_SDT_TK.getText().length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm đúng 10 chữ số.");
            return;
        }
        // Email Tài Khoản
        String Email = txt_Email.getText();
        if (txt_Email.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống.");
            return;
        }
        // Địa Chỉ Của Tài Khoản
        String DiaChi = txt_DiaChi.getText();
        if (txt_DiaChi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống.");
            return;
        }
        // Ảnh Của Tài Khoản
        String Anh_TK = PathAnh;

        // Vai trò là chuỗi: "Nhân Viên" hoặc "Quản Lý"
        String VaiTro = rdo_NhanVien.isSelected() ? "Nhân Viên" : "Quản Lý";
        if ((!rdo_NhanVien.isSelected() && !rdo_QuanLy.isSelected())) {
            JOptionPane.showMessageDialog(this, "Vai Trò Của Tài Khoản Không Được Để Trống.");
            return;
        }

        // Trạng thái boolean từ radio
        boolean TrangThai = rdo_HoatDong.isSelected();
        if (!rdo_HoatDong.isSelected() && !rdo_KhongHoatDong.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái.");
            return;
        }

        try {
            DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(txt_Ngay_DK.getText(), DinhDang);
            Date NgayDK = Date.valueOf(localDate);

            Tai_Khoan tk = new Tai_Khoan(Ma_TK, Ten_TK, SDT, Email, DiaChi, VaiTro, NgayDK, Anh_TK, TrangThai);
            int Result = qltk.Them_TK(tk);
            JOptionPane.showMessageDialog(this, Result == 1 ? "Thêm thành công!" : "Thêm thất bại!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi ngày đăng ký: " + e.getMessage());
        }
    }

    // Xoá Tài Khoản 
    public void Xoa_TK() {
        index = tbl_NhanVien.getSelectedRow();
        if (index >= 0) {
            String Ten = qltk.Get_All().get(index).getTen_TK();
            int Choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Xoá Tài Khoản"
                    + "\nTên:" + Ten, "Xác Nhận Xoá", JOptionPane.YES_NO_OPTION);
            if (Choice == JOptionPane.YES_OPTION) {
                String TheoMa = qltk.Get_All().get(index).getMa_TK();
                int ReSult = qltk.Xoa_TK(TheoMa);
                if (ReSult == 1) {
                    JOptionPane.showMessageDialog(this, "Xoá Tài Khoản: "
                            + "\nMã Tài Khoản: " + TheoMa
                            + "\nTên Tài Khoản: " + Ten
                            + "\n\nThành Công.");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Xoá Thất Bại.");
            return;
        }
    }

    // Sửa Tài Khoản
    public void Sua_TK() {
        index = tbl_NhanVien.getSelectedRow();
        if (index >= 0) {
            String Ma_TK = txt_Ma_TK.getText();
            String Ten_TK = txt_Ten_TK.getText();
            String SDT = txt_SDT_TK.getText();
            String Email = txt_Email.getText(); // Sửa chỗ này, bạn gán nhầm Email = SDT
            String DiaChi = txt_DiaChi.getText();
            String VaiTro = rdo_NhanVien.isSelected() ? "Nhân Viên" : "Quản Lý";

            // Chuyển ngày đăng ký từ chuỗi sang java.sql.Date
            try {
                DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(txt_Ngay_DK.getText(), DinhDang);
                Date NgayDK = Date.valueOf(localDate);

                // Trạng thái: true (1) = Đang hoạt động, false (0) = Không hoạt động
                String TrangThai = rdo_HoatDong.isSelected() ? "Đang Hoạt Động" : "Không Hoạt Động"; // Khi Muốn Đổi Thì Phải Chọn Một Trong Hai Là Hoạt Động Hay Không Hoạt Động
                boolean trangthai;
                String vaitro = "";
                if (vaitro.equalsIgnoreCase("Đang Hoạt Động")) {
                    trangthai = true;
                } else {
                    trangthai = false;
                }

                String Anh_TK = PathAnh;

                // Tạo đối tượng tài khoản
                Tai_Khoan tk = new Tai_Khoan(Ma_TK, Ten_TK, SDT, Email, DiaChi, VaiTro, NgayDK, Anh_TK, trangthai);
                String TheoMa = qltk.Get_All().get(index).getMa_TK();
                int ReSult = qltk.Sua_TK(tk, TheoMa);
                if (ReSult == 1) {
                    JOptionPane.showMessageDialog(this, "Sửa Tài Khoản:"
                            + "\nMã Cũ: " + TheoMa
                            + "\nMã Tài Khoản Mới: " + Ma_TK
                            + "\nThành Công.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xử lý ngày đăng ký: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Dữ Liệu Trong Bảng Để Sửa Tài Khoản.");
            return;
        }
    }

    public void ShowDetail_Tk() {
        index = tbl_NhanVien.getSelectedRow();
        if (index < 0) {
            return;
        }

        Tai_Khoan tk = qltk.Get_All().get(index);
        txt_Ma_TK.setText(tk.getMa_TK());
        txt_Ten_TK.setText(tk.getTen_TK());
        txt_SDT_TK.setText(tk.getSDT_TK());
        txt_Email.setText(tk.getEmail_TK());
        txt_DiaChi.setText(tk.getDiaChi_TK());

        // Định dạng ngày
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txt_Ngay_DK.setText(tk.getNgay_DK_TK().toLocalDate().format(dinhDang));

        // Trạng thái
        if (tk.getTrangThai_TK()) {
            rdo_HoatDong.setSelected(true);
        } else {
            rdo_KhongHoatDong.setSelected(true);
        }

        // Hiển thị VaiTrò
        // Hiển thị VaiTrò
        if (tk.getVaiTro_TK().equalsIgnoreCase("Quản Lý")) {
            rdo_QuanLy.setSelected(true);

            // 🔒 Khóa chức năng sửa vai trò
            rdo_QuanLy.setEnabled(false);
            rdo_NhanVien.setEnabled(false);

            // ❌ Khóa nút xóa
            btn_XoaDL.setEnabled(false);
        } else {
            rdo_NhanVien.setSelected(true);

            // ✅ Cho phép sửa vai trò
            rdo_QuanLy.setEnabled(true);
            rdo_NhanVien.setEnabled(true);

            // ✅ Cho phép xóa
            btn_XoaDL.setEnabled(true);
        }

        // Ảnh đại diện
        PathAnh = tk.getAnh_TK();
        if (PathAnh != null && !PathAnh.isEmpty()) {
            ImageIcon icon = new ImageIcon(PathAnh);
            Image img = icon.getImage().getScaledInstance(lb_UpAnh.getWidth(), lb_UpAnh.getHeight(), Image.SCALE_SMOOTH);
            lb_UpAnh.setIcon(new ImageIcon(img));
            lb_UpAnh.setText("");
        } else {
            lb_UpAnh.setIcon(null);
            lb_UpAnh.setText("Null");
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

        btg_TrangThai = new javax.swing.ButtonGroup();
        btg_VaiTro = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lb_UpAnh = new javax.swing.JLabel();
        txt_Ma_TK = new javax.swing.JTextField();
        txt_Ten_TK = new javax.swing.JTextField();
        txt_SDT_TK = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_DiaChi = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        rdo_HoatDong = new javax.swing.JRadioButton();
        rdo_KhongHoatDong = new javax.swing.JRadioButton();
        txt_Ngay_DK = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        btn_Chon_Anh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        rdo_NhanVien = new javax.swing.JRadioButton();
        rdo_QuanLy = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        btn_LamMoi = new javax.swing.JButton();
        btn_ThemDL = new javax.swing.JButton();
        btn_SuaDL = new javax.swing.JButton();
        btn_XoaDL = new javax.swing.JButton();
        Panel_DSTK = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_NhanVien = new javax.swing.JTable();
        btn_DongTrang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhập Thông Tin Tài Khoản"));

        jLabel2.setText("Mã Tài Khoản :");

        jLabel3.setText("Tên Tài Khoản :");

        jLabel4.setText("Số Điện Thoại :");

        jLabel5.setText("Địa Chỉ :");

        jLabel6.setText("Ngày Đăng Ký :");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lb_UpAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_UpAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_UpAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_UpAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_Ma_TK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_Ma_TK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Ma_TKActionPerformed(evt);
            }
        });

        txt_Ten_TK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_SDT_TK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_DiaChi.setColumns(20);
        txt_DiaChi.setRows(5);
        txt_DiaChi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txt_DiaChi);

        jLabel8.setText("Trạng Thái :");

        btg_TrangThai.add(rdo_HoatDong);
        rdo_HoatDong.setText("Đang Hoạt Động");

        btg_TrangThai.add(rdo_KhongHoatDong);
        rdo_KhongHoatDong.setText("Không Hoạt Động");

        txt_Ngay_DK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Email :");

        txt_Email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_Chon_Anh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Chon_Anh.png"))); // NOI18N
        btn_Chon_Anh.setText("Chọn Ảnh");
        btn_Chon_Anh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Chon_AnhActionPerformed(evt);
            }
        });

        jLabel1.setText("Vai Trò:");

        btg_VaiTro.add(rdo_NhanVien);
        rdo_NhanVien.setText("Nhân Viên");

        btg_VaiTro.add(rdo_QuanLy);
        rdo_QuanLy.setText("Quản Lý");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Ma_TK))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Ten_TK))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_SDT_TK))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Email)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdo_NhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 60, Short.MAX_VALUE))
                                    .addComponent(rdo_QuanLy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Ngay_DK)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rdo_KhongHoatDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdo_HoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Chon_Anh)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_Ma_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_Ten_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_SDT_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(8, 8, 8)
                                .addComponent(rdo_NhanVien)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdo_QuanLy)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_Ngay_DK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Chon_Anh)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_HoatDong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_KhongHoatDong)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thanh Chức Năng"));

        btn_LamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LamMoi_TK.png"))); // NOI18N
        btn_LamMoi.setText("Làm Mới");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        btn_ThemDL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Them_TK.png"))); // NOI18N
        btn_ThemDL.setText("Thêm Dữ Lệu");
        btn_ThemDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemDLActionPerformed(evt);
            }
        });

        btn_SuaDL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Sua_TK.png"))); // NOI18N
        btn_SuaDL.setText("Sửa Dữ Liệu");
        btn_SuaDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaDLActionPerformed(evt);
            }
        });

        btn_XoaDL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Delete_TK.png"))); // NOI18N
        btn_XoaDL.setText("Xoá Dữ Liệu");
        btn_XoaDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaDLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_LamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ThemDL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_SuaDL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_XoaDL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btn_ThemDL, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_SuaDL, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btn_XoaDL, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        Panel_DSTK.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh Sách Tất Cả Tài Khoản"));

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_NhanVien);

        javax.swing.GroupLayout Panel_DSTKLayout = new javax.swing.GroupLayout(Panel_DSTK);
        Panel_DSTK.setLayout(Panel_DSTKLayout);
        Panel_DSTKLayout.setHorizontalGroup(
            Panel_DSTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        Panel_DSTKLayout.setVerticalGroup(
            Panel_DSTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
        );

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_DSTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_DongTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_DongTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Panel_DSTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lb_UpAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_UpAnhMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lb_UpAnhMouseClicked

    private void txt_Ma_TKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Ma_TKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Ma_TKActionPerformed

    private void btn_Chon_AnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Chon_AnhActionPerformed
        // TODO add your handling code here:
        lb_UpAnh.setText("");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            PathAnh = file.getAbsolutePath(); // Gán lại cho biến toàn cục

            // Xử lý scale ảnh về đúng kích thước của lb_UpAnh
            ImageIcon icon = new ImageIcon(PathAnh);
            Image img = icon.getImage().getScaledInstance(lb_UpAnh.getWidth(), lb_UpAnh.getHeight(), Image.SCALE_SMOOTH);
            lb_UpAnh.setIcon(new ImageIcon(img));
            lb_UpAnh.setText(""); // Ẩn chữ nếu đang hiện "Null"
        }
    }//GEN-LAST:event_btn_Chon_AnhActionPerformed

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiActionPerformed
        // TODO add your handling code here:
        LamMoi();
    }//GEN-LAST:event_btn_LamMoiActionPerformed

    private void btn_ThemDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemDLActionPerformed
        // TODO add your handling code here:
        ThemDL_TaiKhoan();
        FillToTable();
    }//GEN-LAST:event_btn_ThemDLActionPerformed

    private void btn_SuaDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaDLActionPerformed
        // TODO add your handling code here:
        Sua_TK();
        LamMoi();
    }//GEN-LAST:event_btn_SuaDLActionPerformed

    private void btn_XoaDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaDLActionPerformed
        // TODO add your handling code here:
        Xoa_TK();
        LamMoi();
    }//GEN-LAST:event_btn_XoaDLActionPerformed

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        // TODO add your handling code here:
        ShowDetail_Tk();
    }//GEN-LAST:event_tbl_NhanVienMouseClicked

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
            java.util.logging.Logger.getLogger(QL_TaiKhoan_TatCa_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QL_TaiKhoan_TatCa_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QL_TaiKhoan_TatCa_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QL_TaiKhoan_TatCa_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QL_TaiKhoan_TatCa_JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_DSTK;
    private javax.swing.ButtonGroup btg_TrangThai;
    private javax.swing.ButtonGroup btg_VaiTro;
    private javax.swing.JButton btn_Chon_Anh;
    private javax.swing.JButton btn_DongTrang;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_SuaDL;
    private javax.swing.JButton btn_ThemDL;
    private javax.swing.JButton btn_XoaDL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_UpAnh;
    private javax.swing.JRadioButton rdo_HoatDong;
    private javax.swing.JRadioButton rdo_KhongHoatDong;
    private javax.swing.JRadioButton rdo_NhanVien;
    private javax.swing.JRadioButton rdo_QuanLy;
    private javax.swing.JTable tbl_NhanVien;
    private javax.swing.JTextArea txt_DiaChi;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Ma_TK;
    private javax.swing.JTextField txt_Ngay_DK;
    private javax.swing.JTextField txt_SDT_TK;
    private javax.swing.JTextField txt_Ten_TK;
    // End of variables declaration//GEN-END:variables
}
