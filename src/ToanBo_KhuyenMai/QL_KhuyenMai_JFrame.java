/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ToanBo_KhuyenMai;
// 📅 Xử lý ngày tháng

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
// 🗓️ Tương tác với SQL kiểu DATE
import java.sql.*;
import java.time.ZoneId;

// 🖥️ Giao diện Swing
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QL_KhuyenMai_JFrame extends javax.swing.JFrame {

    DefaultTableModel TableModel_SP = new DefaultTableModel();
    DefaultTableModel TableModel_DangHoatDong = new DefaultTableModel();
    DefaultTableModel TableModel_KhongHoatDong = new DefaultTableModel();
    int InDex = -1;
    QL_KhuyenMai qlkm = new QL_KhuyenMai();

    /**
     * Creates new form QL_KhuyenMai_JFrame
     */
    public QL_KhuyenMai_JFrame() {
        initComponents();
        Initable();
        FillToTable();
        Initable_KM01();
        FillToTable_KM01();
        Initable_KM02();
        FillToTable_KM02();

        String Ma_KM_TuDong = String.format("KM%03d", qlkm.getSoLuongKhuyenMai() + 1);
        txt_Ma_KM.setText(Ma_KM_TuDong);  // txt_MaKM là ô hiển thị mã khuyến mãi
        txt_Ma_KM.setEnabled(false);

        // Sử Lý Chọn
        cbox_HinhThuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XuLy_HinhThucKM(); // gọi hàm xử lý mỗi khi người dùng đổi lựa chọn
            }
        });
        // Ngày Bắt Đầu KM

        rdo_HoatDong.setSelected(false);
        rdo_KhongHoatDong.setSelected(false);
        rdo_HoatDong.setEnabled(false);
        rdo_KhongHoatDong.setEnabled(false);
        txt_DiemTichLuy.setEnabled(false);
        txt_TienMat.setEnabled(false);

        // Sự Kiện Cho Ngày Bắt Đầu Và Ngày Kết thúc
        PropertyChangeListener ngayListener = evt -> {
            kiemTraNgayKhuyenMai();
            CapNhatTrangThaiTheoNgay();
        };

        txt_NgayBatDau_KM.getDateEditor().addPropertyChangeListener("date", ngayListener);
        txt_NgayKetThuc_KM.getDateEditor().addPropertyChangeListener("date", ngayListener);
    }

    public void kiemTraNgayKhuyenMai() {
        if (isNgayKhuyenMaiHopLe(true)) {
            // Ngày hợp lệ, có thể xử lý tiếp
        }
    }

    public void CapNhatTrangThaiTheoNgay() {
        if (!isNgayKhuyenMaiHopLe(false)) {
            return;
        }

        LocalDate ngayHienTai = LocalDate.now();
        LocalDate ngayBatDau = txt_NgayBatDau_KM.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayKetThuc = txt_NgayKetThuc_KM.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (ngayHienTai.isBefore(ngayBatDau)) {
            rdo_HoatDong.setSelected(false);
            rdo_KhongHoatDong.setSelected(true);
        } else if (!ngayHienTai.isAfter(ngayKetThuc)) {
            rdo_HoatDong.setSelected(true);
            rdo_KhongHoatDong.setSelected(false);
        } else {
            rdo_HoatDong.setSelected(false);
            rdo_KhongHoatDong.setSelected(true);
        }
    }

    public boolean isNgayKhuyenMaiHopLe(boolean kiemTraNgayTrongQuaKhu) {
        java.util.Date ngayBatDauDate = txt_NgayBatDau_KM.getDate();
        java.util.Date ngayKetThucDate = txt_NgayKetThuc_KM.getDate();
        LocalDate homNay = LocalDate.now();

        if (ngayBatDauDate == null || ngayKetThucDate == null) {
            JOptionPane.showMessageDialog(null, "⚠️ Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        LocalDate ngayBatDau = ngayBatDauDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayKetThuc = ngayKetThucDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (ngayKetThuc.isBefore(ngayBatDau)) {
            JOptionPane.showMessageDialog(null, "❌ Ngày kết thúc không được trước ngày bắt đầu!", "Lỗi ngày", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (kiemTraNgayTrongQuaKhu) {
            if (ngayBatDau.isBefore(homNay)) {
                JOptionPane.showMessageDialog(null, "❌ Ngày bắt đầu không được nằm trong quá khứ!", "Lỗi ngày", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (ngayKetThuc.isBefore(homNay)) {
                JOptionPane.showMessageDialog(null, "❌ Ngày kết thúc không được nằm trong quá khứ!", "Lỗi ngày", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        return true;
    }
//    public void kiemTraNgayKhuyenMai() {
//        try {
//            Date ngayBatDauDate = (Date) txt_NgayBatDau_KM.getDate();
//            Date ngayKetThucDate = (Date) txt_NgayKetThuc_KM.getDate();
//
//            if (ngayBatDauDate == null || ngayKetThucDate == null) {
//                JOptionPane.showMessageDialog(null, "⚠️ Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            LocalDate ngayBatDau = ngayBatDauDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate ngayKetThuc = ngayKetThucDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate homNay = LocalDate.now();
//
//            if (ngayKetThuc.isBefore(ngayBatDau)) {
//                JOptionPane.showMessageDialog(null, "❌ Ngày kết thúc không được trước ngày bắt đầu!", "Lỗi ngày", JOptionPane.WARNING_MESSAGE);
//                txt_NgayKetThuc_KM.setDate(Date.from(ngayBatDau.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                return;
//            }
//
//            if (ngayBatDau.isBefore(homNay)) {
//                JOptionPane.showMessageDialog(null, "❌ Ngày bắt đầu không được nằm trong quá khứ!", "Lỗi ngày", JOptionPane.WARNING_MESSAGE);
//                txt_NgayBatDau_KM.setDate(Date.from(homNay.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                return;
//            }
//
//            if (ngayKetThuc.isBefore(homNay)) {
//                JOptionPane.showMessageDialog(null, "❌ Ngày kết thúc không được nằm trong quá khứ!", "Lỗi ngày", JOptionPane.WARNING_MESSAGE);
//                txt_NgayKetThuc_KM.setDate(Date.from(homNay.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                return;
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "❌ Lỗi xử lý ngày khuyến mãi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    // Cập Nhật Khuyến Mãi Thai Ngày
//    public void CapNhatTrangThaiTheoNgay() {
//        // 📅 Lấy ngày hiện tại
//        LocalDate ngayHienTai = LocalDate.now();
//
//        // 📥 Lấy ngày từ giao diện
//        java.util.Date utilDateBatDau = txt_NgayBatDau_KM.getDate();
//        java.util.Date utilDateKetThuc = txt_NgayKetThuc_KM.getDate();
//
//        // ❌ Kiểm tra rỗng
//        if (utilDateBatDau == null || utilDateKetThuc == null) {
//            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc!");
//            rdo_HoatDong.setSelected(false);
//            rdo_KhongHoatDong.setSelected(false);
//            return;
//        }
//
//        // 🔄 Chuyển sang LocalDate
//        LocalDate ngayBatDau;
//        LocalDate ngayKetThuc;
//        try {
//            ngayBatDau = utilDateBatDau.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            ngayKetThuc = utilDateKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "❌ Lỗi chuyển đổi ngày: " + e.getMessage());
//            rdo_HoatDong.setSelected(false);
//            rdo_KhongHoatDong.setSelected(false);
//            return;
//        }
//
//        // 🧠 Kiểm tra logic ngày
//        if (ngayKetThuc.isBefore(ngayBatDau)) {
//            JOptionPane.showMessageDialog(this, "❌ Ngày kết thúc không thể trước ngày bắt đầu!");
//            rdo_HoatDong.setSelected(false);
//            rdo_KhongHoatDong.setSelected(false);
//            return;
//        }
//
//        // ✅ Cập nhật trạng thái
//        if (ngayHienTai.isBefore(ngayBatDau)) {
//            rdo_HoatDong.setSelected(false);
//            rdo_KhongHoatDong.setSelected(true);
//        } else if (!ngayHienTai.isAfter(ngayKetThuc)) {
//            rdo_HoatDong.setSelected(true);
//            rdo_KhongHoatDong.setSelected(false);
//        } else {
//            rdo_HoatDong.setSelected(false);
//            rdo_KhongHoatDong.setSelected(true);
//        }
//
//        // 📦 Nếu cần lưu vào CSDL
//        java.sql.Date sqlDateBatDau = new java.sql.Date(utilDateBatDau.getTime());
//        java.sql.Date sqlDateKetThuc = new java.sql.Date(utilDateKetThuc.getTime());
//        // 👉 sqlDateBatDau và sqlDateKetThuc đã sẵn sàng để insert/update
//    }

    public void Initable() {
        TableModel_SP = new DefaultTableModel();
        String[] cols = {"Mã KM", "Tên KM", "Mô Tả", "Hình Thức", "Giá Trị Yêu Cầu", "Giá Trị", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"};
        TableModel_SP.setColumnIdentifiers(cols);
        tbl_KhuyenMai.setModel(TableModel_SP);
    }

    // Hiển Thị Tất Cả
    public void FillToTable() {
        TableModel_SP.setRowCount(0);
        for (KhuyenMai km : qlkm.Get_All()) {
            TableModel_SP.addRow(qlkm.GetRow(km));
        }
    }

    // Khuyến Mãi Theo Trạng Thái 01
    public void Initable_KM01() {
        TableModel_DangHoatDong = new DefaultTableModel();
        String[] cols = {"Mã KM", "Tên KM", "Mô Tả", "Hình Thức", "Điểm Yêu Cầu ", "Giá Trị", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Ngày Trong Tháng", "Điều Kiện"};
        TableModel_DangHoatDong.setColumnIdentifiers(cols);
        tbl_KM_DangHoatDong.setModel(TableModel_DangHoatDong);
    }

    // Hiển Thị Tất Cả
    public void FillToTable_KM01() {
        TableModel_KhongHoatDong.setRowCount(0);
        for (KhuyenMai_8_O km : qlkm.Get_All_KM01()) {
            TableModel_DangHoatDong.addRow(qlkm.GetRow_KM01(km));
        }
    }

    // Khuyến Mãi Theo Trạng Thái 02
    public void Initable_KM02() {
        TableModel_KhongHoatDong = new DefaultTableModel();
        String[] cols = {"Mã KM", "Tên KM", "Mô Tả", "Hình Thức", "Điểm Yêu Cầu ", "Giá Trị", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Ngày Trong Tháng", "Điều Kiện"};
        TableModel_KhongHoatDong.setColumnIdentifiers(cols);
        tbl_KM_KhongHoatDong.setModel(TableModel_KhongHoatDong);
    }

    // Hiển Thị Tất Cả
    public void FillToTable_KM02() {
        TableModel_KhongHoatDong.setRowCount(0);
        for (KhuyenMai_8_O km : qlkm.Get_All_KM02()) {
            TableModel_KhongHoatDong.addRow(qlkm.GetRow_KM02(km));
        }
    }

    public void LamMoi_KM() {
        txt_Ten_KM.setText("");
        txt_MoTa_KM.setText("");
        cbox_HinhThuc.setSelectedItem("Điểm Tích Luỹ");
        txt_DiemTichLuy.setValue(0);
        txt_GiaTri_KM.setText("");
        txt_NgayBatDau_KM.setDate(null);
        txt_NgayKetThuc_KM.setDate(null);
        btg_TrangThai.clearSelection();
        String maTuDong = String.format("KM%03d", qlkm.getSoLuongKhuyenMai() + 1);
        txt_Ma_KM.setText(maTuDong);  // txt_MaKM là ô hiển thị mã khuyến mãi
        txt_Ma_KM.setEnabled(false);
    }

    public void Them_KM() {
        // 🎯 Tạo mã khuyến mãi tự động
        String Ma_KM = String.format("KM%03d", qlkm.getSoLuongKhuyenMai() + 1);
        txt_Ma_KM.setText(Ma_KM);
        txt_Ma_KM.setEnabled(false);

        // 📋 Lấy thông tin từ form
        String Ten_KM = txt_Ten_KM.getText().trim();
        String MoTa_KM = txt_MoTa_KM.getText().trim();
        String HinhThuc_KM = cbox_HinhThuc.getSelectedItem().toString().trim();
        float GiaTri_KM;

        // 📌 Kiểm tra giá trị khuyến mãi
        try {
            String input = txt_GiaTri_KM.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Giá trị khuyến mãi không được để trống!");
                return;
            }
            if (!input.matches("\\d+(\\.\\d{1,2})?")) {
                JOptionPane.showMessageDialog(this, "❌ Giá trị khuyến mãi phải là số hợp lệ!");
                return;
            }

            GiaTri_KM = Float.parseFloat(input);
            if (GiaTri_KM < 1000 || GiaTri_KM > 1000000) {
                JOptionPane.showMessageDialog(this, "⚠️ Giá trị khuyến mãi phải từ 1.000 đến 1.000.000!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Giá trị nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🧮 Lấy các điều kiện theo hình thức
        float DiemYeuCau = (float) txt_DiemTichLuy.getValue();
        float TienMat = 0;

        if (HinhThuc_KM.toLowerCase().contains("điểm")) {
            if (DiemYeuCau <= 0) {
                JOptionPane.showMessageDialog(this, "❌ Vui lòng nhập số điểm tích lũy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (HinhThuc_KM.toLowerCase().contains("tiền")) {
            try {
                TienMat = Integer.parseInt(txt_TienMat.getText().trim());
                if (TienMat <= 0) {
                    JOptionPane.showMessageDialog(this, "❌ Vui lòng nhập số tiền hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "❌ Số tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else if (HinhThuc_KM.toLowerCase().contains("null")) {
            JOptionPane.showMessageDialog(this, "❌ Hình Thức Không Được Để Là null!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            JOptionPane.showMessageDialog(this, "❌ Vui lòng chọn hình thức khuyến mãi hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 📅 Kiểm tra ngày
        LocalDate bd = DateUtils.convertToLocalDate(txt_NgayBatDau_KM.getDate());
        LocalDate kt = DateUtils.convertToLocalDate(txt_NgayKetThuc_KM.getDate());
        LocalDate ngayHienTai = LocalDate.now();

        if (bd.isBefore(ngayHienTai)) {
            JOptionPane.showMessageDialog(this, "❌ Ngày bắt đầu phải từ hôm nay!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (kt.isBefore(bd)) {
            JOptionPane.showMessageDialog(this, "❌ Ngày kết thúc phải sau ngày bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean TrangThai_KM = !bd.isAfter(ngayHienTai);
        if (TrangThai_KM) {
            rdo_HoatDong.setSelected(true);
        } else {
            rdo_KhongHoatDong.setSelected(true);
        }

        // 📦 Tạo đối tượng khuyến mãi
        java.sql.Date Ngay_BD_KM = java.sql.Date.valueOf(bd);
        java.sql.Date Ngay_KT_KM = java.sql.Date.valueOf(kt);

        float dieuKien = 0;
        if (HinhThuc_KM.toLowerCase().contains("điểm")) {
            dieuKien = DiemYeuCau;
        } else if (HinhThuc_KM.toLowerCase().contains("tiền")) {
            dieuKien = TienMat;
        } 

        KhuyenMai km = new KhuyenMai(Ma_KM, Ten_KM, MoTa_KM, HinhThuc_KM,
                dieuKien, GiaTri_KM, Ngay_BD_KM, Ngay_KT_KM, TrangThai_KM);

        int kq = qlkm.Them_KM(km);
        if (kq == 1) {
            JOptionPane.showMessageDialog(this, "✅ Thêm khuyến mãi thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thêm khuyến mãi thất bại!");
        }
    }

    public void Xoa_KM() {
        InDex = tbl_KhuyenMai.getSelectedRow();
        if (InDex >= 0) {
            String TheoMa_KM = qlkm.Get_All().get(InDex).getMa_KM();
            String Ten_KM = qlkm.Get_All().get(InDex).getTen_KM();

            int Choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Xoá Khuyến Mãi:"
                    + "\n Mã KM: " + TheoMa_KM
                    + "\n Tên KM:" + Ten_KM, "Xác Nhận Xoá Khuyến Mãi ?", JOptionPane.YES_NO_OPTION);
            if (Choice == JOptionPane.YES_OPTION) {
                int ReSult = qlkm.Xoa_KM(TheoMa_KM);
                if (ReSult == 1) {
                    JOptionPane.showMessageDialog(this, "Xoá Khuyến Mãi:"
                            + "\n Mã KM: " + TheoMa_KM
                            + "\n Tên KM: " + Ten_KM
                            + "\n Thành Công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá Khuyến Mãi Thất Bại."
                            + "\n Mã KM: " + TheoMa_KM
                            + "\n Tên KM: " + Ten_KM
                            + "\n Thát Bại.");
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Dữ Liệu Khuyến Mãi Trong Bảng Để Xoá Khuyến Mãi.");
            return;
        }
    }

    public void Sua_KM() {
        int index = tbl_KhuyenMai.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn dòng cần sửa trong bảng!");
            return;
        }

        if (!isNgayKhuyenMaiHopLe(true)) {
            return;
        }

        try {
            String maKM = txt_Ma_KM.getText().trim();
            String tenKM = txt_Ten_KM.getText().trim();
            String moTaKM = txt_MoTa_KM.getText().trim();
            String hinhThucKM = cbox_HinhThuc.getSelectedItem().toString().trim();

            float GiaTri_YeuCau_KM = 0;

            if (hinhThucKM.equalsIgnoreCase("Điểm Tích Luỹ")) {
                try {
                    GiaTri_YeuCau_KM = Float.parseFloat(txt_DiemTichLuy.getValue().toString());
                    if (GiaTri_YeuCau_KM <= 0) {
                        JOptionPane.showMessageDialog(this, "❌ Điểm tích lũy phải lớn hơn 0!");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "❌ Dữ liệu điểm tích lũy không hợp lệ!");
                    return;
                }

            } else if (hinhThucKM.equalsIgnoreCase("Tiền Mặt")) {
                try {
                    String TienMatString = txt_TienMat.getText().trim();
                    if (!TienMatString.matches("\\d+(\\.\\d{1,2})?")) {
                        JOptionPane.showMessageDialog(this, "❌ Tiền mặt phải là số!");
                        return;
                    }
                    GiaTri_YeuCau_KM = Float.parseFloat(TienMatString);
                    if (GiaTri_YeuCau_KM <= 0) {
                        JOptionPane.showMessageDialog(this, "❌ Giá trị tiền mặt phải lớn hơn 0!");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "❌ Dữ liệu tiền mặt không hợp lệ!");
                    return;
                }

            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn hình thức khuyến mãi hợp lệ!");
                return;
            }

            String giaTriInput = txt_GiaTri_KM.getText().trim();
            if (!giaTriInput.matches("\\d+(\\.\\d{1,2})?")) {
                JOptionPane.showMessageDialog(this, "❌ Giá trị KM chỉ được nhập số!");
                return;
            }

            float giaTriKM = Float.parseFloat(giaTriInput);
            if (giaTriKM < 1000 || giaTriKM > 1000000) {
                JOptionPane.showMessageDialog(this, "⚠️ Giá trị KM phải từ 1.000 đến 1.000.000!");
                return;
            }

            LocalDate bd = txt_NgayBatDau_KM.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate kt = txt_NgayKetThuc_KM.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date ngayBD = java.sql.Date.valueOf(bd);
            Date ngayKT = java.sql.Date.valueOf(kt);
            boolean trangThai = rdo_HoatDong.isSelected();

            KhuyenMai km = new KhuyenMai(maKM, tenKM, moTaKM, hinhThucKM, GiaTri_YeuCau_KM, giaTriKM, ngayBD, ngayKT, trangThai);

            String maCu = qlkm.Get_All().get(index).getMa_KM();
            int result = qlkm.Sua_KM(km, maCu);

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "✅ Sửa thành công!\n🔁 Mã cũ: " + maCu + "\n🆕 Mã mới: " + maKM);
                // Gợi ý: loadTable(); resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Sửa thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Lỗi khi sửa khuyến mãi: " + e.getMessage());
        }
    }

    public void ShowDetail() {
        int index = tbl_KhuyenMai.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn dòng cần xem chi tiết!");
            return;
        }

        KhuyenMai km = qlkm.Get_All().get(index);

        // 🌟 Hiển thị thông tin chung
        txt_Ma_KM.setText(km.getMa_KM());
        txt_Ten_KM.setText(km.getTen_KM());
        txt_MoTa_KM.setText(km.getMoTa_KM());
        cbox_HinhThuc.setSelectedItem(km.getHinhThuc_KM());

        // 🔄 Reset tất cả trước
        txt_TienMat.setText("0");
        txt_DiemTichLuy.setValue(0);

        txt_TienMat.setEnabled(false);
        txt_DiemTichLuy.setEnabled(false);

        // 💰 Phân biệt theo hình thức KM
        String hinhThuc = km.getHinhThuc_KM().toLowerCase();

        if (hinhThuc.contains("tiền")) {
            txt_TienMat.setText(String.valueOf(km.getGiaTri_YeuCau_KM()));
            txt_TienMat.setEnabled(true);
        } else if (hinhThuc.contains("điểm")) {
            txt_DiemTichLuy.setValue(km.getGiaTri_YeuCau_KM());
            txt_DiemTichLuy.setEnabled(true);
        } 

        // 🎯 Giá trị khuyến mãi
        txt_GiaTri_KM.setText(String.valueOf(km.getGiaTri_KM()));

        // 📅 Hiển thị ngày khuyến mãi
        txt_NgayBatDau_KM.setDate(new Date(km.getNgay_BD().getTime()));
        txt_NgayKetThuc_KM.setDate(new Date(km.getNgay_KT().getTime()));

        // 🚦 Trạng thái hoạt động
        rdo_HoatDong.setSelected(km.isTrangThai());
        rdo_KhongHoatDong.setSelected(!km.isTrangThai());
    }

    public void XuLy_HinhThucKM() {
        String hinhThuc = cbox_HinhThuc.getSelectedItem().toString().trim();

        // Reset tất cả trước
        txt_TienMat.setEnabled(false);
        txt_DiemTichLuy.setEnabled(false);

        txt_TienMat.setText("0");
        txt_DiemTichLuy.setValue(0);

        if (hinhThuc.equalsIgnoreCase("Tiền Mặt") || hinhThuc.contains("Tiền")) {
            txt_TienMat.setEnabled(true);
        } else if (hinhThuc.equalsIgnoreCase("Điểm Tích Luỹ") || hinhThuc.contains("Điểm")) {
            txt_DiemTichLuy.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hình thức khuyến mãi hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txt_GiaTri_KM.setEnabled(false); // nếu có ô tổng giá trị khuyến mãi
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
        jPanel1 = new javax.swing.JPanel();
        btn_LamMoiKM = new javax.swing.JButton();
        btn_ThemKM = new javax.swing.JButton();
        btn_XoaKM = new javax.swing.JButton();
        btn_SuaKM = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        QL_Table = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_KhuyenMai = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_KM_DangHoatDong = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_KM_KhongHoatDong = new javax.swing.JTable();
        NhapThongTin_Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_Ma_KM = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_Ten_KM = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_GiaTri_KM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        rdo_HoatDong = new javax.swing.JRadioButton();
        rdo_KhongHoatDong = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_MoTa_KM = new javax.swing.JTextArea();
        txt_NgayBatDau_KM = new com.toedter.calendar.JDateChooser();
        txt_NgayKetThuc_KM = new com.toedter.calendar.JDateChooser();
        txt_DiemTichLuy = new javax.swing.JSpinner();
        cbox_HinhThuc = new javax.swing.JComboBox<>();
        txt_TienMat = new javax.swing.JTextField();
        btn_DongTrang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng Chính"));

        btn_LamMoiKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/KhuyenMai.png"))); // NOI18N
        btn_LamMoiKM.setText("Làm Mới");
        btn_LamMoiKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiKMActionPerformed(evt);
            }
        });

        btn_ThemKM.setText("Thêm KM");
        btn_ThemKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemKMActionPerformed(evt);
            }
        });

        btn_XoaKM.setText("Xoá KM");
        btn_XoaKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaKMActionPerformed(evt);
            }
        });

        btn_SuaKM.setText("Sửa KM");
        btn_SuaKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaKMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_XoaKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_LamMoiKM, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(btn_SuaKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ThemKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btn_LamMoiKM, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ThemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btn_SuaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn_XoaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh Sách Khuyến Mãi"));

        tbl_KhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_KhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_KhuyenMai);

        QL_Table.addTab("Tất Cả Khuyến Mãi", jScrollPane3);

        tbl_KM_DangHoatDong.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_KM_DangHoatDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KM_DangHoatDongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_KM_DangHoatDong);

        QL_Table.addTab("Khuyến Mãi Đang Hoạt Động", jScrollPane2);

        tbl_KM_KhongHoatDong.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_KM_KhongHoatDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KM_KhongHoatDongMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_KM_KhongHoatDong);

        QL_Table.addTab("Khuyến Mãi Không Hoạt Động", jScrollPane4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QL_Table)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QL_Table, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
        );

        NhapThongTin_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin Khuyến Mãi"));

        jLabel1.setText("Mã KM:");

        txt_Ma_KM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Tên KM:");

        txt_Ten_KM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Hình Thức:");

        jLabel4.setText("Vui Lòng Nhập:");

        txt_GiaTri_KM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Giá Trị :");

        jLabel6.setText("Ngày Bắt Đầu:");

        jLabel7.setText("Ngày Kết Thúc:");

        jLabel9.setText("Trạng Thái:");

        btg_TrangThai.add(rdo_HoatDong);
        rdo_HoatDong.setText("Đang Hoạt Động");

        btg_TrangThai.add(rdo_KhongHoatDong);
        rdo_KhongHoatDong.setText("Không Hoạt Động");

        jLabel10.setText("Mô Tả:");

        txt_MoTa_KM.setColumns(20);
        txt_MoTa_KM.setRows(5);
        txt_MoTa_KM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txt_MoTa_KM);

        txt_NgayBatDau_KM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_NgayBatDau_KM.setDateFormatString("yyyy-MM-dd");

        txt_NgayKetThuc_KM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_NgayKetThuc_KM.setDateFormatString("yyyy-MM-dd");

        txt_DiemTichLuy.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Theo Điểm"));

        cbox_HinhThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "null", "Điểm Tích Luỹ", "Tiền Mặt", "Phần Trăm" }));
        cbox_HinhThuc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_TienMat.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Theo Tiền"));

        javax.swing.GroupLayout NhapThongTin_PanelLayout = new javax.swing.GroupLayout(NhapThongTin_Panel);
        NhapThongTin_Panel.setLayout(NhapThongTin_PanelLayout);
        NhapThongTin_PanelLayout.setHorizontalGroup(
            NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(txt_Ma_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NhapThongTin_PanelLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(cbox_HinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                                .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_DiemTichLuy, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_GiaTri_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_TienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(rdo_HoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdo_KhongHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12)
                .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_Ten_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_NgayBatDau_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NgayKetThuc_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1))))
                .addGap(9, 9, 9))
        );
        NhapThongTin_PanelLayout.setVerticalGroup(
            NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Ma_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txt_Ten_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NhapThongTin_PanelLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(cbox_HinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_TienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_DiemTichLuy, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_GiaTri_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(NhapThongTin_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdo_HoatDong)
                                .addComponent(rdo_KhongHoatDong)))
                        .addGap(92, 92, 92))
                    .addGroup(NhapThongTin_PanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_NgayBatDau_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_NgayKetThuc_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(NhapThongTin_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_DongTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_DongTrang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(NhapThongTin_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_LamMoiKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiKMActionPerformed
        // TODO add your handling code here:
        LamMoi_KM();
        FillToTable();
        FillToTable_KM01();
        FillToTable_KM02();
    }//GEN-LAST:event_btn_LamMoiKMActionPerformed

    private void btn_ThemKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemKMActionPerformed
        // TODO add your handling code here:
        Them_KM();
        FillToTable();
    }//GEN-LAST:event_btn_ThemKMActionPerformed

    private void btn_SuaKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaKMActionPerformed
        // TODO add your handling code here:
        Sua_KM();
        FillToTable();
    }//GEN-LAST:event_btn_SuaKMActionPerformed

    private void btn_XoaKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaKMActionPerformed
        // TODO add your handling code here:
        Xoa_KM();
        FillToTable();
    }//GEN-LAST:event_btn_XoaKMActionPerformed

    private void btn_DongTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DongTrangActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_DongTrangActionPerformed

    private void tbl_KhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KhuyenMaiMouseClicked
        // TODO add your handling code here:
        ShowDetail();
    }//GEN-LAST:event_tbl_KhuyenMaiMouseClicked

    private void tbl_KM_DangHoatDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KM_DangHoatDongMouseClicked
        // TODO add your handling code here:
        ShowDetail();
    }//GEN-LAST:event_tbl_KM_DangHoatDongMouseClicked

    private void tbl_KM_KhongHoatDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KM_KhongHoatDongMouseClicked
        // TODO add your handling code here:
        ShowDetail();
    }//GEN-LAST:event_tbl_KM_KhongHoatDongMouseClicked

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
            java.util.logging.Logger.getLogger(QL_KhuyenMai_JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QL_KhuyenMai_JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QL_KhuyenMai_JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QL_KhuyenMai_JFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QL_KhuyenMai_JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NhapThongTin_Panel;
    private javax.swing.JTabbedPane QL_Table;
    private javax.swing.ButtonGroup btg_TrangThai;
    private javax.swing.JButton btn_DongTrang;
    private javax.swing.JButton btn_LamMoiKM;
    private javax.swing.JButton btn_SuaKM;
    private javax.swing.JButton btn_ThemKM;
    private javax.swing.JButton btn_XoaKM;
    private javax.swing.JComboBox<String> cbox_HinhThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rdo_HoatDong;
    private javax.swing.JRadioButton rdo_KhongHoatDong;
    private javax.swing.JTable tbl_KM_DangHoatDong;
    private javax.swing.JTable tbl_KM_KhongHoatDong;
    private javax.swing.JTable tbl_KhuyenMai;
    private javax.swing.JSpinner txt_DiemTichLuy;
    private javax.swing.JTextField txt_GiaTri_KM;
    private javax.swing.JTextField txt_Ma_KM;
    private javax.swing.JTextArea txt_MoTa_KM;
    private com.toedter.calendar.JDateChooser txt_NgayBatDau_KM;
    private com.toedter.calendar.JDateChooser txt_NgayKetThuc_KM;
    private javax.swing.JTextField txt_Ten_KM;
    private javax.swing.JTextField txt_TienMat;
    // End of variables declaration//GEN-END:variables
}
