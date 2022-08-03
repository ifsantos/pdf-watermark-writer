package com.github.ifsantos.pdf.api.model;

import java.util.ArrayList;
import java.util.List;

public class PDFRequest {
	String inputFolder;
	List<User> users;

	public PDFRequest() {
		this.users = new ArrayList<>();
	 }

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String originFolder) {
		this.inputFolder = originFolder;
	}

	public static class User {
		String licensedName;
		String cpf;

		public User(String licensedName, String cpf) {
			this.licensedName = licensedName;
			this.cpf = cpf;
		}

		public String getLicensedName() {
			return licensedName;
		}

		public String getCpf() {
			return cpf;
		}
	}
}
