package ToanBo_SanPham;

import ToanBo_BanHang.ChiTiet_HoaDon_2_O_In_HD;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ToanBo_BanHang.Hoa_Don_Cho_In_HD;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;

public class HoaDon_PDF {

    public static void HoaDon_PDF(Hoa_Don_Cho_In_HD hoaDon, String filePath) throws Exception {
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Font setup
//        PdfFont fontNormal = PdfFontFactory.createFont("Arial", PdfEncodings.IDENTITY_H, false);
//        PdfFont fontBold = PdfFontFactory.createFont("Arial-Bold", PdfEncodings.IDENTITY_H, false);
        PdfFont fontNormal = PdfFontFactory.createFont("src/fonts/arial.ttf", PdfEncodings.IDENTITY_H, true);
        PdfFont fontBold = PdfFontFactory.createFont("src/fonts/arialbd.ttf", PdfEncodings.IDENTITY_H, true);
        // Logo
        if (hoaDon.getLinkAnhLogo() != null && !hoaDon.getLinkAnhLogo().isEmpty()) {
            Image logo = new Image(ImageDataFactory.create(hoaDon.getLinkAnhLogo()));
            logo.setWidth(100);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
        }

        // Tiêu đề
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG")
                .setFont(fontBold)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        // Thông tin hóa đơn
        document.add(new Paragraph("Mã hóa đơn: " + hoaDon.getMa_HD()).setFont(fontNormal));
        document.add(new Paragraph("Thời gian in: "
                + hoaDon.getThoiGianIn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setFont(fontNormal));
        document.add(new Paragraph("Địa chỉ: " + hoaDon.getDiaChi()).setFont(fontNormal));
        document.add(new Paragraph(" "));

        // Khách hàng
        document.add(new Paragraph("Khách hàng: " + hoaDon.getKhachHang().getTen_KhachHang()).setFont(fontNormal));
        document.add(new Paragraph("SĐT khách: " + hoaDon.getKhachHang().getSo_DienThoai_KH()).setFont(fontNormal));
        document.add(new Paragraph(" "));

        // Nhân viên
        document.add(new Paragraph("Nhân viên: " + hoaDon.getTaiKhoan().getTen_TaiKhoan()).setFont(fontNormal));
        document.add(new Paragraph("SĐT nhân viên: " + hoaDon.getTaiKhoan().getSoDienThoai_TaiKhoan()).setFont(fontNormal));
        document.add(new Paragraph(" "));

        // Bảng sản phẩm
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(new Cell().add(new Paragraph("STT").setFont(fontBold)));
        table.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setFont(fontBold)));
        table.addHeaderCell(new Cell().add(new Paragraph("Đơn giá").setFont(fontBold)));
        table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setFont(fontBold)));
        table.addHeaderCell(new Cell().add(new Paragraph("Thành tiền").setFont(fontBold)));

        List<ChiTiet_HoaDon_2_O_In_HD> chiTietList = hoaDon.getChiTietHoaDon();
        for (int i = 0; i < chiTietList.size(); i++) {
            ChiTiet_HoaDon_2_O_In_HD ct = chiTietList.get(i);
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1)).setFont(fontNormal)));
            table.addCell(new Cell().add(new Paragraph(ct.getSanPham().getTen_SP()).setFont(fontNormal)));
            table.addCell(new Cell().add(new Paragraph(String.format("%.0f", ct.getSanPham().getDonGia())).setFont(fontNormal)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(ct.getSoLuong())).setFont(fontNormal)));
            table.addCell(new Cell().add(new Paragraph(String.format("%.0f", ct.getThanhTien())).setFont(fontNormal)));
        }

        document.add(table);
        document.add(new Paragraph(" "));

        // Tổng kết
        document.add(new Paragraph("Tổng số lượng sản phẩm: " + hoaDon.getTongSoLuongSanPham()).setFont(fontBold));
        document.add(new Paragraph("Tổng tiền: " + String.format("%.0f", hoaDon.getTongTien())).setFont(fontBold));
        document.add(new Paragraph("Giảm giá: " + String.format("%.0f", hoaDon.getGiamGia())).setFont(fontBold));
        document.add(new Paragraph("Thành tiền sau cùng: " + String.format("%.0f", hoaDon.getThanhTienSauGiam())).setFont(fontBold));
        document.add(new Paragraph(" "));

        // Lời cảm ơn
        document.add(new Paragraph("Fake AL Fresco’s xin cảm ơn quý khách và hẹn gặp lại.")
                .setFont(fontNormal)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Có vấn đề gì xin vui lòng liên hệ số điện thoại: "
                + hoaDon.getSoDienThoaiLienHe()).setFont(fontNormal)
                .setTextAlignment(TextAlignment.CENTER));

        document.close();
    }
}
