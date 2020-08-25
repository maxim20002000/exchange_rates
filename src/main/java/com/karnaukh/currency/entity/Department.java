package com.karnaukh.currency.entity;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {

    private String nameDepartment;

    private List<Currency> currencyList;

    public Department() {
    }

    public Department(String nameDepartment, List<Currency> currencyList) {
        this.nameDepartment = nameDepartment;
        this.currencyList = currencyList;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }
}
