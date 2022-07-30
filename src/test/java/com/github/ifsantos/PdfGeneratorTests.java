package com.github.ifsantos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.TestInstantiationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.ifsantos.pdf.io.IOHandler;
import com.itextpdf.text.Document;

@SpringBootTest
class PdfGeneratorTests {
	static Logger log = LoggerFactory.getLogger(PdfGeneratorTests.class);
	static byte[] imgByteArray = {};
	
	@InjectMocks
	PDFGenerator p;
	@Mock
	IOHandler h;
	
	String inputDir = "C:\\code_home\\mock\\pdf-gen";
	Long timestamp = System.currentTimeMillis();
	String expected = inputDir + File.separator +"output"+File.separator+"Agenor_de_Miranda_Araújo_Neto"+timestamp+".pdf";
	
	@BeforeEach
	void setUp() throws Exception {
        when(h.pdfDocumentFactory(any())).thenReturn(new Document());
        List<String> array = new ArrayList<String>();
        array.add( "001.png" );
		when(h.getFileNamesFromFolder(any(), any())).thenReturn(array);
        when(h.readImage(any())).thenReturn(new byte[1]);
		when(h.getImageBytes(any())).thenReturn(imgByteArray);
		when(h.getBufferedImage(any())).thenReturn(ImageIO.read(new ByteArrayInputStream(imgByteArray)));
        doNothing().when(h).addImageToPdf(any(),any());
	}
	
	@BeforeAll
	static void loadImgInput(){
		try {
			InputStream is = PdfGeneratorTests.class.getClassLoader().getResourceAsStream("test.png");
			imgByteArray = new byte[is.available()];
			is.read(imgByteArray);
			log.info("Mock Image Byte Array size: {} bytes",imgByteArray.length);
		} catch (Exception e) {
			throw new TestInstantiationException(e.toString());
		}
	}


	@Test
	void generatePDFSucess() {
		p.setInputFolder(inputDir);
		String generatePDF = p.generatePDF("Agenor de Miranda Araújo Neto", "999.888.777-45",timestamp );
		
		assertThat(Paths.get(generatePDF)).isEqualTo(Paths.get(expected));
	}
	
	@Test
	void testIfTwoStringPathsAreTheSame() {
		Path expected = Paths.get("C:\\code_home\\mock\\pdf-gen\\output\\Agenor_de_Miranda_Araújo_Neto1651759712704.pdf");
 		Path produced = Paths.get("C:\\code_home\\mock\\pdf-gen/output/Agenor_de_Miranda_Araújo_Neto1651759712704.pdf");
		
		assertThat(produced).isEqualTo(expected);
	}

}
