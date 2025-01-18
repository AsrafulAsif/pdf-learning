package com.aia.pdf_learning.controller;

import com.aia.pdf_learning.model.request.PdfContent;
import com.aia.pdf_learning.service.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @PostMapping("/invoice-pdf")
    public ResponseEntity<?> generatePdf(@Valid @RequestBody PdfContent pdfContent){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf");
        return ResponseEntity.ok().headers(headers).body((pdfService.makeInvoicePdf(pdfContent)));
    }

}
