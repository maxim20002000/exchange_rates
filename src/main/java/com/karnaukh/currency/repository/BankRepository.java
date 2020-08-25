package com.karnaukh.currency.repository;


import com.karnaukh.currency.entity.Bank;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BankRepository {

    private List<Bank> bankList;

    public BankRepository() {
        this.bankList = new ArrayList<>();
    }

    public void addToBankList(Bank bank) {
        bankList.add(bank);
    }

    public List<Bank> getBankList() {
        return bankList;
    }

    public void clearBankList() {
        bankList.clear();
    }
}
