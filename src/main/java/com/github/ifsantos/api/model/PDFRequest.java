package com.github.ifsantos.api.model;

import java.util.ArrayList;
import java.util.List;

public class PDFRequest {
	String inputFolder;
	List<User> users = new ArrayList<>();

	public PDFRequest() { }

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
			super();
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
