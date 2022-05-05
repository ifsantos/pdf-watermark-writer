package com.github.ifsantos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.ifsantos.api.PDFGeneratorService;
import com.github.ifsantos.api.model.PDFRequest;
import com.github.ifsantos.api.model.PDFRequest.User;
import com.github.ifsantos.api.model.PDFResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PDFGeneratorServiceTest {
	
	@InjectMocks
	PDFGeneratorService service;
	
	@Mock
	PDFGenerator g;
	
	MockHttpServletRequest request;
	String name = "jose da silva";
	String inputFolder = "c:\\";
	String cpf = "cpf";
	String outputFolder = "c:\\output\\jose_da_silva123456.pdf";
	PDFRequest r;
	
	@BeforeEach
	void setUp() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(g.generatePDF(any(), any(), any())).thenReturn(outputFolder);
        
        r = new PDFRequest();
        r.setInputFolder(inputFolder);
        r.setUsers(Arrays.asList(new User(name, cpf)));
	}
	

	@Test
	void testGenerate() {
		PDFResponse response = service.generate(r);
        List<String> expected = Arrays.asList(outputFolder);
		assertThat(response.getOutputFilePath()).isEqualTo(expected);
	}

}
