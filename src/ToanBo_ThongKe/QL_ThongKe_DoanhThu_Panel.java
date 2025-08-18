/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ToanBo_ThongKe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 *
 * @author ADMIN
 */
public class QL_ThongKe_DoanhThu_Panel extends javax.swing.JPanel {

    DefaultTableModel TableModel_SanPhamBanChay;
    QL_DoanhThu qldt = new QL_DoanhThu();

    /**
     * Creates new form QL_ThongKe_DoanhThu_Panel
     */
    public QL_ThongKe_DoanhThu_Panel() {
        initComponents();
        hienThiDoanhThuHomNay();
        Initable_SPBC();
        txt_Tong_SP_BanDuoc_TheoNgay.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                hienThiTongSanPhamTheoNgay(); // Gọi hàm xử lý truy vấn & hiển thị
            }
        });

        // Sự Kiện Auto Tìm Kiếm Thời Gian Bắt Đầu Thời Gian Kết Thúc
        txt_ThoiGian_BD_SPBC.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                hienThiSanPhamBanChayTheoThoiGian(txt_ThoiGian_BD_SPBC.getDate(), txt_ThoiGian_KT_SPBC.getDate());
            }
        });

        txt_ThoiGian_KT_SPBC.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                hienThiSanPhamBanChayTheoThoiGian(txt_ThoiGian_BD_SPBC.getDate(), txt_ThoiGian_KT_SPBC.getDate());
            }
        });

        // Render Ảnh Cho Cái Table
        tbl_Bang_SP_BanChay.setRowHeight(50);
        tbl_Bang_SP_BanChay.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) value;
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                } else {
                    label.setBackground(Color.WHITE);
                }
                return label;
            }
        });

        // Cập Nhật Lợi Nhuận
        capNhatLoiNhuan();

        ganSuKienKiemTraNgay();
    }

    // Cái Sự Kiện Của Doanh Thu
    public void ganSuKienKiemTraNgay() {
        PropertyChangeListener listener = evt -> {
            java.util.Date Ngay_BD = txt_NgayBatDau_BieuDo.getDate();
            java.util.Date Ngay_KT = txt_Ngay_KT_BieuDo.getDate();

            if (Ngay_BD == null || Ngay_KT == null) {
                return;
            }

            if (Ngay_BD.after(Ngay_KT)) {
                JOptionPane.showMessageDialog(null, "❌ Ngày bắt đầu không được sau ngày kết thúc!");
                txt_NgayBatDau_BieuDo.setDate(null); // hoặc set lại ngày hợp lệ
                return;
            }

            long millisPerDay = 1000L * 60 * 60 * 24;
            long soNgay = (Ngay_KT.getTime() - Ngay_BD.getTime()) / millisPerDay;

            if (soNgay > 31) {
                JOptionPane.showMessageDialog(null, "❌ Khoảng thời gian không được vượt quá 1 tháng!");
                txt_Ngay_KT_BieuDo.setDate(null); // hoặc set lại ngày hợp lệ
            }
        };

        txt_NgayBatDau_BieuDo.getDateEditor().addPropertyChangeListener(listener);
        txt_Ngay_KT_BieuDo.getDateEditor().addPropertyChangeListener(listener);

    }

    // Cái Lợi Nhuận
    public void capNhatLoiNhuan() {
        float loiNhuan = qldt.tinhTongLoiNhuanTrongNgayHomNay();
        DecimalFormat format = new DecimalFormat("#,###");
        lb_HienLoiNhuan.setText(format.format(loiNhuan) + " VND");
    }

    // Hiển Thị Sản Phẩm Bán Chạy Theo Một Khoản Thời Gian Nhất Định
    public void Initable_SPBC() {
        TableModel_SanPhamBanChay = new DefaultTableModel();
        String[] cols = {"Tên Sản Phẩm", "Số Lượng", "Thành Tiền", "Ảnh SP"};
        TableModel_SanPhamBanChay.setColumnIdentifiers(cols);
        tbl_Bang_SP_BanChay.setModel(TableModel_SanPhamBanChay);
    }

    public void hienThiSanPhamBanChayTheoThoiGian(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        TableModel_SanPhamBanChay.setRowCount(0); // Xoá dữ liệu cũ

        // Kiểm tra nếu chưa chọn ngày
        if (ngayBatDau == null || ngayKetThuc == null) {
            return;
        }

        // Kiểm tra nếu ngày kết thúc < ngày bắt đầu
        if (ngayKetThuc.before(ngayBatDau)) {
            JOptionPane.showMessageDialog(null, "❌ Thời gian kết thúc không được nhỏ hơn thời gian bắt đầu.");
            return;
        }

        // Chuyển kiểu sang java.sql.Date
        java.sql.Date sqlNgayBatDau = new java.sql.Date(ngayBatDau.getTime());
        java.sql.Date sqlNgayKetThuc = new java.sql.Date(ngayKetThuc.getTime());

        // Truy vấn dữ liệu
        List<SanPhamBanChay> dsSP = qldt.laySanPhamBanChayTheoThoiGian(sqlNgayBatDau, sqlNgayKetThuc);

        for (SanPhamBanChay sp : dsSP) {
            Object[] row = new Object[]{
                sp.getTen_SP(),
                sp.getSoLuong(),
                String.format("%,.0f VNĐ", sp.getThanhTien()),
                taoLabelAnhSanPhamTrongBang(sp.getAnh_SP()) // Xử lý ảnh gọn gàng
            };
            TableModel_SanPhamBanChay.addRow(row);
        }
    }

    public Component taoLabelAnhSanPhamTrongBang(String duongDanAnh) {
        JLabel label = new JLabel();
        label.setOpaque(true); // Giúp hiện màu nền nếu cần
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(50, 55));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Viền xám

        if (duongDanAnh == null || duongDanAnh.trim().isEmpty()) {
            label.setText("Chưa có ảnh");
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(Color.RED); // Chữ đỏ
        } else {
            ImageIcon icon = new ImageIcon(duongDanAnh);
            Image img = icon.getImage().getScaledInstance(50, 45, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
            label.setText(""); // Không hiển thị đường dẫn
        }

        return label;
    }

    // Hiển Thị Tổng Sản Phẩm Theo Ngày
    private void hienThiTongSanPhamTheoNgay() {
        // Lấy ngày được chọn từ JDateChooser (java.util.Date)
        java.util.Date ngayChonUtil = txt_Tong_SP_BanDuoc_TheoNgay.getDate();

        if (ngayChonUtil == null) {
            lb_HienTongSanPhamDaBanTheoNgay.setText("⚠️ Chưa chọn ngày!");
            return;
        }

        // Lấy ngày hiện tại để so sánh
        java.util.Date ngayHienTai = new java.util.Date();

        // Nếu ngày được chọn > ngày hiện tại → báo lỗi
        if (ngayChonUtil.after(ngayHienTai)) {
            lb_HienTongSanPhamDaBanTheoNgay.setText("⚠️ Không thể chọn ngày tương lai!");
            return;
        }

        // Chuyển sang java.sql.Date để tương thích với phương thức truy vấn
        java.sql.Date ngayChon = new java.sql.Date(ngayChonUtil.getTime());

        // Truy vấn số lượng sản phẩm đã bán
        int tongSanPham = qldt.layTongSanPhamTheoNgay(ngayChon);

        // Kiểm tra kết quả
        if (tongSanPham > 0) {
            lb_HienTongSanPhamDaBanTheoNgay.setText(String.format("%d sản phẩm đã bán", tongSanPham));
        } else {
            lb_HienTongSanPhamDaBanTheoNgay.setText("📭 Chưa có sản phẩm nào bán \n được trong ngày này.");
        }
    }

    // Doanh Thu Hôm Nay
    private void hienThiDoanhThuHomNay() {
        float doanhThuHomNay = qldt.layTongDoanhThuHomNay();

        DecimalFormat formatter = new DecimalFormat("###,###");
        String doanhThuStr = formatter.format(doanhThuHomNay);

        lb_HienDoanhThu.setText(doanhThuStr + " VND"); // Hiển thị ra label đẹp mắt
    }

    // Biểu Đồ Doanh Thu
    public void veBieuDoDoanhThu(List<BieuDo_3_O> danhSach) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double maxGiaTri = 0;

        for (BieuDo_3_O item : danhSach) {
            double doanhThu = item.getDoanh_Thu();
            double loiNhuan = item.getLoi_Nhuan();
            String ngay = item.getNgay();

            dataset.addValue(doanhThu, "Doanh thu", ngay);
            dataset.addValue(loiNhuan, "Lợi nhuận", ngay);

            // Xác định giá trị lớn nhất để set trục tung
            if (doanhThu > maxGiaTri) {
                maxGiaTri = doanhThu;
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống Kê Doanh Thu và Lợi Nhuận Theo Ngày",
                "Ngày",
                "Số tiền (VNĐ)",
                dataset
        );

        // Tùy chỉnh màu cột
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);    // Doanh thu
        renderer.setSeriesPaint(1, Color.GREEN);  // Lợi nhuận

        // ✅ Cài đặt giới hạn trục tung cao hơn doanh thu lớn nhất 50,000 VNĐ
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(0, maxGiaTri + 50000);

        // Gắn vào panel
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(604, 345));
        BieuDo_Panel.removeAll();
        BieuDo_Panel.setLayout(new BorderLayout());
        BieuDo_Panel.add(chartPanel, BorderLayout.CENTER);
        BieuDo_Panel.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DoanhThu_Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lb_HienDoanhThu = new javax.swing.JLabel();
        LoiNhuan_Panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lb_HienLoiNhuan = new javax.swing.JLabel();
        Trang_Chung_Chuyen = new javax.swing.JPanel();
        BieuDo_Panel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_NgayBatDau_BieuDo = new com.toedter.calendar.JDateChooser();
        txt_Ngay_KT_BieuDo = new com.toedter.calendar.JDateChooser();
        btn_Loc = new javax.swing.JButton();
        Tong_SP_BanDuoc = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lb_HienTongSanPhamDaBanTheoNgay = new javax.swing.JLabel();
        txt_Tong_SP_BanDuoc_TheoNgay = new com.toedter.calendar.JDateChooser();
        SanPham_BanChay = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Bang_SP_BanChay = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txt_ThoiGian_BD_SPBC = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        txt_ThoiGian_KT_SPBC = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1130, 655));

        DoanhThu_Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Doanh Thu Hôm Nay");

        lb_HienDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        javax.swing.GroupLayout DoanhThu_PanelLayout = new javax.swing.GroupLayout(DoanhThu_Panel);
        DoanhThu_Panel.setLayout(DoanhThu_PanelLayout);
        DoanhThu_PanelLayout.setHorizontalGroup(
            DoanhThu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DoanhThu_PanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(DoanhThu_PanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lb_HienDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DoanhThu_PanelLayout.setVerticalGroup(
            DoanhThu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoanhThu_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_HienDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LoiNhuan_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Lợi Nhuận");

        lb_HienLoiNhuan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        javax.swing.GroupLayout LoiNhuan_PanelLayout = new javax.swing.GroupLayout(LoiNhuan_Panel);
        LoiNhuan_Panel.setLayout(LoiNhuan_PanelLayout);
        LoiNhuan_PanelLayout.setHorizontalGroup(
            LoiNhuan_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoiNhuan_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(199, 199, 199))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoiNhuan_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_HienLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        LoiNhuan_PanelLayout.setVerticalGroup(
            LoiNhuan_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoiNhuan_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lb_HienLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Trang_Chung_Chuyen.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Biểu Đồ Danh Thu"));

        javax.swing.GroupLayout BieuDo_PanelLayout = new javax.swing.GroupLayout(BieuDo_Panel);
        BieuDo_Panel.setLayout(BieuDo_PanelLayout);
        BieuDo_PanelLayout.setHorizontalGroup(
            BieuDo_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        BieuDo_PanelLayout.setVerticalGroup(
            BieuDo_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setText("Thời Gian Bắt Đầu :");

        jLabel14.setText("Thời Gian Kết Thúc :");

        txt_NgayBatDau_BieuDo.setDateFormatString("yyyy-MM-dd");

        txt_Ngay_KT_BieuDo.setDateFormatString("yyyy-MM-dd");

        btn_Loc.setText("Lọc");
        btn_Loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_NgayBatDau_BieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Ngay_KT_BieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btn_Loc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Loc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_NgayBatDau_BieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(txt_Ngay_KT_BieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout Trang_Chung_ChuyenLayout = new javax.swing.GroupLayout(Trang_Chung_Chuyen);
        Trang_Chung_Chuyen.setLayout(Trang_Chung_ChuyenLayout);
        Trang_Chung_ChuyenLayout.setHorizontalGroup(
            Trang_Chung_ChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Trang_Chung_ChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
            .addComponent(BieuDo_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Trang_Chung_ChuyenLayout.setVerticalGroup(
            Trang_Chung_ChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Trang_Chung_ChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BieuDo_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tong_SP_BanDuoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Tổng Sản Phẩm Bán Được:");

        jLabel9.setText("Nhập Ngày Sản Phẩm:");

        lb_HienTongSanPhamDaBanTheoNgay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout Tong_SP_BanDuocLayout = new javax.swing.GroupLayout(Tong_SP_BanDuoc);
        Tong_SP_BanDuoc.setLayout(Tong_SP_BanDuocLayout);
        Tong_SP_BanDuocLayout.setHorizontalGroup(
            Tong_SP_BanDuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tong_SP_BanDuocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tong_SP_BanDuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_HienTongSanPhamDaBanTheoNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Tong_SP_BanDuocLayout.createSequentialGroup()
                        .addGroup(Tong_SP_BanDuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_Tong_SP_BanDuoc_TheoNgay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Tong_SP_BanDuocLayout.setVerticalGroup(
            Tong_SP_BanDuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tong_SP_BanDuocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Tong_SP_BanDuoc_TheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_HienTongSanPhamDaBanTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SanPham_BanChay.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Sản Phẩm Bán Chạy"));

        tbl_Bang_SP_BanChay.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_Bang_SP_BanChay);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setText("Nhập Thời Gian Bắt Đầu :");

        jLabel15.setText("Nhập Thời Gian Bắt Đầu :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txt_ThoiGian_BD_SPBC, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txt_ThoiGian_KT_SPBC, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ThoiGian_BD_SPBC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ThoiGian_KT_SPBC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SanPham_BanChayLayout = new javax.swing.GroupLayout(SanPham_BanChay);
        SanPham_BanChay.setLayout(SanPham_BanChayLayout);
        SanPham_BanChayLayout.setHorizontalGroup(
            SanPham_BanChayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPham_BanChayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        SanPham_BanChayLayout.setVerticalGroup(
            SanPham_BanChayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPham_BanChayLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Reset");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DoanhThu_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LoiNhuan_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Tong_SP_BanDuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Trang_Chung_Chuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SanPham_BanChay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LoiNhuan_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tong_SP_BanDuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DoanhThu_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Trang_Chung_Chuyen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SanPham_BanChay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_LocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LocActionPerformed
        // TODO add your handling code here:
        java.util.Date util_NgayBD = txt_NgayBatDau_BieuDo.getDate();
        java.util.Date util_NgayKT = txt_Ngay_KT_BieuDo.getDate();

        if (util_NgayBD == null || util_NgayKT == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!");
            return;
        }

// Chuyển đổi sang java.sql.Date
        java.sql.Date sql_NgayBD = new java.sql.Date(util_NgayBD.getTime());
        java.sql.Date sql_NgayKT = new java.sql.Date(util_NgayKT.getTime());

        QL_DoanhThu thongKeDAO = new QL_DoanhThu();
        List<BieuDo_3_O> danhSach = thongKeDAO.layThongKeTheoNgay(sql_NgayBD, sql_NgayKT);
        veBieuDoDoanhThu(danhSach);


    }//GEN-LAST:event_btn_LocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BieuDo_Panel;
    private javax.swing.JPanel DoanhThu_Panel;
    private javax.swing.JPanel LoiNhuan_Panel;
    private javax.swing.JPanel SanPham_BanChay;
    private javax.swing.JPanel Tong_SP_BanDuoc;
    private javax.swing.JPanel Trang_Chung_Chuyen;
    private javax.swing.JButton btn_Loc;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_HienDoanhThu;
    private javax.swing.JLabel lb_HienLoiNhuan;
    private javax.swing.JLabel lb_HienTongSanPhamDaBanTheoNgay;
    private javax.swing.JTable tbl_Bang_SP_BanChay;
    private com.toedter.calendar.JDateChooser txt_NgayBatDau_BieuDo;
    private com.toedter.calendar.JDateChooser txt_Ngay_KT_BieuDo;
    private com.toedter.calendar.JDateChooser txt_ThoiGian_BD_SPBC;
    private com.toedter.calendar.JDateChooser txt_ThoiGian_KT_SPBC;
    private com.toedter.calendar.JDateChooser txt_Tong_SP_BanDuoc_TheoNgay;
    // End of variables declaration//GEN-END:variables
}
