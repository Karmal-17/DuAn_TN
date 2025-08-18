/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ToanBo_BanHang;

import DBConnect.Chua_Bien;
import ToanBo_KhachHang.KhachHang_3_O_In_HD;
import ToanBo_KhachHang.KhachHang_4_O;
import ToanBo_SanPham.SanPham_6_O;
import javax.swing.table.DefaultTableModel;
import ToanBo_SanPham.QL_Tao_SanPham;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import ToanBo_KhachHang.QL_KhachHang;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import ToanBo_KhuyenMai.KhuyenMai_4_O;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import ToanBo_KhuyenMai.QL_KhuyenMai;
import ToanBo_SanPham.LoaiSanPham;
import java.util.stream.Stream;
import javax.swing.JMenuItem;
import javax.swing.JFrame;
import ToanBo_SanPham.QL_Tao_LoaiSanPham;
import ToanBo_SanPham.SanPham_5_O;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellRenderer;
import ToanBo_KhuyenMai.KhuyenMai;
import ToanBo_SanPham.HoaDon_PDF;
import ToanBo_SanPham.SanPham_3_O_In_HD;
import ToanBo_TaiKhoan.Tai_Khoan_3_O_In_HD;
import com.itextpdf.layout.property.HorizontalAlignment;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author ADMIN
 */
public class QL_BanHang_Panel extends javax.swing.JPanel {

    DefaultTableModel TableModel_HD;
    DefaultTableModel TableModel_SP;
    DefaultTableModel TableModel_SP_DaChon;
    QL_Tao_SanPham QL_Tao_SP = new QL_Tao_SanPham();
    QL_Tao_HoaDon QL_Tao_HD = new QL_Tao_HoaDon();
    QL_KhachHang QL_KH = new QL_KhachHang();
    QL_Tao_ChiTiet_HD QL_Tao_CTHD = new QL_Tao_ChiTiet_HD();
    QL_KhuyenMai QL_KM = new QL_KhuyenMai();
    String Ma_HD_DuocChon = ""; // Biến toàn cục để lưu mã hoá đơn được chọn
    int Index = -1;

    private JTable tblLoaiSP;
    private DefaultTableModel modelLoaiSP;
    private QL_Tao_LoaiSanPham LSP_DAO;
    private boolean daChonHoaDon = false;

    /**
     * Creates new form Menu
     */
    public QL_BanHang_Panel() {
        initComponents();
        Initable_SP();
        FillToTable_SP();
        Initable_HD();
        FillToTable_HD();
        Initable_SP_DUOCCHON();

        // Hiện Mã Tài Khoản Đăng Nhập
//        txt_Ma_TK.setEditable(false);
//        String Ma_TaiKhoan = Chua_Bien.Ma_TK;
//        if (Ma_TaiKhoan != null) {
//            txt_Ma_TK.setText(Ma_TaiKhoan);
//        } else {
//            txt_Ma_TK.setText("Vui Lòng Đăng Nhập Để Bán Hàng.");
//        }
        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        txt_ThoiGianTao.setText(thoiGianHienTai.format(dinhDang));
        txt_ThoiGianTao.setEditable(false);

        // Phần Trạng Thái Thanh Toán
        // Phần Có Muốn Tạo Hoá Đơn Hay Không
        btn_Tao_HD.addActionListener(e -> {
            if (btn_Tao_HD.isSelected()) {
                tbtn_HuyTao.setSelected(false);
                // Thực thi logic áp dụng
            }
        });

        tbtn_HuyTao.addActionListener(e -> {
            if (tbtn_HuyTao.isSelected()) {
                btn_Tao_HD.setSelected(false);
                // Thực thi logic huỷ bỏ
            }
        });

        // Phần Khách Hàng
        txt_Ten_KH.setEditable(false);
        // Phần Số Tiền Cần Trả Lại Cho Khách
        txt_SoTien_CanTraLai.setEditable(false);
        // Tìm Kiếm Khách Hàng
        Nhap_KH();
        // Khuyến Mãi 
        Nhap_KM();
        KM();

        // Tự Động Tạo Mã Hoá Đơn Mới
        TuDong_NhapMa_HD();

        LSP_DAO = new QL_Tao_LoaiSanPham();
//        loadTabsSanPham();

        // Chỉnh Ảnh ô Tất Cả Sản Phẩm
        ChinhAnh();

        // Chỉnh Cái Phần Chọn Số Lượng Sản Phẩm
        SpinnerNumberModel SPN_model = new SpinnerNumberModel(1, 1, 1000, 1);
        txt_SoLuong.setModel(SPN_model);

        // Lấy text từ label
        String diemText = lb_HienDiemTL.getText();
        String tongTienText = lb_HienTongTien_ChuaGiamGia.getText();

        // Xử lý null hoặc "Null"
        if (diemText == null || diemText.trim().isEmpty() || diemText.equalsIgnoreCase("null")) {
            diemText = "0";
        }
        if (tongTienText == null || tongTienText.trim().isEmpty() || tongTienText.equalsIgnoreCase("null")) {
            tongTienText = "0";
        }

        // Parse an toàn
        int diemKhach = Integer.parseInt(diemText);
        float tongTien = Float.parseFloat(tongTienText);

        // Gọi hàm
        hienThiKhuyenMaiPhuHop(diemKhach, tongTien);

        // Dòng Hoá Đơn Tự Động Nhập
        txt_MaHD.setEditable(false);

        // Sự Kiện Tự Động Hiển Thị Số Tiền Khách Trả
        txt_SoTienKhachTra.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                tinhTraLai();
            }

            public void removeUpdate(DocumentEvent e) {
                tinhTraLai();
            }

            public void changedUpdate(DocumentEvent e) {
                tinhTraLai();
            }

            private void tinhTraLai() {
                try {
                    String traText = txt_SoTienKhachTra.getText().replaceAll("[^\\d.]", "");
                    float khachTra = Float.parseFloat(traText);

                    String thanhTienText = lb_ThanhTien.getText().replaceAll("[^\\d.]", "");
                    float thanhTien = Float.parseFloat(thanhTienText);

                    float traLai = khachTra - thanhTien;
                    txt_SoTien_CanTraLai.setText(String.format("%.0f VND", traLai));
                } catch (Exception ex) {
                    txt_SoTien_CanTraLai.setText("0 VND");
                }
            }
        });

        // Lấy Điểm Tích Luỹ Của Khách Hàng Khi Đổi
        txt_KhuyenMai.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                xuLyKhuyenMaiTuDong();
            }

            public void removeUpdate(DocumentEvent e) {
                xuLyKhuyenMaiTuDong();
            }

            public void changedUpdate(DocumentEvent e) {
                xuLyKhuyenMaiTuDong(); // chỉ dùng cho styled text
            }
        });

        // Tự Động Khoá Khi Trạng Thái Đã Thanh Toán
        // Khoá In Hoá Đơn
        String Ma_TK = Chua_Bien.Ma_TK;
        txt_Ma_TK.setText(Ma_TK);
        txt_Ma_TK.setEditable(false);
