package com.karnaukh.currency.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_department", nullable = false)
	private int idDepartment;

	@Column(name = "name_department", nullable = false)
	private String nameDepartment;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Currency> currencyList;

	public Department() {
	}

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
