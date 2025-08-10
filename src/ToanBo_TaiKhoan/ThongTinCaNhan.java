/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ToanBo_TaiKhoan;

import DBConnect.Chua_Bien;
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
public class ThongTinCaNhan extends javax.swing.JFrame {

    QL_TaiKhoan qltk = new QL_TaiKhoan();
    int index = -1;
    String PathAnh = null;

    /**
     * Creates new form ThongTinCaNhan
     */
    public ThongTinCaNhan() {
        initComponents();

        // Cái Phần Ngày Tạo Phải Khoá
        txt_Ngay_DK.setEditable(false);
        // Cái Phần Trạng Thái
        rdo_HoatDong.setEnabled(false);
        rdo_KhongHoatDong.setEnabled(false);
        // Phần Lấy Thông Tin Cá Nhân
        String Ma_TK = Chua_Bien.Ma_TK;
        if (Ma_TK == null) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Đăng Nhập Để Xem Được Thông Tin Cá Nhân.");
            return;
        } else {
            Tai_Khoan tk = qltk.layThongTinTaiKhoan(Chua_Bien.Ma_TK);
            if (tk != null) {
                txt_Ma_TK.setText(tk.getMa_TK());
                txt_Ten_TK.setText(tk.getTen_TK());
                txt_SDT_TK.setText(tk.getSDT_TK());
                txt_Email.setText(tk.getEmail_TK());
                txt_DiaChi.setText(tk.getDiaChi_TK());

                DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                txt_Ngay_DK.setText(tk.getNgay_DK_TK().toLocalDate().format(dinhDang));

                if (tk.getTrangThai_TK()) {
                    rdo_HoatDong.setSelected(true);
                } else {
                    rdo_KhongHoatDong.setSelected(true);
                }

                if (tk.getVaiTro_TK().equalsIgnoreCase("Quản Lý")) {
                    rdo_QuanLy.setSelected(true);
                    rdo_QuanLy.setEnabled(false);
                    rdo_NhanVien.setEnabled(false);
                } else {
                    rdo_NhanVien.setSelected(true);
                    rdo_QuanLy.setEnabled(true);
                    rdo_NhanVien.setEnabled(true);
                }

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
        }
    }

