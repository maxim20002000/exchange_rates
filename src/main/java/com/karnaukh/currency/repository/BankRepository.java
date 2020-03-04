package com.karnaukh.currency.repository;


import com.karnaukh.currency.entity.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankRepository {
    private static BankRepository bankRepositoryInstance;
    private List<Bank> bankList;

    public BankRepository() {
        this.bankList = new ArrayList<Bank>();
    }

    public static BankRepository getBankRepositoryInstance() {
        if (bankRepositoryInstance == null) {
            bankRepositoryInstance = new BankRepository();
        }
        return bankRepositoryInstance;
    }

    public void addToBankList(Bank bank) {
        bankList.add(bank);
    }


}
