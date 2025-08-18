/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ToanBo_SanPham;

import javax.swing.table.DefaultTableModel;
import ToanBo_SanPham.QL_Tao_LoaiSanPham;
import ToanBo_SanPham.QL_Tao_SanPham;
import ToanBo_SanPham.LoaiSanPham;
import ToanBo_SanPham.SanPham;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author ADMIN
 */
public class QL_SanPham_ChiTiet_Panel extends javax.swing.JPanel {

    DefaultTableModel TableModel;
    DefaultTableModel TableModel_SP;
    QL_Tao_LoaiSanPham qllsp = new QL_Tao_LoaiSanPham(); // Quản Lý Loại Sản Phẩm
    QL_Tao_SanPham qlsp = new QL_Tao_SanPham(); // Quản Lý Sản Phẩm

    /**
     * Creates new form QLSP
     */
    public QL_SanPham_ChiTiet_Panel() {
        initComponents();
        Initable_LSP();
        FillToTable_LSP();
        Initable_SP();
        FillToTable_SP();
        TimKiem_LSP();

        // Lọc Sản Phẩm
        btn_Loc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float giaBatDau = Float.parseFloat(txt_Giatien_BD.getText());
                    float giaKetThuc = Float.parseFloat(txt_Giatien_KT.getText());

                    List<SanPham> danhSach = qlsp.locSanPhamTheoKhoangGia(giaBatDau, giaKetThuc);

                    // Xóa dữ liệu cũ trên bảng
                    TableModel_SP.setRowCount(0);

                    // Hiển thị dữ liệu mới
                    for (SanPham sp : danhSach) {
                        TableModel_SP.addRow(new Object[]{
                            sp.getMa_SP(),
                            sp.getTen_SP(),
                            sp.getMoTa_SP(),
                            sp.getDonGia_SP(),
                            sp.getMa_LSP(),
                            sp.getHinhAnh_SP(),
                            sp.getNgayTao_SP(),
                            sp.getTrangThai_SP()
                        });
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số!");
                }
            }
        });

    }

    public void Initable_LSP() {
        TableModel = new DefaultTableModel();
        String[] cols = {"Mã Loại Sản Phẩm", "Tên Loại Sản Phẩm", "Mô Tả Loại Sản Phẩm"};
        TableModel.setColumnIdentifiers(cols);
        tbl_LoaiSanPham.setModel(TableModel);
    }

    // Hiển Thị Tất Cả
    public void FillToTable_LSP() {
        TableModel.setRowCount(0);
        for (LoaiSanPham lsp : qllsp.Get_All_LSP()) {
            TableModel.addRow(qllsp.Get_Row_LSP(lsp));
        }
    }

    public void Initable_SP() {
        TableModel_SP = new DefaultTableModel();
        String[] cols = {"Mã SP", "Tên SP", "Mô Tả SP", "Đơn Giá", "Mã Loại SP", "Hình Ảnh", "Này Tạo", "Trạng Thái"};
        TableModel_SP.setColumnIdentifiers(cols);
        tbl_SanPham.setModel(TableModel_SP);
    }

    // Hiển Thị Tất Cả
    public void FillToTable_SP() {
        TableModel_SP.setRowCount(0);
        for (SanPham sp : qlsp.GetAll_SP()) {
            TableModel_SP.addRow(qlsp.GetRow_SP(sp));
        }
    }

    public void loadSanPhamTheoLoai(String maLSP) {
        DefaultTableModel model = (DefaultTableModel) tbl_SanPham.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<SanPham> ds = qlsp.getSanPhamTheoLoai(maLSP);
        for (SanPham sp : ds) {
            model.addRow(qlsp.GetRow_SP(sp));
        }
    }

    public void TimKiem_LSP() {
        txt_Ma_LSP.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String tuKhoa = txt_Ma_LSP.getText().trim();
                List<Loai_SP_2O> Ds_LSP = qllsp.TimKiemTheo_Loai_SP(tuKhoa);
                if (Ds_LSP.isEmpty()) {
                    return;
                }

                String[] columnNames = {"Mã Loại Sản Phẩm", "Tên Loại Sản Phẩm"};
                Object[][] data = new Object[Ds_LSP.size()][columnNames.length];

                for (int i = 0; i < Ds_LSP.size(); i++) {
                    Loai_SP_2O kh = Ds_LSP.get(i);
                    data[i][0] = kh.getMa_LSP();
                    data[i][1] = kh.getTen_LSP();
                }

                JTable Table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(Table);
                JPopupMenu popup = new JPopupMenu();
                popup.add(scrollPane);
                popup.show(txt_Ma_LSP, 0, txt_Ma_LSP.getHeight());

                Table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int selectedRow = Table.getSelectedRow();
                        if (selectedRow >= 0) {
                            String Theo_Ma_LSP = Table.getValueAt(selectedRow, 0).toString();
                            Loai_SP_2O lsp = qllsp.timKHTheoMa(Theo_Ma_LSP);
                            popup.setVisible(false);
                        }
                    }
                });
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btg_LSP = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        txt_Ma_LSP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_LoaiSanPham = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_SanPham = new javax.swing.JTable();
        btn_Reset = new javax.swing.JButton();
        btn_Tao_LSP = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_Loc = new javax.swing.JButton();
        txt_Giatien_BD = new javax.swing.JTextField();
        txt_Giatien_KT = new javax.swing.JTextField();
        btn_Tao_SP = new javax.swing.JButton();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ô Chức Năng Của Bảng Loại Sản Phẩm"));

        jLabel1.setText("Vui Lòng Nhập:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_Ma_LSP)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Ma_LSP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Loại Sản Phẩm"));

        tbl_LoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_LoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_LoaiSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_LoaiSanPham);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Sản Phẩm"));

        tbl_SanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_SanPham);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btn_Reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reset.png"))); // NOI18N
        btn_Reset.setText("Reset");
        btn_Reset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetActionPerformed(evt);
            }
        });

        btn_Tao_LSP.setText("Tạo Loại Sản Phẩm");
        btn_Tao_LSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tao_LSPActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc Sản Phẩm"));

        jLabel5.setText("Vui Lòng Nhập Giá Tiền Bắt Đầu:");

        jLabel6.setText("Vui Lòng Nhập Giá Tiền Kết Thúc:");

        btn_Loc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Loc_SP.png"))); // NOI18N
        btn_Loc.setText("Lọc Sản Phẩm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(txt_Giatien_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(btn_Loc, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_Giatien_KT, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_Giatien_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addComponent(btn_Loc, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_Giatien_KT, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_Tao_SP.setText("Tạo Sản Phẩm");
        btn_Tao_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tao_SPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_Tao_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btn_Tao_LSP, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                                .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Tao_LSP, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btn_Tao_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_LoaiSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_LoaiSanPhamMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbl_LoaiSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            // Giả sử cột 0 là MA_LSP
            String maLSP = tbl_LoaiSanPham.getValueAt(selectedRow, 0).toString();

            // Gọi hàm load sản phẩm theo loại
            loadSanPhamTheoLoai(maLSP);
        }
    }//GEN-LAST:event_tbl_LoaiSanPhamMouseClicked

    private void btn_Tao_LSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tao_LSPActionPerformed
        // TODO add your handling code here:
        QL_Tao_LoaiSanPham_JFrame ql_Tao_LSP = new QL_Tao_LoaiSanPham_JFrame();
        ql_Tao_LSP.setLocationRelativeTo(null);     // 📍 Căn giữa màn hình

        ql_Tao_LSP.setAlwaysOnTop(true);            // ⏫ Ép lên trên tất cả
        ql_Tao_LSP.setVisible(true);                // 👀 Hiển thị
        ql_Tao_LSP.toFront();                       // 💡 Đưa lên foreground
        ql_Tao_LSP.requestFocus();                  // 🎯 Lấy focus người dùng
        ql_Tao_LSP.setAlwaysOnTop(false);           // 🔁 Tắt lại để tránh che các cửa sổ khác sau đó
    }//GEN-LAST:event_btn_Tao_LSPActionPerformed

    private void btn_Tao_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tao_SPActionPerformed
        // TODO add your handling code here:
        QL_SanPham_Tao_JFrame QL_Tao_SP = new QL_SanPham_Tao_JFrame();
        QL_Tao_SP.setLocationRelativeTo(null);      // 📍 Căn giữa màn hình

        QL_Tao_SP.setAlwaysOnTop(true);             // 🛗 Ưu tiên cửa sổ này lên trên
        QL_Tao_SP.setVisible(true);                 // 👀 Hiển thị cửa sổ
        QL_Tao_SP.toFront();                        // ⏫ Đẩy lên foreground
        QL_Tao_SP.requestFocus();                   // 🎯 Focus bàn phím/chuột vào cửa sổ
        QL_Tao_SP.setAlwaysOnTop(false);            // 🔄 Tắt lại để tránh ảnh hưởng cửa sổ khác
    }//GEN-LAST:event_btn_Tao_SPActionPerformed

    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
        // TODO add your handling code here:
        txt_Ma_LSP.setText("");
        FillToTable_LSP();
        txt_Giatien_BD.setText("");
        txt_Giatien_KT.setText("");
        FillToTable_SP();
    }//GEN-LAST:event_btn_ResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btg_LSP;
    private javax.swing.JButton btn_Loc;
    private javax.swing.JButton btn_Reset;
    private javax.swing.JButton btn_Tao_LSP;
    private javax.swing.JButton btn_Tao_SP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_LoaiSanPham;
    private javax.swing.JTable tbl_SanPham;
    private javax.swing.JTextField txt_Giatien_BD;
    private javax.swing.JTextField txt_Giatien_KT;
    private javax.swing.JTextField txt_Ma_LSP;
    // End of variables declaration//GEN-END:variables
}