//        btn_InHoaDon.setEnabled(false);
    }

    // Phần Tự Động Khoá Khi Mà Trạng Thái Hoá Đơn Đã Thanh Toán
    public void lockHoaDonIfPaid(String trangThai) {
        if (trangThai.equalsIgnoreCase("Đã Thanh Toán")) {
            // Hiển thị cảnh báo cho người dùng
            JOptionPane.showMessageDialog(null,
                    "⚠️ Hoá đơn này đã được thanh toán.\nBạn không được phép chỉnh sửa hoặc xóa dữ liệu.");

            // Khóa các nút chức năng
            btn_Chon_SP.setEnabled(false);
            btn_Sua.setEnabled(false);
            btn_HuyChon_SP.setEnabled(false);
            btn_Chon_SP.setEnabled(false);
            btn__ThanhToan.setEnabled(false);

            // Khóa các ô nhập liệu
            txt_Ma_KH.setEditable(false);
            txt_KhuyenMai.setEditable(false);
            txt_SoTienKhachTra.setEditable(false);

            // Khóa bảng chi tiết sản phẩm nếu có
            tbl_Bang_SP_DaChon.setEnabled(false); // hoặc setEditable(false) nếu là TableModel tùy chỉnh
        }
    }

    // Xử Lý Khuyến Mãi Tự Động
    public void xuLyKhuyenMaiTuDong() {
        String Ma_KM = txt_KhuyenMai.getText().trim();
        String Ma_KH = txt_Ma_KH.getText().trim();

        float tongTien = 0f;
        int colDonGia = TableModel_SP_DaChon.findColumn("Đơn Giá");
        int colSoLuong = TableModel_SP_DaChon.findColumn("Số Lượng");

        // 🔢 Tính tổng tiền sản phẩm đang chọn
        for (int i = 0; i < TableModel_SP_DaChon.getRowCount(); i++) {
            try {
                float donGia = Float.parseFloat(TableModel_SP_DaChon.getValueAt(i, colDonGia).toString());
                int soLuong = Integer.parseInt(TableModel_SP_DaChon.getValueAt(i, colSoLuong).toString());
                tongTien += donGia * soLuong;
            } catch (Exception e) {
                System.err.println("Lỗi tính tổng tiền: " + e.getMessage());
            }
        }
        lb_HienTongTien_ChuaGiamGia.setText(String.format("%,.0f", tongTien));

        // 🎁 Truy vấn khuyến mãi từ mã nhập
        KhuyenMai km = QL_KM.layKhuyenMaiTheoMa(Ma_KM);
        float tienGiam = 0f;

        if (km != null && km.isTrangThai()) {
            // Nếu khuyến mãi hợp lệ và đang hoạt động
            if (tongTien >= km.getGiaTri_YeuCau_KM()) { // ví dụ: ĐiểmYeuCau là ngưỡng tổng tiền
                tienGiam = km.getGiaTri_KM(); // có thể là số tiền hoặc % tùy logic
            }
        }

        lb_Hien_TienGiamGia.setText(String.format("%,.0f", tienGiam));
        float thanhTien = tongTien - tienGiam;
        lb_ThanhTien.setText(String.format("%,.0f", thanhTien));

        // 🏆 Điểm cộng mới
        int diemCong = (int) (thanhTien / 10000);
        lb_SoDiemDuocCong.setText(diemCong + " điểm");

        // 📌 Cập nhật điểm KH hiện tại nếu có mã KH
        if (!Ma_KH.isEmpty()) {
            int diemTL = QL_KH.layDiemTichLuy(Ma_KH);
            lb_HienDiemTL.setText(diemTL + " Điểm");
        } else {
            lb_HienDiemTL.setText("Không xác định");
        }
    }

    // Cột Ảnh Ở Ô Tất Cả Sản Phẩm
    public void ChinhAnh() {
        tbl_Bang_SP.setRowHeight(50); // ✅ đặt chiều cao dòng

        tbl_Bang_SP.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel lbl = new JLabel();
                lbl.setHorizontalAlignment(JLabel.CENTER);

                if (value != null && value instanceof String path && !path.trim().equals("NULL")) {
                    ImageIcon icon = new ImageIcon(path);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // ✅ chỉnh kích thước ảnh
                    lbl.setIcon(new ImageIcon(img));
                } else {
                    lbl.setText("Không có ảnh");
                }

                // ✅ hiệu ứng khi chọn dòng
                if (isSelected) {
                    lbl.setBackground(new Color(220, 240, 255));
                    lbl.setOpaque(true);
                } else {
                    lbl.setOpaque(false);
                }

                return lbl;
            }
        });
    }

//    private void loadTabsSanPham() {
//        List<LoaiSanPham> dsLoai = LSP_DAO.Get_All_LSP();
//
//        for (LoaiSanPham loai : dsLoai) {
//            List<SanPham_5_O> dsSP = QL_Tao_SP.getSanPhamTheoLoai_5_O(loai.getMa_LSP());
//
//            DefaultTableModel model = new DefaultTableModel() {
//                @Override
//                public boolean isCellEditable(int row, int column) {
//                    return false;
//                }
//            };
//            model.setColumnIdentifiers(new String[]{"Mã SP", "Tên SP", "Mô tả", "Đơn giá", "Hình ảnh"});
//
//            for (SanPham_5_O sp : dsSP) {
//                ImageIcon icon = new ImageIcon(sp.getHinhAnh_SP());
//
//                // Resize ảnh xuống 50x50
//                Image img = icon.getImage();
//                Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//                ImageIcon resizedIcon = new ImageIcon(resizedImg);
//
//                model.addRow(new Object[]{
//                    sp.getMa_SP(),
//                    sp.getTen_SP(),
//                    sp.getMoTa_SP(),
//                    sp.getDonGia_SP(),
//                    resizedIcon
//                });
//            }
//
//            JTable table = new JTable(model);
//            table.setRowHeight(50); // Chiều cao phù hợp với ảnh 50x50
//
//            // Renderer hiển thị ảnh
//            table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//                        boolean hasFocus, int row, int column) {
//                    JLabel label = new JLabel();
//                    label.setHorizontalAlignment(CENTER);
//
//                    if (value instanceof ImageIcon) {
//                        label.setIcon((ImageIcon) value);
//                    } else if (value != null) {
//                        label.setText(value.toString());
//                    }
//
//                    if (isSelected) {
//                        label.setBackground(table.getSelectionBackground());
//                        label.setOpaque(true);
//                    } else {
//                        label.setOpaque(false);
//                    }
//
//                    return label;
//                }
//            });
//
//            JScrollPane scroll = new JScrollPane(table);
//            Ds_LSP.addTab(loai.getTen_LSP(), scroll);
//        }
//    }
    // Tự Động Tạo Hoá Đơn Mới
    public void TuDong_NhapMa_HD() {
        String Ma_HD = QL_Tao_HD.taoMaHoaDonMoi();
        txt_MaHD.setText(Ma_HD);
    }

    //    public void Initable_HD() {
//        TableModel_HD = new DefaultTableModel();
//        String[] cols = {"Mã HD", "Mã TK", "Ngày Tạo", "Tổng Tiền", "Hình Thức TT", "Trạng Thái", "Mã KM", "SOTIENCANTRA", "Mã KH", "Tích Điểm"};
//        TableModel_HD.setColumnIdentifiers(cols);
//        tbl_Bang_HD.setModel(TableModel_HD);
//    }
    public void Initable_HD() {
        TableModel_HD = new DefaultTableModel();
        String[] cols = {"Mã HD", "Mã TK", "Ngày Tạo", "Tổng Tiền", "Hình Thức TT", "Trạng Thái"};
        TableModel_HD.setColumnIdentifiers(cols);
        tbl_Bang_HD.setModel(TableModel_HD);
    }

    public void FillToTable_HD() {
        TableModel_HD.setRowCount(0);
        for (HoaDon_6_O hd : QL_Tao_HD.Get_ALL_HoaDon_6_O()) {
            TableModel_HD.addRow(QL_Tao_HD.Get_Row_HD_6_O(hd));
        }
    }

    public void Initable_SP() {
        TableModel_SP = new DefaultTableModel();
        String[] cols = {"Mã SP", "Tên SP", "Mô Tả SP", "Đơn Giá", "Mã Loại SP", "Hình Ảnh"};
        TableModel_SP.setColumnIdentifiers(cols);
        tbl_Bang_SP.setModel(TableModel_SP);
    }

    // Hiển Thị Tất Cả
