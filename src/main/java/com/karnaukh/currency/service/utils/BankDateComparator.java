package com.karnaukh.currency.service.utils;

import com.karnaukh.currency.entity.Bank;

import java.util.Comparator;

public class BankDateComparator implements Comparator<Bank> {
    @Override
    public int compare(Bank o1, Bank o2) {
        return -o1.getDate().compareTo(o2.getDate());
    }
}
