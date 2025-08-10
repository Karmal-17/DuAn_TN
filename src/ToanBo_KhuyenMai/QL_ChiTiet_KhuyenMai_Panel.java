/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ToanBo_KhuyenMai;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author ADMIN
 */
public class QL_ChiTiet_KhuyenMai_Panel extends javax.swing.JPanel {

    int InDex = -1;
    DefaultTableModel TableMoDel;
    QL_KhuyenMai qlkm = new QL_KhuyenMai();
    private QL_KhuyenMai_JFrame QL_KM = null;

    /**
     * Creates new form QL_ChiTiet_KhuyenMai_Panel
     */
    public QL_ChiTiet_KhuyenMai_Panel() {
        initComponents();
        Initable();
        FillToTable();
        TimKiem();

        rdo_DangHoatDong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hienThiKhuyenMaiTheoTrangThai(true);
            }
        });

        rdo_KhongHoatDong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hienThiKhuyenMaiTheoTrangThai(false);
            }
        });
    }

    // Hiển Thị Khuyến Mãi
    private void hienThiKhuyenMaiTheoTrangThai(boolean trangThai) {
        List<KhuyenMai> danhSachKM = qlkm.timKiemKhuyenMaiTheoTrangThai(trangThai);
        // Xóa dữ liệu bảng cũ
        TableMoDel.setRowCount(0);
        // Thêm dòng mới
        for (KhuyenMai km : danhSachKM) {
            TableMoDel.addRow(qlkm.GetRow(km));
        }
    }

    // Chèn Dữ Liệu Vào Trong Bảng
    public void Initable() {
        TableMoDel = new DefaultTableModel();
        String[] cols = {"Mã KM", "Tên KM", "Mô Tả", "Hình Thức", "Giá Trị Yêu Cầu", "Giá Trị", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"};
        TableMoDel.setColumnIdentifiers(cols);
        tbl_DanhSachKM.setModel(TableMoDel);
    }

    // Hiển Thị Tất Cả
    public void FillToTable() {
        TableMoDel.setRowCount(0);
        for (KhuyenMai km : qlkm.Get_All()) {
            TableMoDel.addRow(qlkm.GetRow(km));
        }
    }

    // Lọc Khuyến Mãi Theo Thời Gian
    public void Loc_KM() {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate bd = LocalDate.parse((CharSequence) txt_HienThiNgay_BD.getDate(), df);
            LocalDate kt = LocalDate.parse((CharSequence) txt_HienThiNgay_KT.getDate(), df);
            Date Ngay_BD_KM = Date.valueOf(bd);
            Date Ngay_KT_KM = Date.valueOf(kt);

            List<KhuyenMai> danhSachLoc = qlkm.Loc_KM(Ngay_BD_KM, Ngay_KT_KM);

            DefaultTableModel model = (DefaultTableModel) tbl_DanhSachKM.getModel();
            model.setRowCount(0);
            for (KhuyenMai km : danhSachLoc) {
                Object[] row = { /* như trên */};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày hoặc truy vấn: " + e.getMessage());
        }
    }

    public void Loc_KM01() {
        try {
            // Lấy giá trị java.util.Date từ JDateChooser
            java.util.Date dateBD = txt_HienThiNgay_BD.getDate();
            java.util.Date dateKT = txt_HienThiNgay_KT.getDate();

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
            List<KhuyenMai> danhSachLoc = qlkm.Loc_KM(Ngay_BD_KM, Ngay_KT_KM);

            // Đổ dữ liệu vào bảng
            DefaultTableModel model = (DefaultTableModel) tbl_DanhSachKM.getModel();
            model.setRowCount(0);
            for (KhuyenMai km : danhSachLoc) {
                model.addRow(qlkm.GetRow(km));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày hoặc truy vấn: " + e.getMessage());
        }
    }

    // Tìm Kiếm Khuyến Mãi
    public void TimKiem() {
        txt_VuiLongNhap.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String tuKhoa = txt_VuiLongNhap.getText().trim();
                List<KhuyenMai_2_O> ds = qlkm.TimKiem_KM(tuKhoa);

                String[] columnNames = {"Mã KM", "Tên KM"};
                Object[][] data = new Object[ds.size()][columnNames.length];

                for (int i = 0; i < ds.size(); i++) {
                    KhuyenMai_2_O item = ds.get(i);
                    data[i][0] = item.getMa_KM();
                    data[i][1] = item.getTen_KM();
                }

                JTable table = new JTable(data, columnNames);
                JScrollPane scroll = new JScrollPane(table);
                JPopupMenu popup = new JPopupMenu();
                popup.add(scroll);
                popup.show(txt_VuiLongNhap, 0, txt_VuiLongNhap.getHeight());

                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int index = table.getSelectedRow();
                        String maKM = table.getValueAt(index, 0).toString();
                        FillToTableTheoMaKM(maKM);
                        popup.setVisible(false);
                    }
                });
            }
        });
    }

    public void FillToTableTheoMaKM(String maKM) {
        DefaultTableModel model = (DefaultTableModel) tbl_DanhSachKM.getModel();
        model.setRowCount(0); // Xóa bảng hiện tại

        KhuyenMai km = qlkm.LayChiTietKM(maKM);
        if (km != null) {
            String trangThaiText = km.isTrangThai() ? "Hiệu lực" : "Hết hạn";

            model.addRow(new Object[]{
                km.getMa_KM(),
                km.getTen_KM(),
                km.getMoTa_KM(),
                km.getHinhThuc_KM(),
                km.getGiaTri_YeuCau_KM(),
                km.getGiaTri_KM(),
                km.getNgay_BD(),
                km.getNgay_KT(),
                trangThaiText
            });
        }
    }

    // Chi Tiết Khuyến Mãi
    public void ShowDetail() {
        InDex = tbl_DanhSachKM.getSelectedRow();
        if (InDex >= 0) {
            KhuyenMai km = qlkm.Get_All().get(InDex);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String info = "🔔 Thông Tin Khuyến Mãi:\n"
                    + "───────────────────────────────\n"
                    + "🆔 Mã khuyến mãi: " + km.getMa_KM() + "\n"
                    + "📛 Tên khuyến mãi: " + km.getTen_KM() + "\n"
                    + "📦 Hình thức: " + km.getHinhThuc_KM() + "\n"
                    + "📝 Mô tả: " + km.getMoTa_KM() + "\n"
                    + "🏆 Giá Trị Yêu Cầu: " + km.getGiaTri_YeuCau_KM() + "\n"
                    + "💸 Giá trị: " + km.getGiaTri_KM() + "\n"
                    + "📅 Ngày bắt đầu: " + km.getNgay_BD().toLocalDate().format(formatter) + "\n"
                    + "📅 Ngày kết thúc: " + km.getNgay_KT().toLocalDate().format(formatter) + "\n"
                    + "🚦 Trạng thái: " + (km.isTrangThai() ? "Đang hoạt động" : "Không hoạt động");

            JOptionPane.showMessageDialog(this, info, "Chi tiết khuyến mãi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng dữ liệu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
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

        btg_KieuTK = new javax.swing.ButtonGroup();
        btg_TrangThai = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_VuiLongNhap = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_HienThiNgay_BD = new com.toedter.calendar.JDateChooser();
        txt_HienThiNgay_KT = new com.toedter.calendar.JDateChooser();
        btn_Loc_KM = new javax.swing.JButton();
        Panel_DanhSachKM = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_DanhSachKM = new javax.swing.JTable();
        btn_Show_KM = new javax.swing.JButton();
        btn_Tao_KM = new javax.swing.JButton();
        btn_LamMoi_TimKiem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rdo_DangHoatDong = new javax.swing.JRadioButton();
        rdo_KhongHoatDong = new javax.swing.JRadioButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin Tìm Kiếm Khuyến Mãi"));

        jLabel1.setText("Vui Lòng Nhập:");

        txt_VuiLongNhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_VuiLongNhap)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_VuiLongNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc Khuyến Mãi"));

        jLabel3.setText("Thời Gian Bắt Đầu :");

        jLabel4.setText("Thời Gian Kết Thúc :");

        txt_HienThiNgay_BD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_HienThiNgay_BD.setDateFormatString("yyyy-MM-dd");

        txt_HienThiNgay_KT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_HienThiNgay_KT.setDateFormatString("yyyy-MM-dd");

        btn_Loc_KM.setText("Lọc Khuyến Mãi");
        btn_Loc_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Loc_KMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Loc_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_HienThiNgay_BD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txt_HienThiNgay_KT, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(11, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_HienThiNgay_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_HienThiNgay_KT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(btn_Loc_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Panel_DanhSachKM.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Khuyến Mãi"));

        tbl_DanhSachKM.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_DanhSachKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DanhSachKMMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_DanhSachKM);

        javax.swing.GroupLayout Panel_DanhSachKMLayout = new javax.swing.GroupLayout(Panel_DanhSachKM);
        Panel_DanhSachKM.setLayout(Panel_DanhSachKMLayout);
        Panel_DanhSachKMLayout.setHorizontalGroup(
            Panel_DanhSachKMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        Panel_DanhSachKMLayout.setVerticalGroup(
            Panel_DanhSachKMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
        );

        btn_Show_KM.setText("Chi Tiết Khuyến Mãi");
        btn_Show_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Show_KMActionPerformed(evt);
            }
        });

        btn_Tao_KM.setText("Tạo Khuyến Mãi");
        btn_Tao_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tao_KMActionPerformed(evt);
            }
        });

        btn_LamMoi_TimKiem.setText("Reset");
        btn_LamMoi_TimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_LamMoi_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoi_TimKiemActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin Tìm Kiếm Khuyến Mãi"));

        jLabel2.setText("Vui Lòng Chọn Trạng Thái Khuyến Mãi :");

        btg_TrangThai.add(rdo_DangHoatDong);
        rdo_DangHoatDong.setText("Đang Hoạt Động");

        btg_TrangThai.add(rdo_KhongHoatDong);
        rdo_KhongHoatDong.setText("Không Hoạt Động");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rdo_DangHoatDong)
                        .addGap(32, 32, 32)
                        .addComponent(rdo_KhongHoatDong)))
                .addGap(0, 53, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_DangHoatDong)
                    .addComponent(rdo_KhongHoatDong))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Panel_DanhSachKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_LamMoi_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_Tao_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_Show_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(51, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_LamMoi_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Tao_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Show_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(Panel_DanhSachKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Loc_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Loc_KMActionPerformed
        // TODO add your handling code here:
        Loc_KM01();
    }//GEN-LAST:event_btn_Loc_KMActionPerformed

    private void btn_LamMoi_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoi_TimKiemActionPerformed
        // TODO add your handling code here:
        txt_VuiLongNhap.setText("");
        txt_HienThiNgay_BD.setDate(null);
        txt_HienThiNgay_KT.setDate(null);
        btg_TrangThai.clearSelection();
        FillToTable();
    }//GEN-LAST:event_btn_LamMoi_TimKiemActionPerformed

    private void tbl_DanhSachKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DanhSachKMMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_DanhSachKMMouseClicked

    private void btn_Show_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Show_KMActionPerformed
        // TODO add your handling code here:
        ShowDetail();
    }//GEN-LAST:event_btn_Show_KMActionPerformed

    private void btn_Tao_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tao_KMActionPerformed
        // TODO add your handling code here:
        if (QL_KM == null || !QL_KM.isDisplayable()) {
            QL_KM = new QL_KhuyenMai_JFrame();
            QL_KM.setLocationRelativeTo(null);

            QL_KM.setAlwaysOnTop(true);
            QL_KM.setVisible(true);
            QL_KM.toFront();
            QL_KM.requestFocus();
            QL_KM.setAlwaysOnTop(false);

            QL_KM.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    QL_KM = null;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    QL_KM = null;
                }
            });
        } else {
            QL_KM.toFront();
            QL_KM.requestFocus();

            JOptionPane.showMessageDialog(null,
                    "⚠️ Cửa Sổ Khuyến Mãi Đã Mở.\nVui Lòng Hoàn Tất Thao Tác Trước Khi Mở Mới.");
        }
    }//GEN-LAST:event_btn_Tao_KMActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_DanhSachKM;
    private javax.swing.ButtonGroup btg_KieuTK;
    private javax.swing.ButtonGroup btg_TrangThai;
    private javax.swing.JButton btn_LamMoi_TimKiem;
    private javax.swing.JButton btn_Loc_KM;
    private javax.swing.JButton btn_Show_KM;
    private javax.swing.JButton btn_Tao_KM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_DangHoatDong;
    private javax.swing.JRadioButton rdo_KhongHoatDong;
    private javax.swing.JTable tbl_DanhSachKM;
    private com.toedter.calendar.JDateChooser txt_HienThiNgay_BD;
    private com.toedter.calendar.JDateChooser txt_HienThiNgay_KT;
    private javax.swing.JTextField txt_VuiLongNhap;
    // End of variables declaration//GEN-END:variables
}
