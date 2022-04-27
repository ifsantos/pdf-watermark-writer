package com.github.ifsantos.api.model;

public class PDFRequest {

	String licensedName;
	String cpf;
	String originFolder;
	String outputFileName;
	
	public PDFRequest() {	}
	
	public PDFRequest(String licensedName, String cpf, String originFolder, String outputFileName) {
		super();
		this.licensedName = licensedName;
		this.cpf = cpf;
		this.originFolder = originFolder;
		this.outputFileName = outputFileName;
	}

	public String getLicensedName() {
		return licensedName;
	}
	public void setLicensedName(String licensedName) {
		this.licensedName = licensedName;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getOriginFolder() {
		return originFolder;
	}
	public void setOriginFolder(String originFolder) {
		this.originFolder = originFolder;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	@Override
	public String toString() {
		return "PDFRequest [protectedName=" + licensedName + ", cpf=" + cpf + ", originFolder=" + originFolder
				+ ", outputFileName=" + outputFileName + "]";
	}
	
	
}
