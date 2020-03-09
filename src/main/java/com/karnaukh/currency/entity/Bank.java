package com.karnaukh.currency.entity;

import java.util.List;

public class Bank {
	private String nameBank;
	private List<Department> departmentList;

	public Bank(String nameBank, List<Department> departmentList) {
		this.nameBank = nameBank;
		this.departmentList = departmentList;
	}

}
