package com.karnaukh.currency.service;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CommonBankService {

    void updateCurrencyRates() throws IOException, JAXBException;

    List<Bank> getCurrencyRates(String city);

    List<Bank> getCurrencyForBank(Bank bank);

    void setBestCurrencyForCurrentBank(Bank bank);
}
