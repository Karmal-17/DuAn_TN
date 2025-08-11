/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ToanBo_BanHang;

import ToanBo_SanPham.SanPham_3_O;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 *
 * @author ADMIN
 */
public class In_HoaDon_JFrame extends javax.swing.JFrame {

    String PathAnh = null;
    private JTextField txtTenCuaHang, txtSlogan, txtTenKH, txtMaKH, txtTenSP, txtDonGia, txtGiamGia, txtTenNV, txtSDT_NV;
    private JSpinner spSoLuong;
    private JTextArea txtHoaDon;
    private JLabel lblLogo;
    private JButton btnChonAnh, btnHienThi;
    private ImageIcon logoImage;
    private DefaultTableModel TableModel;
    private java.util.List<SanPham_3_O> danhSachSP = new java.util.ArrayList<>();

    /**
     * Creates new form In_HoaDon_JFrame
     */
    public In_HoaDon_JFrame() {
        initComponents();

        // Hiện Hoá Đơn
        btn_HienThiHD.addActionListener(e -> hienThiHoaDon());
        add(btn_HienThiHD);

        txt_HienThi_HoaDon.setEditable(false);
    }

    private void chonAnh() {
        lb_HienAnh.setText("");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            PathAnh = file.getAbsolutePath();

            ImageIcon icon = new ImageIcon(PathAnh);

            // Lấy kích thước label
            int labelWidth = lb_HienAnh.getWidth();
            int labelHeight = lb_HienAnh.getHeight();

            // Scale ảnh vừa khít label
            Image img = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            lb_HienAnh.setIcon(new ImageIcon(img));
            lb_HienAnh.setText("");
        }
    }