//    public void FillToTable_SP() {
//        TableModel_SP.setRowCount(0);
//        for (SanPham_6_O sp : QL_Tao_SP.GetAll_TatCa_SP()) {
//            TableModel_SP.addRow(QL_Tao_SP.GetRow_TatCa_SP(sp));
//        }
//    }
    public void FillToTable_SP() {
        TableModel_SP.setRowCount(0);
        for (SanPham_6_O sp : QL_Tao_SP.GetAll_TatCa_SP()) {
            Object[] row = QL_Tao_SP.GetRow_TatCa_SP(sp);

            // 👇 Cột 5 truyền đường dẫn ảnh (không xử lý JLabel ở đây nữa)
            row[5] = sp.getHinhAnh_SP(); // hoặc (String) row[5] nếu đã có đường dẫn trong mảng

            TableModel_SP.addRow(row);
        }
    }

    public void Initable_SP_DUOCCHON() {
        TableModel_SP_DaChon = new DefaultTableModel();
        String[] cols = {"Mã SP", "Tên SP", "Đơn Giá", "Số Lượng", "Thành Tiền", "Ghi Chú"};
        TableModel_SP_DaChon.setColumnIdentifiers(cols);
        tbl_Bang_SP_DaChon.setModel(TableModel_SP_DaChon);
    }

    public void FillToTable_SP_DUOCCHON() {
        TableModel_SP_DaChon.setRowCount(0); // Xóa dữ liệu cũ

        List<ChiTiet_SP_6_O> danhSachSPDuocChon = QL_Tao_CTHD.Get_ALL_SPDC(Ma_HD_DuocChon);

        for (ChiTiet_SP_6_O chiTietSP : danhSachSPDuocChon) {
            Object[] rowData = QL_Tao_CTHD.Get_Row(chiTietSP);
            TableModel_SP_DaChon.addRow(rowData);
        }
    }

    public void Reset() {
        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        txt_ThoiGianTao.setText(thoiGianHienTai.format(dinhDang));
        btg_HinhThuc_TT.clearSelection();
        txt_MaHD.setText("");
        txt_Ma_KH.setText("");
        txt_Ten_KH.setText("");
        lb_ThanhTien.setText("");
        lb_HienDiemTL.setText("");
        lb_HienTongTien_ChuaGiamGia.setText("");
        lb_Hien_TienGiamGia.setText("");
        lb_SoDiemDuocCong.setText("");
        txt_SoTienKhachTra.setText("");
        txt_SoTien_CanTraLai.setText("");
        btn_Tao_HD.setSelected(false);
        tbtn_HuyTao.setSelected(false);

        FillToTable_HD();
        FillToTable_SP();
        TuDong_NhapMa_HD();
    }

    public int layGiaTriTuLabel(JLabel label) {
        String text = label.getText().replaceAll("[^\\d]", ""); // loại bỏ hết chữ và ký tự không phải số
        if (text.isBlank()) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    public void Tao_HD() {
        String Ma_HD = txt_MaHD.getText();
        if (Ma_HD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã Hoá Đơn Không Được Để Trống.");
            return;
        }

        String Ma_TK = txt_Ma_TK.getText();
        if (Ma_TK.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã Tài Khoản Không Được Để Trống.");
            return;
        }

        String Ma_KH = txt_Ma_KH.getText();
        if (Ma_KH.isEmpty()) {
            btn_Tao_HD.setSelected(false);
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn Đã Có Tài Khoản Chưa?\n"
                    + "➡ Xin vui lòng tạo tài khoản khách hàng mới.\n\n"
                    + "✅ Nếu bạn đã có tài khoản rồi,\n"
                    + "hãy ấn NO để tiếp tục.",
                    "Xác Nhận Tài Khoản Khách Hàng",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                QL_Tao_KH taoKH = new QL_Tao_KH();
                taoKH.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                taoKH.setLocationRelativeTo(this);
                taoKH.setAlwaysOnTop(true);
                taoKH.setVisible(true);
                taoKH.toFront();
            }
            return;
        }

        String HinhThuc_TT = rdo_TienMat.isSelected() ? "Tiền Mặt" : "Chuyển Khoản";
        String TrangThai_HD = "Chưa Thanh Toán";
        String Ma_KM = txt_KhuyenMai.getText();

        int So_Diem_Duoc_Cong = layGiaTriTuLabel(lb_SoDiemDuocCong); // ✔ lọc VND
        int Thanh_Tien = layGiaTriTuLabel(lb_ThanhTien);             // ✔ lọc VND

        float SoTien_KhachTra = 0f;
        try {
            String tienKhachTraStr = txt_SoTienKhachTra.getText().replaceAll("[^\\d.]", "");
            SoTien_KhachTra = tienKhachTraStr.isEmpty() ? 0f : Float.parseFloat(tienKhachTraStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền khách trả không hợp lệ.");
            return;
        }

        String input = txt_ThoiGianTao.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date utilDate = null;
        try {
            utilDate = sdf.parse(input);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Thời gian tạo hoá đơn không đúng định dạng.");
            Logger.getLogger(QL_BanHang_Panel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        HoaDon hd = new HoaDon(
                Ma_HD, Ma_TK, sqlDate, Thanh_Tien,
                HinhThuc_TT, TrangThai_HD, Ma_KM,
                SoTien_KhachTra, Ma_KH, So_Diem_Duoc_Cong
        );

        int ReSult = QL_Tao_HD.Tao_HD(hd);
        if (ReSult == 1) {
            FillToTable_HD();
            JOptionPane.showMessageDialog(this, "Tạo Hoá Đơn Thành Công.");
        } else {
            JOptionPane.showMessageDialog(this, "Tạo Hoá Đơn Thất Bại.");
        }
    }

    JPanel panelKM = new JPanel(new GridLayout(4, 1));
    JPopupMenu popupKM = new JPopupMenu();

    JLabel lblMa = new JLabel();
    JLabel lblTen = new JLabel();
    JLabel lblDieuKien = new JLabel();
    JLabel lblGiaTri = new JLabel();

    public void Nhap_KM() {
        // Tuỳ chỉnh panel

        panelKM.setBackground(Color.WHITE);
        panelKM.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        Stream.of(lblMa, lblTen, lblDieuKien, lblGiaTri).forEach(lbl -> {
            lbl.setForeground(new Color(60, 60, 60));
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panelKM.add(lbl);
        });

        panelKM.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panelKM.setBackground(new Color(230, 250, 255));
                panelKM.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelKM.setBackground(Color.WHITE);
                panelKM.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            }
        });

        popupKM.add(panelKM);
    }

    // Tìm Kiếm Theo Mã Khách Hàng
    public void Nhap_KH() {
        txt_Ma_KH.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String tuKhoa = txt_Ma_KH.getText().trim();
                List<KhachHang_4_O> dsKH = QL_KH.TimKiemTheo_KhachHang(tuKhoa);
                if (dsKH.isEmpty()) {
                    return;
                }

                String[] columnNames = {"Mã KH", "Tên KH", "SĐT", "Điểm TL"};
                Object[][] data = new Object[dsKH.size()][columnNames.length];

                for (int i = 0; i < dsKH.size(); i++) {
                    KhachHang_4_O kh = dsKH.get(i);
                    data[i][0] = kh.getMa_KH();
                    data[i][1] = kh.getTen_KH();
                    data[i][2] = kh.getSDT();
                    data[i][3] = kh.getDiemTichLuy();
                }

                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                JPopupMenu popup = new JPopupMenu();
                popup.add(scrollPane);
                popup.show(txt_Ma_KH, 0, txt_Ma_KH.getHeight());

                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow >= 0) {
                            String maKH = table.getValueAt(selectedRow, 0).toString();
                            KhachHang_4_O kh = QL_KH.timKHTheoMa(maKH);
                            txt_Ma_KH.setText(kh.getMa_KH());
                            txt_Ten_KH.setText(kh.getTen_KH());
                            lb_HienDiemTL.setText(String.valueOf(kh.getDiemTichLuy()));
                            popup.setVisible(false);
                        }
                    }
                });
            }
        });
    }

    // Khi Click Vào Dữ Liệu Trong Bảng Hoá Đơn Thì Phải Hiện Bảng San Phẩm Được Chọn
    public void loadChiTietHoaDon() {
        int row = tbl_Bang_HD.getSelectedRow();
        if (row < 0) {
            return; // Không có dòng nào được chọn
        }

        // 1. Lấy mã hoá đơn từ bảng hoá đơn
        String maHD = tbl_Bang_HD.getValueAt(row, 0).toString();

        // 2. Truy vấn trạng thái của hoá đơn từ DAO
        String trangThai = QL_Tao_HD.layTrangThaiTheoMaHD(maHD); // Trả về kiểu String

        // 3. Lấy danh sách sản phẩm đã chọn của hoá đơn
        List<ChiTiet_SP_6_O> dsSP = QL_Tao_CTHD.Get_ALL_SPDC(maHD);

        // 4. Xoá dữ liệu cũ trên bảng chi tiết sản phẩm
        TableModel_SP_DaChon = (DefaultTableModel) tbl_Bang_SP_DaChon.getModel();
        TableModel_SP_DaChon.setRowCount(0);

        // 5. Đổ dữ liệu mới và tính tổng tiền
        float tongTien = 0f;
        for (ChiTiet_SP_6_O sp : dsSP) {
            float donGia = sp.getDonGia();
            int soLuong = sp.getSoLuong();
            float thanhTien = donGia * soLuong;

            TableModel_SP_DaChon.addRow(new Object[]{
                sp.getMa_SP(),
                sp.getTen_SP(),
                donGia,
                soLuong,
                thanhTien,
                sp.getGhiChu()
            });

            tongTien += thanhTien;
        }

        // 6. Hiển thị tổng tiền lên giao diện
        lb_HienTongTien_ChuaGiamGia.setText(String.format("%.0f", tongTien));
        txt_SoTienKhachTra.setText(String.format("%.0f", tongTien));

        // 7. Nếu hoá đơn đã thanh toán thì khoá form
        lockHoaDonIfPaid(trangThai); // So sánh kiểu String bên trong
    }

    // Show Dtail Hoá Đơn 
    public void ShowDetail_HD() {
        Index = tbl_Bang_HD.getSelectedRow();
        if (Index >= 0) {
            String Ma_HD = tbl_Bang_HD.getValueAt(Index, 0).toString(); // Lấy mã HD từ bảng
            if (Ma_HD != null) {
                btn_Tao_HD.setSelected(true);
            }
            List<ShowDetail_BanHang> dsChiTiet = QL_Tao_HD.Get_SHOWCT_ByMaHD(Ma_HD);
            if (dsChiTiet.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn.");
                return;
            }

            ShowDetail_BanHang hd = dsChiTiet.get(0); // Vì truy vấn theo mã hóa đơn, chỉ có 1 dòng
            // Thời Gian Tạo Hoá Đơn
            txt_ThoiGianTao.setText(hd.getNgayLap_HD().toString());
            // Mã Tài Khoản
            txt_Ma_TK.setText(hd.getMa_TK());
            // Hình Thức Thanh Toán Hoá Đơn
            if (hd.getHinhThuc_HD().equalsIgnoreCase("Tiền Mặt")) {
                rdo_TienMat.setSelected(true);
            } else {
                rdo_ChuyenKhoan.setSelected(true);
            }
            // Mã Hoá Đơn
            txt_MaHD.setText(hd.getMa_HD());
            // Mã Khách Hàng
            txt_Ma_KH.setText(hd.getMa_KH());
            String Ma_KH = hd.getMa_KH();
            hienThiDiemTichLuy(Ma_KH);
            // Tên Khách Hàng
            txt_Ten_KH.setText(hd.getTen_KH());
            // Điểm Tích Luỹ
            lb_HienDiemTL.setText(String.valueOf((char) hd.getDiemTichLuy()));
            // Khuyến Mãi
            txt_KhuyenMai.setText(hd.getMa_KM());
            float GiaTri_KM = QL_KM.getGiaTriKhuyenMaiTheoMa(hd.getMa_KM());
            // Tổng Tiền
            float tongTien = hd.getTongTien();
            lb_HienTongTien_ChuaGiamGia.setText(tongTien + "  VND");
            // Tìn Giam Giá
            float tienGiam = GiaTri_KM; // Có thể tự tính từ mã khuyến mãi sau
            lb_Hien_TienGiamGia.setText(tienGiam + "  VND");
            // Số Điểm Được Cộng
            lb_SoDiemDuocCong.setText(hd.getTichDiem() + "  Điểm");
            // Thành Tiền
            float thanhTien = tongTien + tienGiam; // - Tien Giam
            lb_ThanhTien.setText(thanhTien + "  VND");
            // Số Tiền Khách Trả
            float tienKhachTra = hd.getSoTienKhachTra_HD();
            txt_SoTienKhachTra.setText(tienKhachTra + "  VND");
            // Số Tiền Cần Trả Lại
            float tienTraLai = tienKhachTra - thanhTien;
            txt_SoTien_CanTraLai.setText(tienTraLai + "  VND");

        } else {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Mã Hoá Đơn Để Show Chi Tiết.");
        }
    }

    private void hienThiDiemTichLuy(String Ma_KH) {
        if (Ma_KH == null || Ma_KH.trim().isEmpty()) {
            lb_HienDiemTL.setText("Không xác định");
            return;
        }

        int DiemTichLuy = QL_KH.layDiemTichLuy(Ma_KH); // gọi DAO đã viết
        lb_HienDiemTL.setText(DiemTichLuy + " Điểm"); // gán lên giao diện
    }

    // Gợi Ý Khuyến Mãi
    public void KM() {
        txt_KhuyenMai.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String tuKhoa = txt_KhuyenMai.getText().trim();
                List<KhuyenMai_4_O> dsKM = QL_KM.getKhuyenMaiTheoMa(tuKhoa);

                popupKM.removeAll(); // Xóa popup cũ

                if (dsKM.isEmpty()) {
                    return;
                }

                for (KhuyenMai_4_O km : dsKM) {
                    JMenuItem item = new JMenuItem(km.getMa_KM() + " - " + km.getTen_KM());
                    item.setFont(new Font("Segoe UI", Font.PLAIN, 13));

                    item.addActionListener(e1 -> {
                        txt_KhuyenMai.setText("" + km.getMa_KM());

                        // Gán nội dung ra panelKM
                        lblMa.setText("🔖 Mã KM: " + km.getMa_KM());
                        lblTen.setText("📢 Tên KM: " + km.getTen_KM());
                        lblDieuKien.setText("📋 Giá Trị Yêu Cầu: " + km.getGiaTri_YeuCau());
                        lblGiaTri.setText("🎯 Giá trị KM: " + km.getGiaTri_KM() + "%");

                        panelKM.setVisible(true);
                        panelKM.revalidate();
                        panelKM.repaint();

                        popupKM.setVisible(false); // Ẩn danh sách sau khi chọn
                        popupKM.show(txt_KhuyenMai, 0, txt_KhuyenMai.getHeight());
                    });

                    popupKM.add(item);
                }

                popupKM.show(txt_KhuyenMai, 0, txt_KhuyenMai.getHeight());
            }
        });
    }

    // Chọn Sản Phẩm Mới Vào Bẳng Hoá Đơn Chi Tiết
    private int timDongTonTai(String maSP) {
        for (int i = 0; i < TableModel_SP_DaChon.getRowCount(); i++) {
            String maSPTable = TableModel_SP_DaChon.getValueAt(i, 0).toString();
            if (maSPTable.equals(maSP)) {
                return i;
            }
        }
        return -1;
    }

    private void capNhatDong(DefaultTableModel model, int rowIndex, float donGia, int soLuongThem) {
        int soLuongCu = Integer.parseInt(model.getValueAt(rowIndex, 3).toString());
        int soLuongMoi = soLuongCu + soLuongThem;
        float thanhTienMoi = donGia * soLuongMoi;

        model.setValueAt(soLuongMoi, rowIndex, 3);
        model.setValueAt(thanhTienMoi, rowIndex, 4);
    }

    private int layDiemKhachHang(String diemStr) {
        if (diemStr == null || diemStr.trim().isEmpty() || diemStr.equalsIgnoreCase("null")) {
            return 0;
        }
        try {
            return Integer.parseInt(diemStr.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void capNhatBangSPDaChon(String maSP, String tenSP, float donGia, int soLuongThem) {
        DefaultTableModel model = (DefaultTableModel) tbl_Bang_SP_DaChon.getModel();
        int rowIndex = timDongTonTai(maSP);

        if (rowIndex != -1) {
            capNhatDong(model, rowIndex, donGia, soLuongThem);
        } else {
            float thanhTien = donGia * soLuongThem;
            model.addRow(new Object[]{maSP, tenSP, donGia, soLuongThem, thanhTien});
        }

        int diemKhachHang = layDiemKhachHang(lb_HienDiemTL.getText());
        TinhToan(diemKhachHang);
    }

    public void ChonSanPham() {
        int indexSP = tbl_Bang_SP.getSelectedRow();
        int indexHD = tbl_Bang_HD.getSelectedRow();

        if (indexHD < 0 || indexSP < 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn hóa đơn và sản phẩm!");
            return;
        }

        String maHD = tbl_Bang_HD.getValueAt(indexHD, 0).toString();
        String maSP = tbl_Bang_SP.getValueAt(indexSP, 0).toString();
        String tenSP = tbl_Bang_SP.getValueAt(indexSP, 1).toString();
        float donGia = Float.parseFloat(tbl_Bang_SP.getValueAt(indexSP, 3).toString());

        int soLuong;
        try {
            soLuong = (Integer) txt_SoLuong.getValue();

            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "❌ Số lượng phải lớn hơn 0!");
                return;
            }

            if (soLuong > 200) {
                JOptionPane.showMessageDialog(this, "❌ Giới hạn là 200 sản phẩm. Không thể chọn nhiều hơn!");
                return;
            }

            // Cảnh báo theo từng mốc
            if (soLuong == 25) {
                JOptionPane.showMessageDialog(this, "⚠️ Sản phẩm này đã vượt mức 25 sản phẩm.");
            } else if (soLuong == 75) {
                JOptionPane.showMessageDialog(this, "⚠️ Sản phẩm này đã vượt mức 50 sản phẩm.");
            } else if (soLuong == 100) {
                JOptionPane.showMessageDialog(this, "⚠️ Sản phẩm này đã vượt mức 75 sản phẩm.");
            } else if (soLuong == 100) {
                JOptionPane.showMessageDialog(this, "⚠️ Sản phẩm này đã vượt mức 100 sản phẩm.");
            }

            // Tiếp tục xử lý thêm nếu cần...
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Số lượng không hợp lệ!");
            return;
        }

        // 💡 Thay vì addRow trực tiếp → gọi hàm cập nhật bảng
        capNhatBangSPDaChon(maSP, tenSP, donGia, soLuong);

        // 📦 Đồng bộ vào CSDL: kiểm tra tồn tại rồi update hoặc insert
        ChiTietHoaDon cthd = new ChiTietHoaDon(maHD, maSP, soLuong, donGia, "");

        if (QL_Tao_CTHD.DaTonTaiCTHD(maHD, maSP)) {
            QL_Tao_CTHD.CapNhat_CTHD(cthd);
        } else {
            QL_Tao_CTHD.Them_CTHD(cthd);
        }

        JOptionPane.showMessageDialog(this, "✅ Đã thêm hoặc cập nhật sản phẩm trong hóa đơn!");
        txt_SoLuong.setValue(1);
    }

    // Show DeTail Sản Phẩm Đã Chọn
    public void ShowDetail_SP_DaChon() {
        int index = tbl_Bang_SP_DaChon.getSelectedRow();

        // ✅ Kiểm tra người dùng đã chọn dòng nào chưa
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm từ bảng!");
            return;
        }

        try {
            // 🧠 Lấy dữ liệu từ bảng
            String Ten_SP = tbl_Bang_SP_DaChon.getValueAt(index, 1).toString();
            float Don_Gia = Float.parseFloat(tbl_Bang_SP_DaChon.getValueAt(index, 2).toString());
            int SoLuong = Integer.parseInt(tbl_Bang_SP_DaChon.getValueAt(index, 3).toString());

            // 🎯 Hiển thị lên giao diện
//            lbl_TenSPDaChon.setText(tenSP);
//            txt_DonGia.setText(String.valueOf(donGia));
            // 📦 Hiển thị số lượng và giới hạn bằng Spinner (VD: từ 1 đến tồn kho hoặc 1000)
            int tonKho = 1000; // Nếu bạn có biến tồn kho, hãy thay bằng biến này
            SpinnerNumberModel model = new SpinnerNumberModel(SoLuong, 1, tonKho, 1);
            txt_SoLuong.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu sản phẩm: " + e.getMessage());
        }
    }

    public void TinhToan(int diemKhach) {
        int colDonGia = TableModel_SP_DaChon.findColumn("Đơn Giá");
        int colSoLuong = TableModel_SP_DaChon.findColumn("Số Lượng");

        float tongTien = 0f;
        int rowCount = TableModel_SP_DaChon.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            try {
                float donGia = Float.parseFloat(TableModel_SP_DaChon.getValueAt(i, colDonGia).toString());
                int soLuong = Integer.parseInt(TableModel_SP_DaChon.getValueAt(i, colSoLuong).toString());

                tongTien += donGia * soLuong;
            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi tính dòng " + i + ": " + e.getMessage());
            }
        }

        lb_HienTongTien_ChuaGiamGia.setText(String.format("%,.0f", tongTien));

        // 🎁 Tìm khuyến mãi phù hợp
        float giamGiaTienMat = 0f;
        List<KhuyenMai> danhSachKM = QL_KM.TimKiem_KhuyenMai_PhuHop(diemKhach, tongTien);
        if (!danhSachKM.isEmpty()) {
            KhuyenMai kmTotNhat = danhSachKM.get(0); // Đã được sắp từ cao xuống
            giamGiaTienMat = kmTotNhat.getGiaTri_KM();
        }

        lb_Hien_TienGiamGia.setText(String.format("%,.0f", giamGiaTienMat));
        float thanhTienSauGiam = tongTien - giamGiaTienMat;
        lb_ThanhTien.setText(String.format("%,.0f", thanhTienSauGiam));

        // 🏅 Tính điểm thưởng
        int diemCong = (int) (thanhTienSauGiam / 10000);
        lb_SoDiemDuocCong.setText(diemCong + " điểm");
    }

    // Giảm Giá Tốt Nhất
    public void hienThiKhuyenMaiPhuHop(int diemKhach, float tongTien) {
        List<KhuyenMai> danhSachKM = QL_KM.TimKiem_KhuyenMai_PhuHop(diemKhach, tongTien);

        if (danhSachKM.size() > 0) {
            KhuyenMai kmTotNhat = danhSachKM.get(0); // Vì đã sort từ cao xuống rồi

            // Hiển thị tên hoặc mã khuyến mãi nếu cần
//            lb_TenKM.setText(kmTotNhat.getMaKM());
            // Hiển thị giá trị được giảm
            lb_Hien_TienGiamGia.setText(String.format("- %.0f VNĐ", kmTotNhat.getGiaTri_KM()));

            // Có thể set giảm luôn vào tổng đơn nếu muốn áp dụng tự động
            float tongTienSauGiam = tongTien - kmTotNhat.getGiaTri_KM();
            lb_ThanhTien.setText(String.format("%.0f VNĐ", tongTienSauGiam));
        } else {
//            lb_TenKM.setText("Không có KM phù hợp");
            lb_Hien_TienGiamGia.setText("0 VNĐ");
            lb_ThanhTien.setText(String.format("%.0f VNĐ", tongTien));
        }
    }

    public void capNhatTongTien() {
        float tongTien = 0f;
        int rowCount = TableModel_SP_DaChon.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            Object cellValue = TableModel_SP_DaChon.getValueAt(i, 4);
            if (cellValue != null) {
                try {
                    float thanhTien = Float.parseFloat(cellValue.toString());
                    tongTien += thanhTien;
                } catch (NumberFormatException e) {
                    System.err.println("⚠️ Lỗi định dạng Thành Tiền tại dòng " + i + ": " + cellValue);
                }
            }
        }

        lb_ThanhTien.setText(String.format("%.0f VND", tongTien));
    }

    // Tự Động Cập Nhật Số Tiền Trả Lại Khách
    public void capNhatTienTraLai() {
        try {
            String traText = txt_SoTienKhachTra.getText().replaceAll("[^\\d.]", "");
            float khachTra = Float.parseFloat(traText);

            String thanhTienText = lb_ThanhTien.getText().replaceAll("[^\\d.]", "");
            float thanhTien = Float.parseFloat(thanhTienText);

            float traLai = khachTra - thanhTien;
            txt_SoTien_CanTraLai.setText(String.format("%.0f VND", traLai));
        } catch (Exception ex) {
            txt_SoTien_CanTraLai.setText("0 VND");
        }
    }

    public void suaSanPhamDaChon() {
        int selectedRow = tbl_Bang_SP_DaChon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng sản phẩm cần sửa!");
            return;
        }

        // 🔢 Lấy thông tin dòng đã chọn
        String maSP = TableModel_SP_DaChon.getValueAt(selectedRow, 0).toString();
        String maHD = Ma_HD_DuocChon; // Biến hóa đơn hiện tại (giả sử đã lưu ở đâu đó)
        int soLuongMoi = (Integer) txt_SoLuong.getValue();

        if (soLuongMoi <= 0) {
            JOptionPane.showMessageDialog(null, "❌ Số lượng phải lớn hơn 0!");
            return;
        }

        if (soLuongMoi > 200) {
            JOptionPane.showMessageDialog(null, "❌ Giới hạn là 200 sản phẩm. Không thể chọn nhiều hơn!");
            return;
        }

