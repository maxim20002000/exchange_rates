package com.karnaukh.currency.entity;

import java.util.List;

public class Department {
	private String nameDepartment;
	private List<Currency> currencyList;

	public Department(String nameDepartment, List<Currency> currencyList) {
		this.nameDepartment = nameDepartment;
		this.currencyList = currencyList;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	@Override
	public String toString() {
		return "******* " + nameDepartment + " *******";
	}
}
