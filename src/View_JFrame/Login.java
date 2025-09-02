/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View_JFrame;

import DBConnect.Chua_Bien;
import javax.swing.ImageIcon;
import ToanBo_TaiKhoan.QL_Login;
import ToanBo_TaiKhoan.Tai_Khoan;
import ToanBo_TaiKhoan.TaiKhoan_4_O;
import javax.swing.JOptionPane;
import View_JFrame.TrangChu_NQL;
import View_JFrame.TrangChu_NV;

/**
 *
 * @author ADMIN
 */
public class Login extends javax.swing.JFrame {

    QL_Login qllg = new QL_Login();

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.setLocationRelativeTo(this);
        tbtn_An_Hien.setIcon(iconHide);
        txt_NhapMK.setEchoChar('*'); // Ẩn mật khẩu
    }
    // Icon từ file
    ImageIcon iconHide = new ImageIcon(getClass().getResource("/Icon/Close_Eye.png"));
    ImageIcon iconShow = new ImageIcon(getClass().getResource("/Icon/Opend_Eye.png"));

//    public void DangNhap() {
//        String Email = txt_NhapEmail.getText().trim();
//        String Ma_TK = txt_NhapMK.getText().trim();
//        Chua_Bien.Ma_TK = Ma_TK;
//        String vaiTro = qllg.login(Email, Ma_TK);
    ////        TaiKhoan_4_O taiKhoan = qllg.getTaiKhoanTheoEmail(Email);
//        int Choice = JOptionPane.showConfirmDialog(this, "Bạn Có Xác Nhận Đăng Nhập Với:"
//                + "\n Mật Khẩu: " + Ma_TK
//                + "\n Email: " + Email
//                + "\n Hay Không ?", "Xác Nhận Đăng Nhập.", JOptionPane.YES_NO_OPTION);
//        if (Choice == JOptionPane.YES_OPTION) {
//            if (vaiTro != null) {
//                JOptionPane.showMessageDialog(this,
//                        "Đăng nhập thành công!"
//                        + "\nTài khoản: " + Email
//                        + "\nVai trò: " + vaiTro);
//
//                // Chuyển giao diện theo vai trò
//                switch (vaiTro) {
//                    case "Quản Lý":
//                        new TrangChu_NQL().setVisible(true);
//                        break;
//                    case "Nhân Viên":
//                        new TrangChu_NV().setVisible(true);
//                        break;
////            case "KH": new GiaoDienKhachHang().setVisible(true); break;
//                    default:
//                        JOptionPane.showMessageDialog(this, "Vai trò không xác định!");
//                }
//
//                this.dispose(); // Đóng form đăng nhập
//            } else {
//                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại! Email hoặc mã không đúng.");
//                return;
//            }
//        }
//    }
    public void DangNhap() {
        String Email = txt_NhapEmail.getText().trim();
        String Ma_TK = txt_NhapMK.getText().trim();
        Chua_Bien.Ma_TK = Ma_TK;

        String vaiTro = qllg.login(Email, Ma_TK);

        int Choice = JOptionPane.showConfirmDialog(this, "Bạn Có Xác Nhận Đăng Nhập Với:"
                + "\n Mật Khẩu: " + Ma_TK
                + "\n Email: " + Email
                + "\n Hay Không ?", "Xác Nhận Đăng Nhập.", JOptionPane.YES_NO_OPTION);

        if (Choice == JOptionPane.YES_OPTION) {
            if (vaiTro != null) {
                // ✅ Cập nhật trạng thái tài khoản thành "Đang hoạt động"
                int ketQua = qllg.capNhatTrangThai(Ma_TK, true); // 1 = Đang hoạt động
                if (ketQua <= 0) {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái tài khoản!");
                    return;
                }

                JOptionPane.showMessageDialog(this,
                        "Đăng nhập thành công!"
                        + "\nTài khoản: " + Email
                        + "\nVai trò: " + vaiTro);

                // Chuyển giao diện theo vai trò
                switch (vaiTro) {
                    case "Quản Lý":
                        new TrangChu_NQL().setVisible(true);
                        break;
                    case "Nhân Viên":
                        new TrangChu_NV().setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Vai trò không xác định!");
                }

                this.dispose(); // Đóng form đăng nhập
            } else {
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại! Email hoặc mã không đúng.");
            }
        }
    }

//    public void Test() {
//        
//        TaiKhoan_4_O taiKhoan = dao.getTaiKhoanTheoEmail(Email);
//
//        if (vaiTro != null) {
//            switch (vaiTro) {
//                case "Quản Lý":
//                    new TrangChu_NQL(taiKhoan).setVisible(true);
//                    break;
//                case "Nhân Viên":
//                    new TrangChu_NV(taiKhoan).setVisible(true);
//                    break;
//            }
//            this.dispose();
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_NhapEmail = new javax.swing.JTextField();
        txt_NhapMK = new javax.swing.JPasswordField();
        tbtn_An_Hien = new javax.swing.JToggleButton();
        btn_DangNhap = new javax.swing.JButton();
        bt_Cannel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        jLabel1.setText("Welcome to  Fake AL Fresco’s");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin"));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nhập Email :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nhập PassWord :");

        txt_NhapEmail.setBackground(new java.awt.Color(204, 204, 255));

        txt_NhapMK.setBackground(new java.awt.Color(204, 204, 255));

        tbtn_An_Hien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtn_An_HienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_NhapMK, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tbtn_An_Hien, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_NhapEmail))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_NhapEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_NhapMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tbtn_An_Hien, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btn_DangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Dang_Nhap_TK.png"))); // NOI18N
        btn_DangNhap.setText("Đăng Nhập");
        btn_DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DangNhapActionPerformed(evt);
            }
        });

        bt_Cannel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Cannel_Login.png"))); // NOI18N
        bt_Cannel.setText("Cannel");
        bt_Cannel.setPreferredSize(new java.awt.Dimension(113, 46));
        bt_Cannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_CannelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btn_DangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(bt_Cannel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_DangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Cannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Anh_Dang_nHAP.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CannelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_bt_CannelActionPerformed

    private void btn_DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DangNhapActionPerformed
        // TODO add your handling code here:
        DangNhap();
    }//GEN-LAST:event_btn_DangNhapActionPerformed

    private void tbtn_An_HienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtn_An_HienActionPerformed
        // TODO add your handling code here:
        if (tbtn_An_Hien.isSelected()) {
            tbtn_An_Hien.setIcon(iconShow); // hiện icon mắt mở
            txt_NhapMK.setEchoChar((char) 0); // hiện mật khẩu
        } else {
            tbtn_An_Hien.setIcon(iconHide); // hiện icon mắt đóng
            txt_NhapMK.setEchoChar('*'); // ẩn mật khẩu
        }
    }//GEN-LAST:event_tbtn_An_HienActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Cannel;
    private javax.swing.JButton btn_DangNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton tbtn_An_Hien;
    private javax.swing.JTextField txt_NhapEmail;
    private javax.swing.JPasswordField txt_NhapMK;
    // End of variables declaration//GEN-END:variables
}
