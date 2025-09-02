/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ToanBo_KhachHang;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QL_KhachHang_JFrame extends javax.swing.JFrame {

    DefaultTableModel TableModel_TatCa_KH;
    DefaultTableModel TableModel_KhachThuong;
    DefaultTableModel TableModel_KhachVIP;
    DefaultTableModel TableModel_KhachLuxury;
    int Index = -1;
    QL_KhachHang qlkh = new QL_KhachHang();

    /**
     * Creates new form QL_KhachHang_JFrame
     */
    public QL_KhachHang_JFrame() {
        initComponents();
        // Tất Cả Khách Hàng
        Initable_TatCa_KH();
        FillToTable_TatCa_KH();
        // Khách Thường
        Initable_KH_Thuong();
        FillToTable_TatCa_KH();
        // Khách VIP
        Initable_KH_VIP();
        FillToTable_KH_VIP();
        // Khách Luxury
        Initable_KH_Luxury();
        FillToTable_KH_Luxury();
        this.setLocationRelativeTo(this);
        LocalDate ngayHienTai = LocalDate.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Hiển thị vào textField
        txt_NgayTaoKH.setText(ngayHienTai.format(dinhDang));

        // Điều Chỉnh Hoạt Động Của Cái Phần Loại KH
        rdo_KhachThuong.setEnabled(false);
        rdo_KhachVIP.setEnabled(false);
        rdo_KhachLuxury.setEnabled(false);
        // Gắn Sựu Kiện Điểm Tích Luỹ
        txt_DiemTichLuy.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int Diem = (Integer) txt_DiemTichLuy.getValue();
                if (Diem < 50) {
                    rdo_KhachThuong.setSelected(true);
                } else if (Diem < 150) {
                    rdo_KhachVIP.setSelected(true);
                } else {
                    rdo_KhachLuxury.setSelected(true);
                }
            }
        });

        // Check Spinner 
        SpinnerNumberModel Diem_TL_SPN = new SpinnerNumberModel(0, 0, 1000, 1);
        txt_DiemTichLuy.setModel(Diem_TL_SPN);
        
        // Mã Khách Hàng
        txt_Ma_KH.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                capNhatSDT();
            }

            public void removeUpdate(DocumentEvent e) {
                capNhatSDT();
            }

            public void changedUpdate(DocumentEvent e) {
                capNhatSDT();
            }

            private void capNhatSDT() {
                String maKH = txt_Ma_KH.getText().trim();
                String sdt = "";

                for (KhachHang kh : qlkh.Get_All()) {
                    if (kh.getMa_KH().equalsIgnoreCase(maKH)) {
                        sdt = kh.getSDT_KH();
                        break;
                    }
                }

                txt_SDT_KH.setText(sdt);
            }
        });
    }

    // Tất Cả Khách Hàng
    public void Initable_TatCa_KH() {
        TableModel_TatCa_KH = new DefaultTableModel();
        String[] cols = {"Mã Khách Hàng", "Tên KH", "SĐT", "Email", "Ngày Tạo", "Điểm Tích Luỹ", "Loại Khách Hàng"};
        TableModel_TatCa_KH.setColumnIdentifiers(cols);
        tbl_TatCa_KH.setModel(TableModel_TatCa_KH);

    }

    // Hiển Thị Tất Cả
    public void FillToTable_TatCa_KH() {
        TableModel_TatCa_KH.setRowCount(0);
        for (KhachHang kh : qlkh.Get_All()) {
            TableModel_TatCa_KH.addRow(qlkh.GetRow(kh));
        }
    }

    // Tất Cả Khách Thường
    public void Initable_KH_Thuong() {
        TableModel_KhachThuong = new DefaultTableModel();
        String[] cols = {"Mã Khách Hàng", "Tên KH", "SĐT", "Email", "Ngày Tạo", "Điểm Tích Luỹ"};
        TableModel_KhachThuong.setColumnIdentifiers(cols);
        tbl_KhachThuong.setModel(TableModel_KhachThuong);

    }

    // Hiển Thị Tất Cả
    public void FillToTable_KH_Thuong() {
        TableModel_KhachThuong.setRowCount(0);
        for (KhachHang_6_O kh : qlkh.Get_All_KhachThuong()) {
            TableModel_KhachThuong.addRow(qlkh.GetRow_KhachThuong(kh));
        }
    }

    // Tất Cả Khách VIP
    public void Initable_KH_VIP() {
        TableModel_KhachVIP = new DefaultTableModel();
        String[] cols = {"Mã Khách Hàng", "Tên KH", "SĐT", "Email", "Ngày Tạo", "Điểm Tích Luỹ"};
        TableModel_KhachVIP.setColumnIdentifiers(cols);
        tbl_Khach_VIP.setModel(TableModel_KhachVIP);

    }

    // Hiển Thị Tất Cả
    public void FillToTable_KH_VIP() {
        TableModel_KhachVIP.setRowCount(0);
        for (KhachHang_6_O kh : qlkh.Get_All_KhachVIP()) {
            TableModel_KhachVIP.addRow(qlkh.GetRow_KhachVIP(kh));
        }
    }

    // Tất Cả Khách Luxury
    public void Initable_KH_Luxury() {
        TableModel_KhachLuxury = new DefaultTableModel();
        String[] cols = {"Mã Khách Hàng", "Tên KH", "SĐT", "Email", "Ngày Tạo", "Điểm Tích Luỹ"};
        TableModel_KhachLuxury.setColumnIdentifiers(cols);
        tbl_Khach_Luxury.setModel(TableModel_KhachLuxury);

    }

    // Hiển Thị Tất Cả
    public void FillToTable_KH_Luxury() {
        TableModel_KhachLuxury.setRowCount(0);
        for (KhachHang_6_O kh : qlkh.Get_All_KhachLuxury()) {
            TableModel_KhachLuxury.addRow(qlkh.GetRow_KhachLuxury(kh));
        }
    }

    public void LamMoi() {
        txt_Ma_KH.setText("");
        txt_Ten_KH.setText("");
        LocalDate ngayHienTai = LocalDate.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Hiển thị vào textField
        txt_NgayTaoKH.setText(ngayHienTai.format(dinhDang));
        txt_DiemTichLuy.setValue(0);
        txt_SDT_KH.setText("");
        btg_LoaiKH.clearSelection();
        FillToTable_TatCa_KH();
        FillToTable_KH_Thuong();
        FillToTable_KH_VIP();
        FillToTable_KH_Luxury();
    }

    public void Them_KH() {
        String Ma_KH = txt_Ma_KH.getText();
        String Ten_KH = txt_Ten_KH.getText();
        String SDT_KH = txt_SDT_KH.getText();

        // Xử lý ngày tạo khách hàng với kiểm tra lỗi
        Date NgayTao_KH = null;
        try {
            String txtNgayTao = txt_NgayTaoKH.getText();
            if (txtNgayTao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày tạo khách hàng.");
                return;
            }
            DateTimeFormatter DinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(txtNgayTao, DinhDang);
            NgayTao_KH = Date.valueOf(localDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xử lý ngày đăng ký: " + e.getMessage());
            return;
        }

        // Xử lý các dữ liệu khác
        int DiemTichLuy;
        try {
            DiemTichLuy = (Integer) txt_DiemTichLuy.getValue();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm tích lũy phải là số nguyên.");
            return;
        }

        String LoaiKhachHang = rdo_KhachLuxury.isSelected() ? "Khách Luxury"
                : rdo_KhachVIP.isSelected() ? "Khách VIP"
                : "Khách Thường";

        String Email = txt_Email_KH.getText();

        // Tạo đối tượng khách hàng
        KhachHang kh = new KhachHang(Ma_KH, Ten_KH, SDT_KH, Email, NgayTao_KH, LoaiKhachHang, DiemTichLuy);

        // Thêm khách hàng vào hệ thống
        int Result = qlkh.Them_KH(kh);
        if (Result == 1) {
            JOptionPane.showMessageDialog(this, "Thêm Dữ Liệu Khách Hàng Thành Công.");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm Dữ Liệu Khách Hàng Thất Bại.\nVui Lòng Kiểm Tra Dữ Liệu Bạn Nhập.");
        }
    }

    public void Sua_KH() {
        int Index = tbl_TatCa_KH.getSelectedRow();
        if (Index >= 0) {
            String Ma_KH = txt_Ma_KH.getText();
            String Ten_KH = txt_Ten_KH.getText();
            String SDT_KH = txt_SDT_KH.getText();
            String Email = txt_Email_KH.getText();

            Date NgayTao_KH = null;
            try {
                String txtNgayTao = txt_NgayTaoKH.getText();
                if (txtNgayTao.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày tạo khách hàng.");
                    return;
                }
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(txtNgayTao, format);
                NgayTao_KH = Date.valueOf(localDate);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xử lý ngày đăng ký: " + e.getMessage());
                return;
            }

            int DiemTichLuy;
            try {
                DiemTichLuy = (Integer) txt_DiemTichLuy.getValue();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Điểm tích lũy phải là số nguyên.");
                return;
            }

            String LoaiKhachHang = rdo_KhachLuxury.isSelected() ? "Khách Luxury"
                    : rdo_KhachVIP.isSelected() ? "Khách VIP"
                    : "Khách Thường";

            KhachHang kh = new KhachHang(Ma_KH, Ten_KH, SDT_KH, Email, NgayTao_KH, LoaiKhachHang, DiemTichLuy);
            String TheoMa = qlkh.Get_All().get(Index).getMa_KH();

            int result = qlkh.Sua_KH(kh, TheoMa);
            if (result == 1) {
                JOptionPane.showMessageDialog(this,
                        "✅ Sửa khách hàng thành công!\nMã mới: " + Ma_KH
                        + "\nTên: " + Ten_KH
                        + "\nSĐT: " + SDT_KH);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Sửa khách hàng thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn khách hàng cần sửa.");
        }
    }

    public void Xoa_KH() {
        Index = tbl_TatCa_KH.getSelectedRow();
        if (Index >= 0) {
            String TheoMa = qlkh.Get_All().get(Index).getMa_KH();
            String Ten = qlkh.Get_All().get(Index).getTen_KH();
            String SDT = qlkh.Get_All().get(Index).getSDT_KH();
            int Choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Xoá Tài Khoản:"
                    + "\n Mã Khách Hàng :  " + TheoMa
                    + "\n Tên Khách Hàng: " + Ten
                    + "\n Số Điện Thoại: " + SDT, "Xác Nhận Xoá Tài Khoản", JOptionPane.YES_NO_OPTION);
            if (Choice == JOptionPane.YES_OPTION) {

                int Result = qlkh.Xoa_KH(TheoMa);
                if (Result == 1) {
                    JOptionPane.showMessageDialog(this, "Xoá Dữ Liệu Khách Hàng Thành Công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá Khách Hàng Có Mã : " + TheoMa
                            + "\n Tên Khách Hàng Là: " + Ten
                            + "\n Thất Bại.");
                    return;
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Dữ Liệu Khách Hàng Trong Bảng Để Xoá.");
            return;
        }
    }

    private void capNhatLoaiKhachTheoDiem(int diem) {
        if (diem < 50) {
            rdo_KhachThuong.setSelected(true);
        } else if (diem < 150) {
            rdo_KhachVIP.setSelected(true);
        } else {
            rdo_KhachLuxury.setSelected(true);
        }
    }

    public void ShowDetail() {
        int index = tbl_TatCa_KH.getSelectedRow();

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng khách hàng trong bảng!");
            return;
        }

        // Lấy khách hàng tại dòng đã chọn
        KhachHang kh = qlkh.Get_All().get(index);

        // Gán thông tin cơ bản
        txt_Ma_KH.setText(kh.getMa_KH());
        txt_Ten_KH.setText(kh.getTen_KH());
        txt_SDT_KH.setText(kh.getSDT_KH());
        txt_Email_KH.setText(kh.getEmail_KH());

        // Định dạng ngày tạo
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngayTao = kh.getNgayTao_KH().toLocalDate();
        txt_NgayTaoKH.setText(dinhDang.format(ngayTao));

        // Gán điểm tích lũy cho spinner — kiểm tra min/max an toàn
        int diemTichLuy = kh.getDiemTichLuy();
        SpinnerNumberModel model = (SpinnerNumberModel) txt_DiemTichLuy.getModel();

        Integer minObj = (Integer) model.getMinimum();
        Integer maxObj = (Integer) model.getMaximum();

        int min = (minObj != null) ? minObj : 0;
        int max = (maxObj != null) ? maxObj : 1000; // gán max mặc định nếu chưa có

        if (diemTichLuy >= min && diemTichLuy <= max) {
            txt_DiemTichLuy.setValue(diemTichLuy);
        } else {
            txt_DiemTichLuy.setValue(min); // hoặc có thể dùng 0 tuỳ anh
        }

        // ✅ Tự động phân loại khách theo điểm tích lũy
        capNhatLoaiKhachTheoDiem(diemTichLuy);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btg_LoaiKH = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        btn_LamMoi = new javax.swing.JButton();
        btn_ThemKH = new javax.swing.JButton();
        btn_SuaKH = new javax.swing.JButton();
        btn_XoaKH = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        QL_KH = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_TatCa_KH = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_KhachThuong = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Khach_VIP = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_Khach_Luxury = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_Ma_KH = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_Ten_KH = new javax.swing.JTextField();
        txt_SDT_KH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_NgayTaoKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdo_KhachThuong = new javax.swing.JRadioButton();
        rdo_KhachVIP = new javax.swing.JRadioButton();
        rdo_KhachLuxury = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txt_Email_KH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_DiemTichLuy = new javax.swing.JSpinner();
        btn_DongTrang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng Chính"));

        btn_LamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LamMoi_KhachHang.png"))); // NOI18N
        btn_LamMoi.setText("Làm Mới");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        btn_ThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Them_KhachHang.png"))); // NOI18N
        btn_ThemKH.setText("Thêm");
        btn_ThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemKHActionPerformed(evt);
            }
        });

        btn_SuaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Sua_KhachHang.png"))); // NOI18N
        btn_SuaKH.setText("Sửa");
        btn_SuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaKHActionPerformed(evt);
            }
        });

        btn_XoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Xoa_KhachHang.png"))); // NOI18N
        btn_XoaKH.setText("Xoá");
        btn_XoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_XoaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_SuaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_ThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btn_SuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_XoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Khách Hàng"));

        tbl_TatCa_KH.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_TatCa_KH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_TatCa_KHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_TatCa_KH);

        QL_KH.addTab("Tất Cả Khách Hàng", jScrollPane2);

        tbl_KhachThuong.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbl_KhachThuong);

        QL_KH.addTab("Khách Thường", jScrollPane3);

        tbl_Khach_VIP.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tbl_Khach_VIP);

        QL_KH.addTab("Khách VIP", jScrollPane4);

        tbl_Khach_Luxury.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tbl_Khach_Luxury);

        QL_KH.addTab("Khách Luxury", jScrollPane5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QL_KH)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QL_KH, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin Khách Hàng"));

        jLabel1.setText("Mã KH :");

        txt_Ma_KH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_Ma_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Ma_KHActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên KH:");

        txt_Ten_KH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_SDT_KH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("SDT :");

        jLabel4.setText("Ngày Tạo :");

        txt_NgayTaoKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Loại KH:");

        btg_LoaiKH.add(rdo_KhachThuong);
        rdo_KhachThuong.setText("Khách Thường");
        rdo_KhachThuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_KhachThuongActionPerformed(evt);
            }
        });

        btg_LoaiKH.add(rdo_KhachVIP);
        rdo_KhachVIP.setText("Khách VIP");

        btg_LoaiKH.add(rdo_KhachLuxury);
        rdo_KhachLuxury.setText("Khách Luxury");
        rdo_KhachLuxury.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_KhachLuxuryActionPerformed(evt);
            }
        });

        jLabel6.setText("Điểm Tích Luỹ :");

        txt_Email_KH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Email :");

        txt_DiemTichLuy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_Email_KH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Ten_KH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_SDT_KH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Ma_KH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NgayTaoKH, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(txt_DiemTichLuy))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(rdo_KhachThuong)
                        .addGap(36, 36, 36)
                        .addComponent(rdo_KhachVIP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(rdo_KhachLuxury)
                        .addGap(74, 74, 74))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_Ma_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(txt_Ten_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_SDT_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Email_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_NgayTaoKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_DiemTichLuy, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdo_KhachThuong)
                    .addComponent(rdo_KhachLuxury)
                    .addComponent(rdo_KhachVIP))
                .addContainerGap(14, Short.MAX_VALUE))
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
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_DongTrang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_DongTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiActionPerformed
        // TODO add your handling code here:
        LamMoi();
        FillToTable_TatCa_KH();
        FillToTable_KH_Luxury();
        FillToTable_KH_Thuong();
        FillToTable_KH_VIP();
    }//GEN-LAST:event_btn_LamMoiActionPerformed

    private void btn_ThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemKHActionPerformed
        // TODO add your handling code here:
        Them_KH();
        FillToTable_TatCa_KH();
        FillToTable_KH_Luxury();
        FillToTable_KH_Thuong();
        FillToTable_KH_VIP();
    }//GEN-LAST:event_btn_ThemKHActionPerformed

    private void btn_SuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaKHActionPerformed
        // TODO add your handling code here:
        Sua_KH();
        FillToTable_TatCa_KH();
        FillToTable_KH_Luxury();
        FillToTable_KH_Thuong();
        FillToTable_KH_VIP();
    }//GEN-LAST:event_btn_SuaKHActionPerformed

    private void btn_XoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaKHActionPerformed
        // TODO add your handling code here:
        Xoa_KH();
        FillToTable_TatCa_KH();
        FillToTable_KH_Luxury();
        FillToTable_KH_Thuong();
        FillToTable_KH_VIP();
    }//GEN-LAST:event_btn_XoaKHActionPerformed

    private void txt_Ma_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Ma_KHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Ma_KHActionPerformed

    private void rdo_KhachThuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_KhachThuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_KhachThuongActionPerformed

    private void rdo_KhachLuxuryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_KhachLuxuryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_KhachLuxuryActionPerformed

    private void btn_DongTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DongTrangActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_DongTrangActionPerformed

    private void tbl_TatCa_KHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_TatCa_KHMouseClicked
        // TODO add your handling code here:
        ShowDetail();
    }//GEN-LAST:event_tbl_TatCa_KHMouseClicked

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
            java.util.logging.Logger.getLogger(QL_KhachHang_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QL_KhachHang_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QL_KhachHang_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QL_KhachHang_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QL_KhachHang_JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane QL_KH;
    private javax.swing.ButtonGroup btg_LoaiKH;
    private javax.swing.JButton btn_DongTrang;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_SuaKH;
    private javax.swing.JButton btn_ThemKH;
    private javax.swing.JButton btn_XoaKH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JRadioButton rdo_KhachLuxury;
    private javax.swing.JRadioButton rdo_KhachThuong;
    private javax.swing.JRadioButton rdo_KhachVIP;
    private javax.swing.JTable tbl_KhachThuong;
    private javax.swing.JTable tbl_Khach_Luxury;
    private javax.swing.JTable tbl_Khach_VIP;
    private javax.swing.JTable tbl_TatCa_KH;
    private javax.swing.JSpinner txt_DiemTichLuy;
    private javax.swing.JTextField txt_Email_KH;
    private javax.swing.JTextField txt_Ma_KH;
    private javax.swing.JTextField txt_NgayTaoKH;
    private javax.swing.JTextField txt_SDT_KH;
    private javax.swing.JTextField txt_Ten_KH;
    // End of variables declaration//GEN-END:variables
}