//    private void hienThiHoaDon() {
//        // Lấy model từ JTable
//        if (TableModel == null) {
//            TableModel = (DefaultTableModel) myTable.getModel();
//        }
//
//        DecimalFormat df = new DecimalFormat("#,###");
//
//        String tenCH = txt_TenCuaHang.getText();
//        String slogan = txt_Slogan.getText();
//        String tenKH = txt_TenKH.getText();
//        String sdtKH = txt_SoDienThoai.getText();
//        Date thoiGianDat = txt_ThoiGianDatHang.getDate();
//        String tenNV = txt_TenNhanVien.getText();
//        String sdtNV = txt_SoDienThoai_NV.getText();
//        String diaChi = txt_DiaChi.getText();
//        Date thoiGianIn = txt_ThoiGianIn_HD.getDate();
//        double giamGia = Double.parseDouble(txt_GiamGia.getText());
//
//        int tongSanPham = 0;
//        double tongTien = 0;
//
//        // Header bảng sản phẩm
//        StringBuilder sbSP = new StringBuilder();
//        sbSP.append(String.format("%-4s %-20s %10s %5s %12s\n",
//                "STT", "Tên sản phẩm", "Đơn giá", "SL", "Thành tiền"));
//        sbSP.append("----------------------------------------------------------\n");
//
//        for (int i = 0; i < TableModel.getRowCount(); i++) {
//            String stt = String.format("%02d", i + 1);
//            String tenSP = TableModel.getValueAt(i, 0).toString();
//            double donGia = Double.parseDouble(TableModel.getValueAt(i, 1).toString());
//            int soLuong = Integer.parseInt(TableModel.getValueAt(i, 2).toString());
//            double thanhTien = donGia * soLuong;
//
//            tongSanPham += soLuong;
//            tongTien += thanhTien;
//
//            sbSP.append(String.format("%-4s %-20s %10s %5d %12s\n",
//                    stt, tenSP, df.format(donGia), soLuong, df.format(thanhTien)));
//        }
//
//        double thanhTienCuoi = tongTien - giamGia;
//
//        // Nội dung QR
//        String noiDungQR = String.format(
//                "%s\n%s\nKH: %s - %s\nTổng: %s VND\nThanh toán: %s VND",
//                tenCH, slogan, tenKH, sdtKH, df.format(tongTien), df.format(thanhTienCuoi)
//        );
//        ImageIcon qrIcon = taoQRCode(noiDungQR, 100, 100);
//
//        // Nội dung hóa đơn
//        StringBuilder sbHD = new StringBuilder();
//        sbHD.append("===== ").append(tenCH).append(" =====\n");
//        sbHD.append(slogan).append("\n\n");
//        sbHD.append("Thời gian đặt: ").append(thoiGianDat).append("\n");
//        sbHD.append("Tên KH: ").append(tenKH).append("\n");
//        sbHD.append("SĐT: ").append(sdtKH).append("\n\n");
//        sbHD.append(sbSP).append("\n");
//        sbHD.append("Tổng sản phẩm: ").append(tongSanPham).append("\n");
//        sbHD.append("Tổng tiền: ").append(df.format(tongTien)).append(" VND\n");
//        sbHD.append("Giảm giá: ").append(df.format(giamGia)).append(" VND\n");
//        sbHD.append("Thành tiền: ").append(df.format(thanhTienCuoi)).append(" VND\n\n");
//        sbHD.append("Nhân viên: ").append(tenNV).append("\n");
//        sbHD.append("SĐT NV: ").append(sdtNV).append("\n");
//        sbHD.append("Địa chỉ: ").append(diaChi).append("\n");
//        sbHD.append("In lúc: ").append(thoiGianIn).append("\n");
//        sbHD.append("-------------------------------------\n");
//        sbHD.append("Ghi chú: ").append(txt_GhiChu.getText()).append("\n");
//        sbHD.append("CẢM ƠN QUÝ KHÁCH! HẸN GẶP LẠI!\n");
//        sbHD.append("Có vấn đề gì xin vui lòng liên hệ qua số điện thoại: ").append(txt_SDT_LienHe.getText()).append("\n");
//
//        txt_HienThi_HoaDon.setText(sbHD.toString());
//
//        // Gán QR code vào label
//        lb_HienMa_QR.setIcon(qrIcon);
//    }
    private void hienThiHoaDon() {
        DecimalFormat df = new DecimalFormat("#,###");

        String tenCH = txt_TenCuaHang.getText();
        String slogan = txt_Slogan.getText();
        String tenKH = txt_TenKH.getText();
        String sdtKH = txt_SoDienThoai.getText();
        Date thoiGianDat = txt_ThoiGianDatHang.getDate();
        String tenNV = txt_TenNhanVien.getText();
        String sdtNV = txt_SoDienThoai_NV.getText();
        String diaChi = txt_DiaChi.getText();
        Date thoiGianIn = txt_ThoiGianIn_HD.getDate();
        double giamGia = Double.parseDouble(txt_GiamGia.getText());

        int tongSanPham = 0;
        double tongTien = 0;

        // Header bảng sản phẩm
        StringBuilder sbSP = new StringBuilder();
        sbSP.append(String.format("%-4s %-20s %10s %5s %12s\n",
                "STT", "Tên sản phẩm", "Đơn giá", "SL", "Thành tiền"));
        sbSP.append("----------------------------------------------------------\n");

        for (int i = 0; i < danhSachSP.size(); i++) {
            SanPham_3_O sp = danhSachSP.get(i);
            String stt = String.format("%02d", i + 1);
            double thanhTien = sp.getDonGia_SP() * sp.getSoLuong_SP();

            tongSanPham += sp.getSoLuong_SP();
            tongTien += thanhTien;

            sbSP.append(String.format("%-4s %-20s %10s %5d %12s\n",
                    stt, sp.getTen_SP(), df.format(sp.getDonGia_SP()), sp.getSoLuong_SP(), df.format(thanhTien)));
        }

        double thanhTienCuoi = tongTien - giamGia;

        // Nội dung QR
        String noiDungQR = String.format(
                "%s\n%s\nKH: %s - %s\nTổng: %s VND\nThanh toán: %s VND",
                tenCH, slogan, tenKH, sdtKH, df.format(tongTien), df.format(thanhTienCuoi)
        );
        ImageIcon qrIcon = taoQRCode(noiDungQR, 100, 100);

        // Nội dung hóa đơn
        StringBuilder sbHD = new StringBuilder();
        sbHD.append("===== ").append(tenCH).append(" =====\n");
        sbHD.append(slogan).append("\n\n");
        sbHD.append("Thời gian đặt: ").append(thoiGianDat).append("\n");
        sbHD.append("Tên KH: ").append(tenKH).append("\n");
        sbHD.append("SĐT: ").append(sdtKH).append("\n\n");
        sbHD.append(sbSP).append("\n");
        sbHD.append("Tổng sản phẩm: ").append(tongSanPham).append("\n");
        sbHD.append("Tổng tiền: ").append(df.format(tongTien)).append(" VND\n");
        sbHD.append("Giảm giá: ").append(df.format(giamGia)).append(" VND\n");
        sbHD.append("Thành tiền: ").append(df.format(thanhTienCuoi)).append(" VND\n\n");
        sbHD.append("Nhân viên: ").append(tenNV).append("\n");
        sbHD.append("SĐT NV: ").append(sdtNV).append("\n");
        sbHD.append("Địa chỉ: ").append(diaChi).append("\n");
        sbHD.append("In lúc: ").append(thoiGianIn).append("\n");
        sbHD.append("-------------------------------------\n");
        sbHD.append("Ghi chú: ").append(txt_GhiChu.getText()).append("\n");
        sbHD.append("CẢM ƠN QUÝ KHÁCH! HẸN GẶP LẠI!\n");
        sbHD.append("Có vấn đề gì xin vui lòng liên hệ qua số điện thoại: ").append(txt_SDT_LienHe.getText()).append("\n");

        txt_HienThi_HoaDon.setText(sbHD.toString());
        lb_HienMa_QR.setIcon(qrIcon);
    }

    // Có vấn đề gì xin vui lòng liên hệ qua số điện thoại
    private ImageIcon taoQRCode(String text, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void Them_SP() {
        String tenSP = txt_Ten_SP.getText().trim();
        String donGiaStr = txt_DonGia.getText().trim();
        int soLuong = (int) txt_SoLuong.getValue();

        if (tenSP.isEmpty() || donGiaStr.isEmpty() || soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
            return;
        }
        float donGia;
        try {
            donGia = Float.parseFloat(donGiaStr);
            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!");
                return;
            }

            // Thêm vào danh sách sản phẩm
            danhSachSP.add(new SanPham_3_O(tenSP, donGia, soLuong));

            // Reset ô nhập
            txt_Ten_SP.setText("");
            txt_DonGia.setText("");
            txt_SoLuong.setValue(0);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        txt_HienThi_HoaDon = new javax.swing.JTextArea();
        btn_ChonAnh = new javax.swing.JButton();
        lb_HienAnh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_TenCuaHang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_Slogan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_ThoiGianDatHang = new com.toedter.calendar.JDateChooser();
        txt_SoDienThoai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_TenKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_Ten_SP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_SoLuong = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        txt_DonGia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_TenNhanVien = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_SoDienThoai_NV = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_GiamGia = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_ThoiGianIn_HD = new com.toedter.calendar.JDateChooser();
        btn_HienThiHD = new javax.swing.JButton();
        btn_Them_SP = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txt_SDT_LienHe = new javax.swing.JTextField();
        lb_HienMa_QR = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_DiaChi = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_GhiChu = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_HienThi_HoaDon.setColumns(20);
        txt_HienThi_HoaDon.setRows(5);
        txt_HienThi_HoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hiển Thị Hoá Đơn"));
        jScrollPane1.setViewportView(txt_HienThi_HoaDon);

        btn_ChonAnh.setText("Chọn Ảnh");
        btn_ChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChonAnhActionPerformed(evt);
            }
        });

        lb_HienAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Tên Cửa Hàng: ");

        jLabel2.setText("Slogan :");

        jLabel3.setText("Thời Gian Đặt Hàng:");

        txt_ThoiGianDatHang.setDateFormatString("yyyy-MM-dd");

        jLabel4.setText("Số Điện Thoại:");

        jLabel5.setText("Tên KH:");

        jLabel6.setText("Số Lượng:");

        jLabel7.setText("Tên Sản Phẩm:");

        jLabel8.setText("Đơn Giá:");

        jLabel9.setText("Tên Nhân Viên:");

        jLabel10.setText("Số Điện Thoại Nhân Viên:");

        jLabel11.setText("Giảm Giá :");

        jLabel12.setText("Thời Gian In Hoá Đơn:");

        txt_ThoiGianIn_HD.setDateFormatString("yyyy-MM-dd");

        btn_HienThiHD.setText("Hiển Thị Thử");

        btn_Them_SP.setText("Thêm SP");
        btn_Them_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them_SPActionPerformed(evt);
            }
        });

        jLabel13.setText("Số Điện Thoại Liên Hệ:");

        lb_HienMa_QR.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hiện Mã QR Chi Tiết Hoá Đơn"));

        jLabel14.setText("Địa Chỉ:");

        jLabel15.setText("Ghi Chú:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_TenCuaHang)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Slogan, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ThoiGianDatHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_HienAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ChonAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_TenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_HienMa_QR, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_GhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(114, 114, 114)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_Ten_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_Them_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_DonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_GiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_ThoiGianIn_HD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_TenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_SoDienThoai_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_SDT_LienHe, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_HienThiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lb_HienAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_ChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(103, 103, 103))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_TenCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_Slogan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_ThoiGianDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_TenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_Ten_SP)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_Them_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_DonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_GiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_TenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_SoDienThoai_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_ThoiGianIn_HD, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_SDT_LienHe, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_HienThiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_HienMa_QR, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_GhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChonAnhActionPerformed
        // TODO add your handling code here:
        chonAnh();
    }//GEN-LAST:event_btn_ChonAnhActionPerformed

    private void btn_Them_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them_SPActionPerformed
        // TODO add your handling code here:
        Them_SP();
    }//GEN-LAST:event_btn_Them_SPActionPerformed

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
            java.util.logging.Logger.getLogger(In_HoaDon_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(In_HoaDon_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(In_HoaDon_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(In_HoaDon_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new In_HoaDon_JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ChonAnh;
    private javax.swing.JButton btn_HienThiHD;
    private javax.swing.JButton btn_Them_SP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_HienAnh;
    private javax.swing.JLabel lb_HienMa_QR;
    private javax.swing.JTextField txt_DiaChi;
    private javax.swing.JTextField txt_DonGia;
    private javax.swing.JTextField txt_GhiChu;
    private javax.swing.JTextField txt_GiamGia;
    private javax.swing.JTextArea txt_HienThi_HoaDon;
    private javax.swing.JTextField txt_SDT_LienHe;
    private javax.swing.JTextField txt_Slogan;
    private javax.swing.JTextField txt_SoDienThoai;
    private javax.swing.JTextField txt_SoDienThoai_NV;
    private javax.swing.JSpinner txt_SoLuong;
    private javax.swing.JTextField txt_TenCuaHang;
    private javax.swing.JTextField txt_TenKH;
    private javax.swing.JTextField txt_TenNhanVien;
    private javax.swing.JTextField txt_Ten_SP;
    private com.toedter.calendar.JDateChooser txt_ThoiGianDatHang;
    private com.toedter.calendar.JDateChooser txt_ThoiGianIn_HD;
    // End of variables declaration//GEN-END:variables
}