    public void Sua_TK() {
        // Lấy dữ liệu từ giao diện
        String maTK = txt_Ma_TK.getText().trim();
        String tenTK = txt_Ten_TK.getText().trim();
        String sdt = txt_SDT_TK.getText().trim();
        String email = txt_Email.getText().trim();
        String diaChi = txt_DiaChi.getText().trim();
        String vaiTro = rdo_NhanVien.isSelected() ? "Nhân Viên" : "Quản Lý";
        String anhTK = PathAnh;
        boolean trangThai = rdo_HoatDong.isSelected(); // true nếu đang hoạt động

        try {
            // Chuyển chuỗi ngày sang java.sql.Date
            DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(txt_Ngay_DK.getText().trim(), dinhDang);
            Date ngayDK = Date.valueOf(localDate);

            // Tạo đối tượng tài khoản
            Tai_Khoan tk = new Tai_Khoan(maTK, tenTK, sdt, email, diaChi, vaiTro, ngayDK, anhTK, trangThai);

            // Mã tài khoản cũ để xác định dòng cần sửa
            String maCu = Chua_Bien.Ma_TK;

            // Gọi DAO để cập nhật
            int result = qltk.CapNhat_ThongtinCaNhan(tk, maCu);

            if (result == 1) {
                JOptionPane.showMessageDialog(this,
                        "✅ Sửa tài khoản thành công!"
                        + "\nMã cũ: " + maCu
                        + "\nMã mới: " + maTK);
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Không thể cập nhật tài khoản. Vui lòng kiểm tra lại.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Lỗi xử lý ngày đăng ký: " + e.getMessage());
        }
    }

//    public void ShowDetail_Tk() {
//       
//
//        Tai_Khoan tk = qltk.Get_All().get(index);
//        txt_Ma_TK.setText(tk.getMa_TK());
//        txt_Ten_TK.setText(tk.getTen_TK());
//        txt_SDT_TK.setText(tk.getSDT_TK());
//        txt_Email.setText(tk.getEmail_TK());
//        txt_DiaChi.setText(tk.getDiaChi_TK());
//
//        // Định dạng ngày
//        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        txt_Ngay_DK.setText(tk.getNgay_DK_TK().toLocalDate().format(dinhDang));
//
//        // Trạng thái
//        if (tk.getTrangThai_TK()) {
//            rdo_HoatDong.setSelected(true);
//        } else {
//            rdo_KhongHoatDong.setSelected(true);
//        }
//
//        // Hiển thị VaiTrò
//        // Hiển thị VaiTrò
//        if (tk.getVaiTro_TK().equalsIgnoreCase("Quản Lý")) {
//            rdo_QuanLy.setSelected(true);
//
//            // 🔒 Khóa chức năng sửa vai trò
//            rdo_QuanLy.setEnabled(false);
//            rdo_NhanVien.setEnabled(false);
//
//            // ❌ Khóa nút xóa
//            btn_XoaDL.setEnabled(false);
//        } else {
//            rdo_NhanVien.setSelected(true);
//
//            // ✅ Cho phép sửa vai trò
//            rdo_QuanLy.setEnabled(true);
//            rdo_NhanVien.setEnabled(true);
//
//            // ✅ Cho phép xóa
//            btn_XoaDL.setEnabled(true);
//        }
//
//        // Ảnh đại diện
//        PathAnh = tk.getAnh_TK();
//        if (PathAnh != null && !PathAnh.isEmpty()) {
//            ImageIcon icon = new ImageIcon(PathAnh);
//            Image img = icon.getImage().getScaledInstance(lb_UpAnh.getWidth(), lb_UpAnh.getHeight(), Image.SCALE_SMOOTH);
//            lb_UpAnh.setIcon(new ImageIcon(img));
//            lb_UpAnh.setText("");
//        } else {
//            lb_UpAnh.setIcon(null);
//            lb_UpAnh.setText("Null");
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btg_VaiTro = new javax.swing.ButtonGroup();
        btg_TrangThai = new javax.swing.ButtonGroup();
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
        btn_SuaDL = new javax.swing.JButton();
        btn_SuaDL1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Cá Nhân Của Tài Khoản"));

        jLabel2.setText("Mã Tài Khoản :");

        jLabel3.setText("Tên Tài Khoản :");

        jLabel4.setText("Số Điện Thoại :");

        jLabel5.setText("Địa Chỉ :");

        jLabel6.setText("Ngày Tạo Tk:");

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

        rdo_HoatDong.setText("Đang Hoạt Động");

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

        rdo_NhanVien.setText("Nhân Viên");

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

        btn_SuaDL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Sua_TK.png"))); // NOI18N
        btn_SuaDL.setText("Cập Nhật Thông Tin");
        btn_SuaDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaDLActionPerformed(evt);
            }
        });

        btn_SuaDL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Dong_Trang.png"))); // NOI18N
        btn_SuaDL1.setText("Đóng Trang");
        btn_SuaDL1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaDL1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_SuaDL, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_SuaDL1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_SuaDL, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_SuaDL1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
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

    private void btn_SuaDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaDLActionPerformed
        // TODO add your handling code here:
        Sua_TK();
    }//GEN-LAST:event_btn_SuaDLActionPerformed

    private void btn_SuaDL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaDL1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_SuaDL1ActionPerformed

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
            java.util.logging.Logger.getLogger(ThongTinCaNhan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongTinCaNhan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongTinCaNhan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongTinCaNhan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongTinCaNhan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btg_TrangThai;
    private javax.swing.ButtonGroup btg_VaiTro;
    private javax.swing.JButton btn_Chon_Anh;
    private javax.swing.JButton btn_SuaDL;
    private javax.swing.JButton btn_SuaDL1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_UpAnh;
    private javax.swing.JRadioButton rdo_HoatDong;
    private javax.swing.JRadioButton rdo_KhongHoatDong;
    private javax.swing.JRadioButton rdo_NhanVien;
    private javax.swing.JRadioButton rdo_QuanLy;
    private javax.swing.JTextArea txt_DiaChi;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Ma_TK;
    private javax.swing.JTextField txt_Ngay_DK;
    private javax.swing.JTextField txt_SDT_TK;
    private javax.swing.JTextField txt_Ten_TK;
    // End of variables declaration//GEN-END:variables
}
