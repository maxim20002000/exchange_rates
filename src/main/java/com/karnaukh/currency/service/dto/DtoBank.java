package com.karnaukh.currency.service.dto;

import com.karnaukh.currency.entity.Department;
import org.springframework.data.annotation.Id;

import java.util.List;


public class DtoBank {

    private String id;

    private String nameBank;

    private List<Department> departmentList;

    public DtoBank() {
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
