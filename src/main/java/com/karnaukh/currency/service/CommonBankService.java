package com.karnaukh.currency.service;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.BestAndWorstCurrency;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public interface CommonBankService {

    /**
     * This method is used to update rates every half o'clock
     */
    void updateCurrencyRates() throws IOException, JAXBException;

    List<Bank> getCurrencyRates(String city);

    TreeSet<BestAndWorstCurrency> getBestAndWorstRatesPerWeekForBank(Bank bank);

    //  List<Bank> getCurrencyForBank(Bank bank);

    void setBestCurrencyForCurrentBank();


}