// Cảnh báo theo từng mốc
        if (soLuongMoi == 50) {
            JOptionPane.showMessageDialog(null, "⚠️ Sản phẩm này đã vượt mức 50.");
        } else if (soLuongMoi == 75) {
            JOptionPane.showMessageDialog(null, "⚠️ Sản phẩm này đã vượt mức 75.");
        } else if (soLuongMoi == 100) {
            JOptionPane.showMessageDialog(null, "⚠️ Sản phẩm này đã vượt mức 100.");
        }

// Có thể xử lý tiếp sau đoạn này...
        // 🧮 Lấy đơn giá từ bảng hiện tại
        float donGia = Float.parseFloat(TableModel_SP_DaChon.getValueAt(selectedRow, 2).toString());
        float thanhTienMoi = donGia * soLuongMoi;

        // ✅ Cập nhật lại trên bảng
        TableModel_SP_DaChon.setValueAt(soLuongMoi, selectedRow, 3);
        TableModel_SP_DaChon.setValueAt(thanhTienMoi, selectedRow, 4);

        // 📦 Đồng bộ vào CSDL
        boolean updateThanhCong = QL_Tao_CTHD.capNhatSoLuongSanPham(maHD, maSP, soLuongMoi);
        if (!updateThanhCong) {
            JOptionPane.showMessageDialog(null, "⚠️ Cập nhật xuống CSDL thất bại!");
        }

        // 🔁 Tính lại tổng tiền & khuyến mãi
        int diemKhach = layDiemKhachHang(lb_HienDiemTL.getText());
        TinhToan(diemKhach);

        JOptionPane.showMessageDialog(null, "✅ Đã cập nhật số lượng thành công!");
    }

    public Hoa_Don_Cho_In_HD getHoaDonDaTao() {
        Hoa_Don_Cho_In_HD hoaDon = new Hoa_Don_Cho_In_HD();

        // Mã hóa đơn
        hoaDon.setMa_HD(txt_MaHD.getText().trim());

        // Thời gian in
        hoaDon.setThoiGian_In_HD(LocalDateTime.now());

        // Địa chỉ cửa hàng
        hoaDon.setDiaChi("Số 13 Đường Trịnh Văn Bô, Hà Nội"); // hoặc lấy từ cấu hình

        // Logo
        hoaDon.setLinkAnhLogo("C:\\Users\\ADMIN\\OneDrive\\Documents\\Nghiep\\NetBean_25\\DuAn_TN\\src\\Icon\\Logo_Hoa_Don.png"); // đường dẫn ảnh logo
//        Image logo = new Image(ImageDataFactory.create(hoaDon.getLinkAnhLogo()));
//        logo.scaleToFit(120, 60); // co ảnh vừa với khung 120x60 pt
//        logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        document.add(logo);
        // Thông tin khách hàng
        KhachHang_3_O_In_HD kh = new KhachHang_3_O_In_HD();
        kh.setMa_KhachHang(txt_Ma_KH.getText().trim());
        kh.setTen_KhachHang(txt_Ten_KH.getText().trim());
        kh.setSo_DienThoai_KH(txt_Ma_KH.getText().trim());
        hoaDon.setKhachHang(kh);

        // Thông tin nhân viên
        // Thông tin nhân viên
        Tai_Khoan_3_O_In_HD tk = new Tai_Khoan_3_O_In_HD();
        String maTK = txt_Ma_TK.getText().trim();

        tk.setMa_TaiKhoan(maTK);
        tk.setTen_TaiKhoan(QL_KH.getTenTaiKhoan(maTK)); // gọi hàm lấy tên từ DB
        tk.setSoDienThoai_TaiKhoan(QL_KH.getSoDienThoaiTaiKhoan(maTK)); // gọi hàm lấy SDT từ DB

        hoaDon.setTaiKhoan(tk);

        // Danh sách sản phẩm
        List<ChiTiet_HoaDon_2_O_In_HD> chiTietList = new ArrayList<>();
        for (int i = 0; i < tbl_Bang_SP_DaChon.getRowCount(); i++) {
            ChiTiet_HoaDon_2_O_In_HD ct = new ChiTiet_HoaDon_2_O_In_HD();

            SanPham_3_O_In_HD sp = new SanPham_3_O_In_HD();
            sp.setTen_SP(tbl_Bang_SP_DaChon.getValueAt(i, 1).toString());
            sp.setDonGia(Float.parseFloat(tbl_Bang_SP_DaChon.getValueAt(i, 2).toString()));
            ct.setSanPham(sp);

            int soLuong = Integer.parseInt(tbl_Bang_SP_DaChon.getValueAt(i, 3).toString());
            ct.setSoLuong(soLuong);
            ct.setThanhTien(sp.getDonGia() * soLuong);

            chiTietList.add(ct);
        }
        hoaDon.setChiTietHoaDon(chiTietList);

        // Tổng kết
        int tongSoLuong = 0;
        for (ChiTiet_HoaDon_2_O_In_HD ct : chiTietList) {
            tongSoLuong += ct.getSoLuong();
        }
        hoaDon.setTongSoLuongSanPham(tongSoLuong);
//        hoaDon.setTongSoLuongSanPham(Integer.parseInt(txtTongSoLuong.getText().trim()));

// Làm sạch chuỗi trước khi chuyển đổi
        hoaDon.setTongTien(Float.parseFloat(lb_HienTongTien_ChuaGiamGia.getText().replaceAll("[^\\d.]", "").trim()));
        hoaDon.setGiamGia(Float.parseFloat(lb_Hien_TienGiamGia.getText().replaceAll("[^\\d.]", "").trim()));
        hoaDon.setThanhTienSauGiam(Float.parseFloat(lb_ThanhTien.getText().replaceAll("[^\\d.]", "").trim()));

        // Số điện thoại liên hệ
        hoaDon.setSoDienThoaiLienHe("0899076903"); // hoặc lấy từ cấu hình

        return hoaDon;
    }

    private void inHoaDonPDF() {
        // Kiểm tra xem đã có hóa đơn chưa
        if (getHoaDonDaTao() == null) {
            JOptionPane.showMessageDialog(null, "Chưa có hóa đơn để in.");
            return;
        }

        try {
            String filePath = "HoaDon_" + getHoaDonDaTao().getMa_HD() + ".pdf";
            HoaDon_PDF.HoaDon_PDF(getHoaDonDaTao(), filePath);
            JOptionPane.showMessageDialog(null, "Đã in hóa đơn ra file: " + filePath);

            // Mở file PDF sau khi in (tuỳ chọn)
            Desktop.getDesktop().open(new File(filePath));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in hóa đơn PDF.");
        }
    }

    public static String LayBankIdTuTen(String tenNganHang) {
        switch (tenNganHang.trim().toLowerCase()) {
            case "vietcombank":
                return "vietcombank";
            case "techcombank":
                return "techcombank";
            case "vietinbank":
                return "vietinbank";
            case "bidv":
                return "bidv";
            case "agribank":
                return "agribank";
            case "mb bank":
            case "mbbank":
                return "mbbank";
            case "acb":
                return "acb";
            case "sacombank":
                return "sacombank";
            case "tpbank":
                return "tpbank";
            case "vpbank":
                return "vpbank";
            default:
                return "vietcombank"; // mặc định nếu không khớp
        }
    }

    public static String taoLinkVietQR(String bankId, String stk, String tenTk, double soTien, String noiDung) throws UnsupportedEncodingException {
        String baseUrl = "https://img.vietqr.io/image/";
        String fullUrl = baseUrl
                + bankId.toLowerCase() + "-" + stk + "-compact2.png"
                + "?amount=" + (int) soTien
                + "&addInfo=" + URLEncoder.encode(noiDung, "UTF-8")
                + "&accountName=" + URLEncoder.encode(tenTk, "UTF-8");
        return fullUrl;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btg_HinhThuc_TT = new javax.swing.ButtonGroup();
        btg_TrangThai = new javax.swing.ButtonGroup();
        ThongTin_HD = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_Ma_KH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Ten_KH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lb_HienDiemTL = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_KhuyenMai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lb_HienTongTien_ChuaGiamGia = new javax.swing.JLabel();
        lb_Hien_TienGiamGia = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lb_SoDiemDuocCong = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lb_ThanhTien = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_SoTienKhachTra = new javax.swing.JTextField();
        txt_SoTien_CanTraLai = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_Tao_HD = new javax.swing.JToggleButton();
        tbtn_HuyTao = new javax.swing.JToggleButton();
        btn__ThanhToan = new javax.swing.JButton();
        btn_ReSet = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_MaHD = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Bang_SP = new javax.swing.JTable();
        txt_ThoiGianTao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Bang_HD = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_Bang_SP_DaChon = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txt_SoLuong = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        btn_Chon_SP = new javax.swing.JButton();
        btn_HuyChon_SP = new javax.swing.JButton();
        btn_Sua = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txt_Ma_TK = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        rdo_TienMat = new javax.swing.JRadioButton();
        rdo_ChuyenKhoan = new javax.swing.JRadioButton();

        ThongTin_HD.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nhập Thông Tin Hoá Đơn"));

        jLabel2.setText("Mã KH:");

        txt_Ma_KH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_Ma_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Ma_KHActionPerformed(evt);
            }
        });

        jLabel3.setText("Tên KH:");

        txt_Ten_KH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Số Điểm Tích Luỹ:");

        lb_HienDiemTL.setText("Null");

        jLabel6.setText("Khuyến Mãi:");

        txt_KhuyenMai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Tổng Cộng :");

        lb_HienTongTien_ChuaGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        lb_Hien_TienGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel10.setText("Giảm Giá :");

        jLabel11.setText("Điểm Cộng:");

        lb_SoDiemDuocCong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel13.setText("Thành Tiền :");

        lb_ThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel15.setText("Khách Trả :");

        txt_SoTienKhachTra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_SoTien_CanTraLai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_SoTien_CanTraLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SoTien_CanTraLaiActionPerformed(evt);
            }
        });

        jLabel16.setText("Tiền Trả Lại :");

        btn_Tao_HD.setBackground(new java.awt.Color(204, 255, 204));
        btn_Tao_HD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Tao_HD.setText("Tạo Hoá Đơn");
        btn_Tao_HD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tao_HDActionPerformed(evt);
            }
        });

        tbtn_HuyTao.setBackground(new java.awt.Color(255, 204, 204));
        tbtn_HuyTao.setText("Huỷ Tạo");
        tbtn_HuyTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtn_HuyTaoActionPerformed(evt);
            }
        });

        btn__ThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn__ThanhToan.setText("Thanh Toán");
        btn__ThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn__ThanhToanActionPerformed(evt);
            }
        });

        btn_ReSet.setText("Reset");
        btn_ReSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReSetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ThongTin_HDLayout = new javax.swing.GroupLayout(ThongTin_HD);
        ThongTin_HD.setLayout(ThongTin_HDLayout);
        ThongTin_HDLayout.setHorizontalGroup(
            ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongTin_HDLayout.createSequentialGroup()
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThongTin_HDLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ThongTin_HDLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_KhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ThongTin_HDLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lb_HienDiemTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(ThongTin_HDLayout.createSequentialGroup()
                                    .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_Ten_KH, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                        .addComponent(txt_Ma_KH))))
                            .addGroup(ThongTin_HDLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(lb_Hien_TienGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ThongTin_HDLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(lb_HienTongTien_ChuaGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btn_ReSet)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ThongTin_HDLayout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addComponent(btn_Tao_HD, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tbtn_HuyTao, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ThongTin_HDLayout.createSequentialGroup()
                                    .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_SoTienKhachTra)
                                        .addComponent(txt_SoTien_CanTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_ThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lb_SoDiemDuocCong, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(ThongTin_HDLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(btn__ThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        ThongTin_HDLayout.setVerticalGroup(
            ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongTin_HDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Ma_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Ten_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_HienDiemTL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_KhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_HienTongTien_ChuaGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_Hien_TienGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_SoDiemDuocCong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_SoTienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_SoTien_CanTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThongTin_HDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Tao_HD, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(tbtn_HuyTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn__ThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_ReSet, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Mã HD:");

        txt_MaHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Thời Gian Tạo:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Sản Phẩm"));

        tbl_Bang_SP.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_Bang_SP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        txt_ThoiGianTao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ThoiGianTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ThoiGianTaoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Hoá đơn"));

        tbl_Bang_HD.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_Bang_HD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Bang_HDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Bang_HD);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng Danh Sách Sản Phẩm Được Chọn"));

        tbl_Bang_SP_DaChon.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_Bang_SP_DaChon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Bang_SP_DaChonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_Bang_SP_DaChon);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng Sản Phẩm"));

        txt_SoLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Số Lượng SP Cần Dùng :");

        btn_Chon_SP.setText("Chọn SP");
        btn_Chon_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Chon_SPActionPerformed(evt);
            }
        });

        btn_HuyChon_SP.setText("Huỷ Chọn SP");
        btn_HuyChon_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HuyChon_SPActionPerformed(evt);
            }
        });

        btn_Sua.setText("Sửa ");
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Chon_SP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_SoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addComponent(btn_HuyChon_SP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_SoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Chon_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_HuyChon_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel9.setText("Mã Nhân Viên :");

        txt_Ma_TK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_Ma_TK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Ma_TKActionPerformed(evt);
            }
        });

        jLabel12.setText("Hình Thức TT:");

        btg_HinhThuc_TT.add(rdo_TienMat);
        rdo_TienMat.setText("Tiền Mặt");

        btg_HinhThuc_TT.add(rdo_ChuyenKhoan);
        rdo_ChuyenKhoan.setText("Chuyển Khoản");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(ThongTin_HD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_ThoiGianTao, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Ma_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(rdo_TienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(rdo_ChuyenKhoan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_MaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_MaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdo_TienMat)
                        .addComponent(rdo_ChuyenKhoan))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_ThoiGianTao, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txt_Ma_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(ThongTin_HD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_Ma_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Ma_KHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Ma_KHActionPerformed

    private void txt_SoTien_CanTraLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SoTien_CanTraLaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SoTien_CanTraLaiActionPerformed

    private void btn_ReSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReSetActionPerformed
        // TODO add your handling code here:
        Reset();
        FillToTable_HD();
        FillToTable_SP();
        FillToTable_SP_DUOCCHON();
        TableModel_SP_DaChon.setRowCount(0);
    }//GEN-LAST:event_btn_ReSetActionPerformed

    private void btn_Tao_HDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tao_HDActionPerformed
        // TODO add your handling code here:
        String Ma_TaiKhoan = Chua_Bien.Ma_TK;
        if (Ma_TaiKhoan == null) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Đăng Nhập Để Bán Hàng.");
            return;
        } else {
            Tao_HD();
            FillToTable_HD();
        }
    }//GEN-LAST:event_btn_Tao_HDActionPerformed

    private void tbl_Bang_HDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Bang_HDMouseClicked
        // TODO add your handling code here:
        ShowDetail_HD();
        int selectedRow = tbl_Bang_HD.getSelectedRow();
