package com.github.ifsantos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.ifsantos.io.IOHandler;
import com.itextpdf.text.Document;

@SpringBootTest
class PdfGeneratorTests {
	@InjectMocks
	PDFGenerator p;
	@Mock
	IOHandler h;
	private String inputDir = "C:\\code_home\\mock\\pdf-gen";
	Long timestamp = System.currentTimeMillis();
	String expected = "C:\\code_home\\mock\\pdf-gen\\output\\Agenor_de_Miranda_Araújo_Neto"+timestamp+".pdf";
	
	@BeforeEach
	void setUp() throws Exception {
        when(h.pdfDocumentFactory(any())).thenReturn(new Document());
        List<String> array = new ArrayList<String>();
        array.add( "001.png" );
		when(h.getFileNamesFromFolder(any(), any())).thenReturn(array);
        when(h.readImage(any())).thenReturn(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        byte[] byteArray = {0,0,1};
		when(h.getImageBytes(any())).thenReturn(byteArray);
        doNothing().when(h).addImageToPdf(any(),any());
	}
	
	@Test
	void generatePDFSucess() {
		
		String generatePDF = p.generatePDF(inputDir , "Agenor de Miranda Araújo Neto", "999.888.777-45",timestamp );
		
		assertThat(generatePDF).isEqualTo(expected);
	}

}
