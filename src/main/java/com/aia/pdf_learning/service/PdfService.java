package com.aia.pdf_learning.service;

import com.aia.pdf_learning.model.request.PdfContent;
import com.aia.pdf_learning.model.request.Product;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Slf4j
public class PdfService {

    public byte[] makeInvoicePdf(PdfContent pdfContent) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add Invoice Title
            Paragraph title = new Paragraph("Invoice",
                    new Font(Font.TIMES_ROMAN, 26, Font.BOLD , Color.DARK_GRAY));
            title.setAlignment(Element.ALIGN_CENTER);
//            title.setSpacingAfter(20F);
            document.add(title);

            // Add Date
            Paragraph date = new Paragraph("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            // Add Order ID
            Paragraph orderId = new Paragraph("Order ID: " + pdfContent.orderId(),
                    new Font(Font.TIMES_ROMAN, 14, Font.NORMAL, Color.BLACK));
            orderId.setAlignment(Element.ALIGN_CENTER);
            document.add(orderId);

            // Add Customer Details
            Paragraph customerDetails = new Paragraph();
            customerDetails.add(new Chunk("Customer Details: \n",
                    new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK)));
            customerDetails.add(new Chunk("Name: " + pdfContent.customerName() + "\n",
                    new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK)));
            customerDetails.add(new Chunk("Phone: " + pdfContent.customerPhoneNumber() + "\n",
                    new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK)));
            customerDetails.add(new Chunk("Address: " + pdfContent.customerAddress(),
                    new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK)));
            customerDetails.setSpacingAfter(20F);
            document.add(customerDetails);

            // Add Product Table Header
            Paragraph productHeader = new Paragraph("Product Details:",
                    new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK));
            document.add(productHeader);

            // Create Table
            PdfPTable table = new PdfPTable(4); // Four columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Set column widths
            table.setWidths(new float[]{3f, 1f, 2f, 2f});

            // Add Table Header
            table.addCell(createHeaderCell("Product Name"));
            table.addCell(createHeaderCell("Quantity"));
            table.addCell(createHeaderCell("Price"));
            table.addCell(createHeaderCell("Total"));

            // Add Product Rows
            float totalAmount = 0.0F;
            for (Product product : pdfContent.products()) {
                table.addCell(createCell(product.name()));
                table.addCell(createCell(String.valueOf(product.quantity())));
                table.addCell(createCell("$" + product.price()));
                float productTotal = Integer.parseInt(product.quantity()) * Float.parseFloat(product.price());
                table.addCell(createCell("$" + productTotal));
                totalAmount += productTotal;
            }

            // Add Table to Document
            document.add(table);

            // Add Total Amount
            Paragraph total = new Paragraph("Total Amount: $" + totalAmount,
                    new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK));
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);


            Paragraph signatureLabel = new Paragraph("Signature",
                    new Font(Font.TIMES_ROMAN, 12, Font.ITALIC | Font.UNDERLINE, Color.BLACK));
            signatureLabel.setAlignment(Element.ALIGN_RIGHT);
            signatureLabel.setSpacingBefore(50F);
            signatureLabel.setSpacingAfter(20F);
            signatureLabel.setIndentationRight(50F);
            document.add(signatureLabel);

            // Add Footer
            Paragraph footer = new Paragraph("Thank you for your purchase!",
                    new Font(Font.TIMES_ROMAN, 12, Font.ITALIC, Color.DARK_GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Close the Document
            document.close();

        } catch (DocumentException e) {
            log.error("Error while creating PDF: {}", e.getMessage(), e);
        }

        return outputStream.toByteArray();
    }

    // Helper Method to Create Table Header Cells
    private PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.WHITE)));
        cell.setBackgroundColor(Color.GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    // Helper Method to Create Regular Table Cells
    private PdfPCell createCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

}