//        Ma_HD_DuocChon = tbl_Bang_HD.getValueAt(selectedRow, 0).toString(); // Lấy mã hóa đơn
//        int selectedRow = tbl_HoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            daChonHoaDon = true;
//            JOptionPane.showMessageDialog(null, "✅ Bạn đã chọn hóa đơn, có thể thêm sản phẩm.");
        }

        loadChiTietHoaDon();
    }//GEN-LAST:event_tbl_Bang_HDMouseClicked


    private void btn_Chon_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Chon_SPActionPerformed
        // TODO add your handling code here:
        ChonSanPham();
    }//GEN-LAST:event_btn_Chon_SPActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
        // TODO add your handling code here:
        suaSanPhamDaChon();
    }//GEN-LAST:event_btn_SuaActionPerformed

    private void tbl_Bang_SP_DaChonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Bang_SP_DaChonMouseClicked
        // TODO add your handling code here:
        ShowDetail_SP_DaChon();
    }//GEN-LAST:event_tbl_Bang_SP_DaChonMouseClicked

    private void btn__ThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn__ThanhToanActionPerformed
        // TODO add your handling code here:
        // 👉 Mã hóa đơn và khách hàng

        String Ma_HD = txt_MaHD.getText();
        String Ma_KH = txt_Ma_KH.getText();

