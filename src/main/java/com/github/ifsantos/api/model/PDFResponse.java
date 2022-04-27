package com.github.ifsantos.api.model;

public class PDFResponse {

	String outputFilePath;
	
	public PDFResponse() {	}

	public PDFResponse(String outputFilePath) {
		super();
		this.outputFilePath = outputFilePath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}
	
}
