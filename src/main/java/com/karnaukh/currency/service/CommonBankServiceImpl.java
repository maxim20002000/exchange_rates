package com.karnaukh.currency.service;

import com.karnaukh.currency.dao.DaoRates;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.service.bank.AbsolutbankServiceImpl;
import com.karnaukh.currency.service.bank.AlfabankServiceImpl;
import com.karnaukh.currency.service.bank.BelarusbankServiceImpl;
import com.karnaukh.currency.service.bank.BelgazprombankServiceImpl;
import com.karnaukh.currency.service.bank.MTBankServiceImpl;
import com.karnaukh.currency.service.bank.VTBbankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class CommonBankServiceImpl implements CommonBankService {

    @Autowired
    private AbsolutbankServiceImpl absolutbankServiceImpl;

    @Autowired
    private AlfabankServiceImpl alfabankServiceImpl;

    @Autowired
    private BelarusbankServiceImpl belarusbankServiceImpl;

    @Autowired
    private BelgazprombankServiceImpl belgazprombankServiceImpl;

    @Autowired
    private MTBankServiceImpl mtBankServiceImpl;

    @Autowired
    private VTBbankServiceImpl vtBbankServiceImpl;

    @Autowired
    private DaoRates daoRates;

    @Override
    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void updateCurrencyRates() throws JAXBException, IOException {
        absolutbankServiceImpl.updateCurrencyRate();
        alfabankServiceImpl.updateCurrencyRate();
        belarusbankServiceImpl.updateCurrencyRate();
        belgazprombankServiceImpl.updateCurrencyRate();
        mtBankServiceImpl.updateCurrencyRate();
        vtBbankServiceImpl.updateCurrencyRate();
    }

    @Override
    public List<Bank> getCurrencyRates(String city) {
        List<Bank> bankList = daoRates.getBankList(city);
        return bankList;
    }

    @Override
    public List<Bank> getCurrencyForBank(Bank bank) {
        Date date = new Date();
        daoRates.getCurrencyForBank(bank,date.toInstant().minus(7,ChronoUnit.DAYS),date.toInstant());
        return null;
    }

    @Override
    public void setBestCurrencyForCurrentBank(Bank bank) {
        daoRates.setBestCurrencyForCurrentBank(bank);

    }
}