// 👉 Tách số tiền từ Label "120000 VND"
        String thanhTienText = lb_ThanhTien.getText();
        String thanhTienClean = thanhTienText.replaceAll("[^\\d.]", "");
        float ThanhTienGoc = Float.parseFloat(thanhTienClean);

// 👉 Tách tiền khách trả "130000 VND"
//        String khachTraText = txt_SoTienKhachTra.getText();
//        String khachTraClean = khachTraText.replaceAll("[^\\d.]", "");
//        float SoTien_KhachTra = Float.parseFloat(khachTraClean);
        // 👉 Lấy và kiểm tra số tiền khách trả
        String khachTraText = txt_SoTienKhachTra.getText().trim();

        if (khachTraText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Vui lòng nhập số tiền khách trả.");
            return;
        }

        String khachTraClean = khachTraText.replaceAll("[^\\d.]", "");
        float soTienKhachTra;

        try {
            soTienKhachTra = Float.parseFloat(khachTraClean);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Số tiền khách trả không hợp lệ.");
            return;
        }

        if (soTienKhachTra <= 0) {
            JOptionPane.showMessageDialog(null, "❌ Số tiền khách trả phải lớn hơn 0.");
            return;
        }

// 👉 Lấy và kiểm tra số tiền cần thanh toán
        String thanhTienRaw = lb_ThanhTien.getText().replaceAll("[^\\d.]", "");
        float soTienCanThanhToan;

        try {
            soTienCanThanhToan = Float.parseFloat(thanhTienRaw);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Số tiền cần thanh toán không hợp lệ.");
            return;
        }

        if (soTienKhachTra < soTienCanThanhToan) {
            JOptionPane.showMessageDialog(null, "❌ Số tiền khách trả không đủ để thanh toán.");
            return;
        }

