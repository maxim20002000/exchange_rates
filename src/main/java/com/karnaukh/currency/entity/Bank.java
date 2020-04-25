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
@Table(name = "banks")
public class Bank implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bank", nullable = false)
	private int idBank;

	@Column(name = "bank_name", nullable = false)
	private String nameBank;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Department> departmentList;

	public Bank() {
	}

	public Bank(String nameBank, List<Department> departmentList) {
		this.nameBank = nameBank;
		this.departmentList = departmentList;
	}

	@Override
	public String toString() {
		return "------- " + nameBank + " -------";
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}
}
