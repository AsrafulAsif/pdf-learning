package com.aia.pdf_learning;

import com.aia.pdf_learning.service.PdfService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
class PdfLearningApplicationTests {

	@Autowired
	PdfService pdfService;

	@Test
	void contextLoads() {
	}
}
