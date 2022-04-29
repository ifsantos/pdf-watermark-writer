package com.github.ifsantos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PdfGeneratorTests {
	@Autowired
	PDFGenerator p;
	
	@Test
	void contextLoads() {
		Long timestamp = System.currentTimeMillis();
		String expected = "C:\\code_home\\mock\\pdf-gen\\Agenor_de_Miranda_Araújo_Neto"+timestamp+".pdf";
		
		String generatePDF = p.generatePDF("C:\\code_home\\mock\\pdf-gen", "Agenor de Miranda Araújo Neto", "999.888.777-45",timestamp );
		
		assertThat(generatePDF).isEqualTo(expected);
	}

}
