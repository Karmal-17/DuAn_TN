/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Luu_Anh;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.*;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public class Test {

    public static void main(String[] args) {
        try {
            String dest = "hoadon_demo_tv.pdf";

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Load font hỗ trợ tiếng Việt
            String fontPath = "C:/Windows/Fonts/arial.ttf";
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);

            // Áp dụng font mặc định cho document
            document.setFont(font);

            document.add(new Paragraph("HÓA ĐƠN BÁN HÀNG")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Cửa hàng ABC - 123 Đường XYZ, TP.HCM")
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Ngày: 11/08/2025\n\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell("Tên sản phẩm");
            table.addHeaderCell("SL");
            table.addHeaderCell("Đơn giá");
            table.addHeaderCell("Thành tiền");

            table.addCell("Sản phẩm A");
            table.addCell("2");
            table.addCell("50,000");
            table.addCell("100,000");

            table.addCell("Sản phẩm B");
            table.addCell("1");
            table.addCell("150,000");
            table.addCell("150,000");

            table.addCell(new Cell(1, 3).add(new Paragraph("Tổng cộng").setBold()));
            table.addCell("250,000");

            document.add(table);

            document.add(new Paragraph("\nCảm ơn quý khách và hẹn gặp lại!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

            document.close();

            System.out.println("✅ PDF tiếng Việt đã tạo thành công: " + dest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