// ✅ Nếu qua hết kiểm tra thì xử lý thanh toán
// Gọi hàm xử lý thanh toán ở đây...
// ✅ Nếu qua hết kiểm tra thì xử lý thanh toán
// 👉 Mã khuyến mãi
        String Ma_KM = txt_KhuyenMai.getText();

// ✅ Lấy giá trị khuyến mãi
        QL_KhuyenMai QLKM = new QL_KhuyenMai(); // Tạo instance
        float giaTriKM = QLKM.LayGiaTri_KhuyenMai(Ma_KM); // ✅ gọi qua object
//        float giaTriKM = QL_KhuyenMai.layGiaTriKhuyenMai(Ma_KM);
//lb_Hien_TienGiamGia.setText(String.valueOf(giaTriKM) + "VND");
// 👉 Tính thành tiền sau khuyến mãi
        float ThanhTienSauKM = ThanhTienGoc - giaTriKM;
//        lb_ThanhTien.setText(String.valueOf(ThanhTienSauKM) + "VND");
// 👉 Tính tiền trả lại
        float SoTien_CanTra_Lai = soTienKhachTra - ThanhTienSauKM;

// 👉 Tách số điểm cộng từ "18 điểm"
        String diemText = lb_SoDiemDuocCong.getText();
        String diemClean = diemText.replaceAll("[^\\d]", "");
        int SoDiem_DuocCong = Integer.parseInt(diemClean);

