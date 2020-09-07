package com.karnaukh.currency.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.karnaukh.currency.entity.Department;

import java.util.Date;
import java.util.List;


public class DtoBank {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS dd-MM-yyyy")
    private Date date;

    private String nameBank;

    private List<Department> departmentList;

    public DtoBank() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
