/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ToanBo_TaiKhoan;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QL_ChiTet_TaiKhoan_Panel extends javax.swing.JPanel {

    // Tìm KIẾM
    private JPanel pnlGoiY;
    private List<Tai_Khoan> danhSachTaiKhoan; // đã load từ DB
    private DefaultTableModel model;
    DefaultTableModel TableModel_TatCa;
    DefaultTableModel TableModel_QuanLy;
    DefaultTableModel TableModel_NhanVien;
    QL_TaiKhoan qltk = new QL_TaiKhoan();
    private QL_TaiKhoan_TatCa_JFrame QL_TJK = null;

    /**
     * Creates new form QL_ChiTet_TaiKhoan_Panel
     */
    public QL_ChiTet_TaiKhoan_Panel() {
        initComponents();
        Initable_TatCa_TaiKhoan();
        FillToTable_TatCa_TaiKhoan();

        Initable_QuanLy_TaiKhoan();
        FillToTable_QuanLy_TaiKhoan();

        Initable_NhanVien_TaiKhoan();
        FillToTable_NhanVien_TaiKhoan();
        pnlGoiY = new JPanel();
        pnlGoiY.setLayout(new BoxLayout(pnlGoiY, BoxLayout.Y_AXIS));
        pnlGoiY.setVisible(false);
        rdo_NhanVien.addActionListener(e -> capNhatBangTheoVaiTro("Nhân Viên"));
        rdo_QuanLy.addActionListener(e -> capNhatBangTheoVaiTro("Quản Lý"));
        // Tìm Kiếm
        txt_TimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                xuLyTimKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                xuLyTimKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                xuLyTimKiem();
            }
        });
    }

    // Giúp Sử Lý Phần Tìm Kiếm
    private void xuLyTimKiem() {
        String tuKhoa = txt_TimKiem.getText().trim();

        capNhatGoiY(); // hiển thị gợi ý bên dưới
        capNhatBangKetQua(tuKhoa); // cập nhật bảng kết quả
    }

    // Tất Cả Tài Khoản 
    public void Initable_TatCa_TaiKhoan() {
        TableModel_TatCa = new DefaultTableModel();
        String[] cols = {"Mã Tài Khoản", "Tên Tài Khoản", "SĐT", "Email", "Địa Chỉ", "Vai Trò", "Ngày Đăng Ký", "Ảnh", "Trạng Thái"};
        TableModel_TatCa.setColumnIdentifiers(cols);
        tbl_TatCa.setModel(TableModel_TatCa);
    }

//    Hiển Thị Theo Vai Trò
    public void FillToTable_TatCa_TaiKhoan() {
        TableModel_TatCa.setRowCount(0);
        for (Tai_Khoan tk : qltk.Get_All()) {
            TableModel_TatCa.addRow(qltk.GetRow(tk));
        }
    }

    // Tài Khoản Người Quản Lý
    public void Initable_QuanLy_TaiKhoan() {
        TableModel_QuanLy = new DefaultTableModel();
        String[] cols = {"Mã Tài Khoản", "Tên Tài Khoản", "SĐT", "Email", "Địa Chỉ", "Ngày Đăng Ký", "Ảnh", "Trạng Thái"};
        TableModel_QuanLy.setColumnIdentifiers(cols);
        tbl_QuanLy.setModel(TableModel_QuanLy);
    }

//    Hiển Thị Theo Vai Trò
    public void FillToTable_QuanLy_TaiKhoan() {
        TableModel_QuanLy.setRowCount(0);
        for (Tai_Khoan_8_O tk : qltk.Get_All_QuanLy()) {
            TableModel_QuanLy.addRow(qltk.GetRow_QuanLy(tk));
        }
    }

    // Tài Khoản Người Nhân Viên
    public void Initable_NhanVien_TaiKhoan() {
        TableModel_NhanVien = new DefaultTableModel();
        String[] cols = {"Mã Tài Khoản", "Tên Tài Khoản", "SĐT", "Email", "Địa Chỉ", "Ngày Đăng Ký", "Ảnh", "Trạng Thái"};
        TableModel_NhanVien.setColumnIdentifiers(cols);
        tbl_NhanVien.setModel(TableModel_NhanVien);
    }

