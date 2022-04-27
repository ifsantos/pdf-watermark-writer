package com.github.ifsantos.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ifsantos.PDFGenerator;
import com.github.ifsantos.api.model.PDFRequest;
import com.github.ifsantos.api.model.PDFRequest.User;
import com.github.ifsantos.api.model.PDFResponse;

@RestController
@RequestMapping("/pdf")
public class PDFGeneratorService {
	@Autowired
	public PDFGenerator generator;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PDFResponse generate(@RequestBody PDFRequest r) {
		PDFResponse resp = new PDFResponse();
		for (User user : r.getUsers()) {
			resp.add(generator.generatePDF(r.getInputFolder(), user.getLicensedName(), user.getCpf()));
		}
		return resp;
		
	}

}
