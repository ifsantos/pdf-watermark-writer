package com.github.ifsantos.api;

import com.github.ifsantos.PDFGenerator;
import com.github.ifsantos.api.model.PDFRequest;
import com.github.ifsantos.api.model.PDFResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@RestController
@RequestMapping
public class PDFGeneratorService {
	@Autowired
	public PDFGenerator generator;

	@PostMapping("/pdf")
	@ResponseStatus(HttpStatus.CREATED)
	public PDFResponse generate(@RequestBody PDFRequest r) {
		PDFResponse resp = new PDFResponse();
		generator.setInputFolder(r.getInputFolder());
		r.getUsers().forEach(user ->
			resp.add(generator.generatePDF(user.getLicensedName(), user.getCpf(),
					System.currentTimeMillis()))
		);
		return resp;
	}

	@PostMapping("/flux")
	public Flux<String> getMethodName(@RequestBody PDFRequest r) {
		generator.setInputFolder(r.getInputFolder());
		return Flux.fromIterable(r.getUsers())
			.map(user -> generator.generatePDF(user.getLicensedName(), user.getCpf(),
					System.currentTimeMillis()))
					.defaultIfEmpty("defaultV").subscribeOn(Schedulers.boundedElastic());
	}	
}