//    Hiển Thị Theo Vai Trò
    public void FillToTable_NhanVien_TaiKhoan() {
        TableModel_NhanVien.setRowCount(0);
        for (Tai_Khoan_8_O tk : qltk.Get_All_NhanVien()) {
            TableModel_NhanVien.addRow(qltk.GetRow_NhanVien(tk));
        }
    }

    // Gợi Ý tìm Kiếm
    private void capNhatGoiY() {
        String tuKhoa = txt_TimKiem.getText().trim();
        pnlGoiY.removeAll();

        if (tuKhoa.isEmpty()) {
            pnlGoiY.setVisible(false);
            return;
        }

        // 🔍 Gọi DAO để lấy danh sách gợi ý từ DB
        List<Tai_Khoan> ketQua = qltk.TimKiem_TaiKhoan(tuKhoa);

        for (Tai_Khoan tk : ketQua) {
            JLabel lblGoiY = new JLabel(tk.getSDT_TK() + " | " + tk.getTen_TK());
            lblGoiY.setFont(new Font("Arial", Font.PLAIN, 13));
            lblGoiY.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            lblGoiY.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txt_TimKiem.setText(tk.getTen_TK()); // hoặc tk.getSDT_TK()
                    pnlGoiY.setVisible(false);
                    // Gọi hàm hiển thị chi tiết nếu cần
                }
            });

            pnlGoiY.add(lblGoiY);
        }

        pnlGoiY.setVisible(pnlGoiY.getComponentCount() > 0);
        pnlGoiY.revalidate();
        pnlGoiY.repaint();
    }

    private void capNhatBangKetQua(String tuKhoa) {
        List<Tai_Khoan> ketQua = qltk.TimKiem_TaiKhoan(tuKhoa);

        TableModel_TatCa.setRowCount(0); // Xóa dữ liệu cũ

        for (Tai_Khoan tk : ketQua) {
            Object[] row = qltk.GetRow(tk); // Dùng hàm đã có
            TableModel_TatCa.addRow(row);
        }
    }

    private void capNhatBangTheoVaiTro(String vaiTro) {
        List<Tai_Khoan> ketQua = qltk.TimKiem_TheoVaiTro(vaiTro);
        TableModel_TatCa.setRowCount(0);

        for (Tai_Khoan tk : ketQua) {
            Object[] row = qltk.GetRow(tk);
            TableModel_TatCa.addRow(row);
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

        btg_VaiTro = new javax.swing.ButtonGroup();
        Chua_Table_Panel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_TatCa = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_QuanLy = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_NhanVien = new javax.swing.JTable();
        TimKiemTheoTen_Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_TimKiem = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rdo_NhanVien = new javax.swing.JRadioButton();
        rdo_QuanLy = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        btn_Tao_TatCa_TK = new javax.swing.JButton();
        btn_ReSet = new javax.swing.JButton();

        Chua_Table_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Tài Khoản"));

        tbl_TatCa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_TatCa);

        jTabbedPane1.addTab("Tất Cả Tài Khoản", jScrollPane2);

        tbl_QuanLy.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbl_QuanLy);

        jTabbedPane1.addTab("Quản Lý", jScrollPane3);

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
        jScrollPane4.setViewportView(tbl_NhanVien);

        jTabbedPane1.addTab("Nhân Viên", jScrollPane4);

        javax.swing.GroupLayout Chua_Table_PanelLayout = new javax.swing.GroupLayout(Chua_Table_Panel);
        Chua_Table_Panel.setLayout(Chua_Table_PanelLayout);
        Chua_Table_PanelLayout.setHorizontalGroup(
            Chua_Table_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        Chua_Table_PanelLayout.setVerticalGroup(
            Chua_Table_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );

        TimKiemTheoTen_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm Kiếm Tài Khoản"));

        jLabel1.setText("Vui Lòng Nhập :");

        txt_TimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout TimKiemTheoTen_PanelLayout = new javax.swing.GroupLayout(TimKiemTheoTen_Panel);
        TimKiemTheoTen_Panel.setLayout(TimKiemTheoTen_PanelLayout);
        TimKiemTheoTen_PanelLayout.setHorizontalGroup(
            TimKiemTheoTen_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimKiemTheoTen_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TimKiemTheoTen_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TimKiemTheoTen_PanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 233, Short.MAX_VALUE))
                    .addComponent(txt_TimKiem))
                .addContainerGap())
        );
        TimKiemTheoTen_PanelLayout.setVerticalGroup(
            TimKiemTheoTen_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimKiemTheoTen_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc Vai Trò Của Tài Khoản"));

        jLabel2.setText("Vui Lòng Chọn:");

        btg_VaiTro.add(rdo_NhanVien);
        rdo_NhanVien.setText("Nhân Viên");

        btg_VaiTro.add(rdo_QuanLy);
        rdo_QuanLy.setText("Quản Lý");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rdo_NhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(rdo_QuanLy)
                        .addGap(24, 24, 24))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_NhanVien)
                    .addComponent(rdo_QuanLy))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng Chính"));

        btn_Tao_TatCa_TK.setText("Tạo Tài Khoản Tất Cả");
        btn_Tao_TatCa_TK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tao_TatCa_TKActionPerformed(evt);
            }
        });

        btn_ReSet.setText("ReSet");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Tao_TatCa_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ReSet, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Tao_TatCa_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ReSet, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Chua_Table_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(TimKiemTheoTen_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TimKiemTheoTen_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(Chua_Table_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Tao_TatCa_TKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tao_TatCa_TKActionPerformed
        // TODO add your handling code here:
        if (QL_TJK == null || !QL_TJK.isDisplayable()) {
            QL_TJK = new QL_TaiKhoan_TatCa_JFrame();
            QL_TJK.setLocationRelativeTo(null);

            QL_TJK.setAlwaysOnTop(true);
            QL_TJK.setVisible(true);
            QL_TJK.toFront();
            QL_TJK.requestFocus();
            QL_TJK.setAlwaysOnTop(false);

            QL_TJK.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    QL_TJK = null;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    QL_TJK = null;
                }
            });
        } else {
            QL_TJK.toFront();
            QL_TJK.requestFocus();

            JOptionPane.showMessageDialog(null,
                    "⚠️ Cửa Sổ Tạo Tài Khoản Đã Mở.\nVui Lòng Hoàn Tất Thao Tác Trước Khi Mở Mới.");
        }

    }//GEN-LAST:event_btn_Tao_TatCa_TKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Chua_Table_Panel;
    private javax.swing.JPanel TimKiemTheoTen_Panel;
    private javax.swing.ButtonGroup btg_VaiTro;
    private javax.swing.JButton btn_ReSet;
    private javax.swing.JButton btn_Tao_TatCa_TK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdo_NhanVien;
    private javax.swing.JRadioButton rdo_QuanLy;
    private javax.swing.JTable tbl_NhanVien;
    private javax.swing.JTable tbl_QuanLy;
    private javax.swing.JTable tbl_TatCa;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}
