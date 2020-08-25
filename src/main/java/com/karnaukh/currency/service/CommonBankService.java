package com.karnaukh.currency.service;

import com.karnaukh.currency.entity.Bank;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CommonBankService {

    void updateCurrencyRates() throws IOException, JAXBException;

    List<Bank> getCurrencyRates(String city);
}
