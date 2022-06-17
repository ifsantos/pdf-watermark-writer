package com.github.ifsantos.pdf.api.model;

import java.util.ArrayList;
import java.util.List;

public class PDFResponse {

	List<String> outputFilePath = new ArrayList<>();
	
	public PDFResponse() {	}

	public List<String> getOutputFilePath() {
		return outputFilePath;
	}

	public void add(String folder) {
		this.outputFilePath.add(folder);
	}
	
}