// ✅ Gọi DAO xử lý thanh toán
        boolean ketQua = QL_Tao_HD.thanhToanHoaDon(
                Ma_HD, Ma_KH,
                ThanhTienSauKM,
                soTienKhachTra,
                SoTien_CanTra_Lai,
                Ma_KM,
                SoDiem_DuocCong
        );

//        if (ketQua) {
//            JOptionPane.showMessageDialog(this, "🎉 Thanh toán thành công!");
//
//            String noiDungThanhToan = "Thanh toan hoa don " + Ma_HD;
//
//            // ✅ Chỉ tạo QR nếu chọn Chuyển Khoản
//            if (rdo_ChuyenKhoan.isSelected()) {
//                try {
//                    BufferedImage qrImage = Tao_Ma_QR_Core.generateQRImage(
//                            ThanhTienSauKM,
//                            noiDungThanhToan
//                    );
//
//                    JFrame qrFrame = new JFrame("Mã QR Thanh Toán");
//                    qrFrame.setSize(300, 300);
//                    qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                    qrFrame.setLocationRelativeTo(null);
//
//                    JLabel qrLabel = new JLabel(new ImageIcon(qrImage));
//                    qrFrame.add(qrLabel);
//                    qrFrame.setVisible(true);
//
//                    JOptionPane.showMessageDialog(this, "✅ Mã QR thanh toán đã được tạo!");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    JOptionPane.showMessageDialog(this, "❌ Lỗi tạo QR: " + e.getMessage());
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "💵 Thanh toán tiền mặt - Không cần tạo mã QR.");
//            }
//
//            Reset();
//        } else {
//            JOptionPane.showMessageDialog(this, "❌ Thanh toán thất bại. Vui lòng thử lại.");
//        }
        if (ketQua) {
            JOptionPane.showMessageDialog(this, "🎉 Thanh toán thành công!");

            String noiDungThanhToan = null;
//            float SoTien = ThanhTienSauKM;
            // ✅ Chỉ tạo QR nếu chọn Chuyển Khoản
//            if (rdo_ChuyenKhoan.isSelected()) {
//                try {
//                    // 👉 Gọi hàm chuẩn VietQR EMV
//                    BufferedImage qrImage = Tao_Ma_QR_Core_01.generateVietQR(
//                            ThanhTienSauKM, // 💰 số tiền
//                            noiDungThanhToan // 📝 nội dung chuyển khoản
//                    );
//
//                    JFrame qrFrame = new JFrame("Mã QR Thanh Toán");
//                    qrFrame.setSize(300, 300);
//                    qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                    qrFrame.setLocationRelativeTo(null);
//
//                    JLabel qrLabel = new JLabel(new ImageIcon(qrImage));
//                    qrFrame.add(qrLabel);
//                    qrFrame.setVisible(true);
//
//                    JOptionPane.showMessageDialog(this, "✅ Mã QR thanh toán đã được tạo!");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    JOptionPane.showMessageDialog(this, "❌ Lỗi tạo QR: " + e.getMessage());
//                }
//            } 
            if (rdo_ChuyenKhoan.isSelected()) {
                try {
                    noiDungThanhToan = "Thanh toan hoa don " + Ma_HD;
                    double soTien = ThanhTienSauKM;

                    // 👉 Lấy danh sách tài khoản ngân hàng đang hoạt động
                    QL_NganHang QL_NH = new QL_NganHang();
                    List<NganHang> danhSachNH = QL_NH.layDanhSachNganHangHoatDong();

                    if (danhSachNH.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "❌ Không có tài khoản ngân hàng hoạt động.");
                        return;
                    }

                    // 👉 Chọn tài khoản đầu tiên (hoặc anh có thể chọn theo logic riêng)
                    NganHang selectedNH = danhSachNH.get(0);

                    String bankId = LayBankIdTuTen(selectedNH.getTen_NganHang());
                    String stk = selectedNH.getSo_TaiKhoan();
                    String tenChuTK = selectedNH.getTen_Chu_TK();

                    // 👉 Tạo link VietQR
                    String linkQR = taoLinkVietQR(bankId, stk, tenChuTK, soTien, noiDungThanhToan);

                    // 👉 Mở trình duyệt
                    Desktop.getDesktop().browse(new URI(linkQR));

                    JOptionPane.showMessageDialog(this, "✅ Link QR thanh toán đã được mở!");

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "❌ Lỗi tạo QR: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "💵 Thanh toán tiền mặt - Không cần tạo mã QR.");
            }
            Reset();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thanh toán thất bại. Vui lòng thử lại.");
        }


    }//GEN-LAST:event_btn__ThanhToanActionPerformed

    private void btn_HuyChon_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HuyChon_SPActionPerformed
        // TODO add your handling code here:

        int Hang_SP_DaChon = tbl_Bang_SP_DaChon.getSelectedRow();
        if (Hang_SP_DaChon != -1) {
            String maSP = tbl_Bang_SP_DaChon.getValueAt(Hang_SP_DaChon, 0).toString();
            String maHD = txt_MaHD.getText();

            int confirm = JOptionPane.showConfirmDialog(null, "Huỷ sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // 🔥 Xoá trong DB
                QL_Tao_CTHD.xoaChiTietHoaDon(maHD, maSP);

                // 🔥 Xoá dòng trên bảng
                TableModel_SP_DaChon = (DefaultTableModel) tbl_Bang_SP_DaChon.getModel();
                TableModel_SP_DaChon.removeRow(Hang_SP_DaChon);

                // 🔁 Cập nhật tổng tiền và tiền trả lại
                capNhatTongTien();
                capNhatTienTraLai();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chọn sản phẩm trước khi huỷ nha!");
        }


    }//GEN-LAST:event_btn_HuyChon_SPActionPerformed

    private void txt_Ma_TKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Ma_TKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Ma_TKActionPerformed

    private void tbtn_HuyTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtn_HuyTaoActionPerformed
        // TODO add your handling code here:
        int selectedRow = tbl_Bang_HD.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "⚠️ Vui lòng chọn hóa đơn cần xóa.");
            return;
        }

        String maHD = tbl_Bang_HD.getValueAt(selectedRow, 0).toString(); // Giả sử cột 0 là MA_HD
        String trangThai = tbl_Bang_HD.getValueAt(selectedRow, 4).toString(); // Giả sử cột 4 là TRANGTHAI

        if (!trangThai.equalsIgnoreCase("Chưa Thanh Toán")) {
            JOptionPane.showMessageDialog(null, "❌ Chỉ được xóa hóa đơn chưa thanh toán.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc muốn xóa hóa đơn " + maHD + " không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int result = QL_Tao_HD.xoaHoaDonVaThanhToan(maHD);
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "✅ Xóa hóa đơn thành công.");
                FillToTable_HD();
                FillToTable_SP_DUOCCHON();// Hàm reload bảng
            } else {
                JOptionPane.showMessageDialog(null, "❌ Xóa hóa đơn thất bại.");
            }
        }
    }//GEN-LAST:event_tbtn_HuyTaoActionPerformed

    private void txt_ThoiGianTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ThoiGianTaoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_ThoiGianTaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ThongTin_HD;
    private javax.swing.ButtonGroup btg_HinhThuc_TT;
    private javax.swing.ButtonGroup btg_TrangThai;
    private javax.swing.JButton btn_Chon_SP;
    private javax.swing.JButton btn_HuyChon_SP;
    private javax.swing.JButton btn_ReSet;
    private javax.swing.JButton btn_Sua;
    private javax.swing.JToggleButton btn_Tao_HD;
    private javax.swing.JButton btn__ThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lb_HienDiemTL;
    private javax.swing.JLabel lb_HienTongTien_ChuaGiamGia;
    private javax.swing.JLabel lb_Hien_TienGiamGia;
    private javax.swing.JLabel lb_SoDiemDuocCong;
    private javax.swing.JLabel lb_ThanhTien;
    private javax.swing.JRadioButton rdo_ChuyenKhoan;
    private javax.swing.JRadioButton rdo_TienMat;
    private javax.swing.JTable tbl_Bang_HD;
    private javax.swing.JTable tbl_Bang_SP;
    private javax.swing.JTable tbl_Bang_SP_DaChon;
    private javax.swing.JToggleButton tbtn_HuyTao;
    private javax.swing.JTextField txt_KhuyenMai;
    private javax.swing.JTextField txt_MaHD;
    private javax.swing.JTextField txt_Ma_KH;
    private javax.swing.JTextField txt_Ma_TK;
    private javax.swing.JSpinner txt_SoLuong;
    private javax.swing.JTextField txt_SoTienKhachTra;
    private javax.swing.JTextField txt_SoTien_CanTraLai;
    private javax.swing.JTextField txt_Ten_KH;
    private javax.swing.JTextField txt_ThoiGianTao;
    // End of variables declaration//GEN-END:variables
}
