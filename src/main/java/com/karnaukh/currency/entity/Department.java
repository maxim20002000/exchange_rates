package com.karnaukh.currency.entity;

import java.util.List;

public class Department {
	private String nameDepartment;
	private List<Currency> currencyList;

	public Department(String nameDepartment, List<Currency> currencyList) {
		this.nameDepartment = nameDepartment;
		this.currencyList = currencyList;
	}


}
