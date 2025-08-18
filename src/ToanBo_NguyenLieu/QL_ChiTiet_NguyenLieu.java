/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ToanBo_NguyenLieu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class QL_ChiTiet_NguyenLieu extends javax.swing.JPanel {

    DefaultTableModel TableModel;
    QL_NguyenLieu qlnl = new QL_NguyenLieu();
    private QL_Tao_NguyenLieu_JFrame taoNLFrame = null;

    /**
     * Creates new form QL_ChiTiet_NguyenLieu
     */
    public QL_ChiTiet_NguyenLieu() {
        initComponents();
        Initable();
        FillToTable();
        initAutoCompleteNguyenLieu();
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

    public void Reset() {
        txt_Ngay_BD.setDate(null);
        txt_Ngay_KT.setDate(null);
        txt_TimKiem.setText("");
        FillToTable();
    }

    public void TimKiemTheoTen() {
        String keyword = txt_TimKiem.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) tbl_NguyenLieu.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tbl_NguyenLieu.setRowSorter(sorter);

        // Cột tên thường là cột số 1 ➜ chỉnh cho đúng vị trí
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword, 1));
    }

    public void showNguyenLieuInTable(NguyenLieu nl) {
        DefaultTableModel model = (DefaultTableModel) tbl_NguyenLieu.getModel();
        model.setRowCount(0); // clear bảng

        model.addRow(new Object[]{
            nl.getMa_NL(),
            nl.getTen_NL(),
            nl.getDonViTinh_NL(),
            nl.getSoLuongTon_NL(),
            nl.getGiaNhap_NL(),
            new SimpleDateFormat("yyyy-MM-dd").format(nl.getNgayNhap_NL()),
            nl.getAnh_NL()
        });
    }
    JPopupMenu popupSuggestions = new JPopupMenu();

    private JPanel createSuggestionPanel(NguyenLieu nl) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setBackground(Color.WHITE);

        JLabel lblTenMa = new JLabel(nl.getTen_NL() + " (" + nl.getMa_NL() + ")");
        lblTenMa.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String detail = "Đơn vị: " + nl.getDonViTinh_NL()
                + "   |  Tồn kho: " + nl.getSoLuongTon_NL()
                + "   |  Giá: " + String.format("%,.0f₫", nl.getGiaNhap_NL());

        JLabel lblDetail = new JLabel(detail);
        lblDetail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDetail.setForeground(Color.GRAY);

        JPanel content = new JPanel(new GridLayout(2, 1));
        content.setBackground(Color.WHITE);
        content.add(lblTenMa);
        content.add(lblDetail);

        panel.add(content, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                txt_TimKiem.setText(nl.getTen_NL() + " - " + nl.getMa_NL());
                popupSuggestions.setVisible(false);
                showNguyenLieuInTable(nl);
                txt_TimKiem.requestFocusInWindow();
                txt_TimKiem.setCaretPosition(txt_TimKiem.getText().length());
            }

            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(230, 245, 255));
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
            }
        });

        return panel;
    }

    public void initAutoCompleteNguyenLieu() {
        txt_TimKiem.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            private void updateSuggestions() {
                String input = txt_TimKiem.getText().toLowerCase().trim();
                popupSuggestions.removeAll();

                if (input.isEmpty()) {
                    popupSuggestions.setVisible(false);
                    return;
                }

                List<NguyenLieu> danhSach = qlnl.Get_All();
                for (NguyenLieu nl : danhSach) {
                    if (nl.getTen_NL().toLowerCase().contains(input) || nl.getMa_NL().toLowerCase().contains(input)) {
                        popupSuggestions.add(createSuggestionPanel(nl));
                    }
                }

                if (popupSuggestions.getComponentCount() > 0) {
                    popupSuggestions.show(txt_TimKiem, 0, txt_TimKiem.getHeight());
                } else {
                    popupSuggestions.setVisible(false);
                }
            }
        });
    }

    public void Loc_NL() {
        try {
            // Lấy giá trị java.util.Date từ JDateChooser
            java.util.Date dateBD = txt_Ngay_BD.getDate();
            java.util.Date dateKT = txt_Ngay_KT.getDate();

            // Kiểm tra xem người dùng đã chọn ngày chưa
            if (dateBD == null || dateKT == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc!");
                return;
            }

            // Chuyển đổi sang LocalDate bằng cách sử dụng Instant
            LocalDate bd = dateBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate kt = dateKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Chuyển sang java.sql.Date để truy vấn SQL
            Date Ngay_BD_KM = Date.valueOf(bd);
            Date Ngay_KT_KM = Date.valueOf(kt);

            // Thực hiện lọc khuyến mãi
            List<NguyenLieu> danhSachLoc = qlnl.Loc_NL(Ngay_BD_KM, Ngay_KT_KM);

            // Đổ dữ liệu vào bảng
            DefaultTableModel model = (DefaultTableModel) tbl_NguyenLieu.getModel();
            model.setRowCount(0);
            for (NguyenLieu km : danhSachLoc) {
                model.addRow(qlnl.GetRow(km));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày hoặc truy vấn: " + e.getMessage());
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

        btg_KiemTK = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_NguyenLieu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_TimKiem = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_LocNL = new javax.swing.JButton();
        txt_Ngay_BD = new com.toedter.calendar.JDateChooser();
        txt_Ngay_KT = new com.toedter.calendar.JDateChooser();
        btn_Reset = new javax.swing.JButton();
        btn_TaoNL = new javax.swing.JButton();

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Tìm Kiếm Nguyên Liệu"));

        jLabel1.setText("Vui Lòng Nhập Tên Nguyên Liệu:");

        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_TimKiem)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Lọc Nguyên Liệu Theo Thời Gian"));

        jLabel2.setText("Ngày Bắt Đầu :");

        jLabel3.setText("Ngày Kết Thúc :");

        btn_LocNL.setText("Lọc NL");
        btn_LocNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LocNLActionPerformed(evt);
            }
        });

        txt_Ngay_BD.setDateFormatString("yyyy-MM-dd");

        txt_Ngay_KT.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(btn_LocNL, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_Ngay_BD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Ngay_KT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Ngay_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Ngay_KT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_LocNL, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reset.png"))); // NOI18N
        btn_Reset.setText("ReSet");
        btn_Reset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetActionPerformed(evt);
            }
        });

        btn_TaoNL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Tao_NL.png"))); // NOI18N
        btn_TaoNL.setText("Tạo Nguyên Liệu");
        btn_TaoNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoNLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_TaoNL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_TaoNL, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_NguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NguyenLieuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_NguyenLieuMouseClicked

    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btn_ResetActionPerformed

    private void btn_TaoNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoNLActionPerformed
        // TODO add your handling code here:
        if (taoNLFrame == null || !taoNLFrame.isDisplayable()) {
            taoNLFrame = new QL_Tao_NguyenLieu_JFrame();
            taoNLFrame.setLocationRelativeTo(this);

            // ✨ Đảm bảo hiện lên trước
            taoNLFrame.setAlwaysOnTop(true);
            taoNLFrame.setVisible(true);
            taoNLFrame.toFront();
            taoNLFrame.requestFocus();
            taoNLFrame.setAlwaysOnTop(false);

            // 🧠 Reset về null khi đóng
            taoNLFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    taoNLFrame = null;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    taoNLFrame = null;
                }
            });
        } else {
            taoNLFrame.toFront();
            taoNLFrame.requestFocus(); // 👈 Thêm dòng này để lấy lại focus

            JOptionPane.showMessageDialog(this,
                    "⚠️ Cửa Sổ Tạo Nguyên Liệu Đã Mở.\nVui Lòng Hoàn Tất Thao Tác Trước Khi Mở Mới.");
        }
    }//GEN-LAST:event_btn_TaoNLActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void btn_LocNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LocNLActionPerformed
        // TODO add your handling code here:
        Loc_NL();
    }//GEN-LAST:event_btn_LocNLActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btg_KiemTK;
    private javax.swing.JButton btn_LocNL;
    private javax.swing.JButton btn_Reset;
    private javax.swing.JButton btn_TaoNL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_NguyenLieu;
    private com.toedter.calendar.JDateChooser txt_Ngay_BD;
    private com.toedter.calendar.JDateChooser txt_Ngay_KT;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}
