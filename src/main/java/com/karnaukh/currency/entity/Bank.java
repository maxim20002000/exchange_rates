package com.karnaukh.currency.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection = "banks")
public class Bank implements Serializable {

    @Id
    private String id;

    private String nameBank;

    private Date date;

    private String city;

    private List<Department> departmentList;

    public Bank() {
    }

    public Bank(String nameBank, String city, List<Department> departmentList) {
        this.nameBank = nameBank;
        this.city = city;
        this.departmentList = departmentList;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
