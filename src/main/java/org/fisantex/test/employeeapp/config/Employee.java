package org.fisantex.test.employeeapp.config;

import java.io.Serializable;

public class Employee implements Serializable{
    private String name;

	public String getName() {
		return "Funziona";
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
