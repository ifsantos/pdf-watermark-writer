package com.github.ifsantos.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ifsantos.PDFGenerator;
import com.github.ifsantos.api.model.PDFRequest;
import com.github.ifsantos.api.model.PDFResponse;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/generator")
public class PDFGeneratorService {
	@Autowired
	public PDFGenerator generator;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PDFResponse generate(@RequestBody PDFRequest r) throws MalformedURLException, DocumentException, IOException, URISyntaxException{
		
		return new PDFResponse(generator.generatePDF(r.getOriginFolder(), r.getOutputFileName(), r.getLicensedName(), r.getCpf()));
		
	}

}
