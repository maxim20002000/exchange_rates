package com.karnaukh.currency.service;

import com.karnaukh.currency.dao.DaoRates;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.BestAndWorstCurrency;
import com.karnaukh.currency.service.bank.AbsolutbankServiceImpl;
import com.karnaukh.currency.service.bank.AlfabankServiceImpl;
import com.karnaukh.currency.service.bank.BelarusbankServiceImpl;
import com.karnaukh.currency.service.bank.BelgazprombankServiceImpl;
import com.karnaukh.currency.service.bank.MTBankServiceImpl;
import com.karnaukh.currency.service.bank.VTBbankServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TreeSet;

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

    @Autowired
    @Qualifier("bankNames")
    private List<String> banksNames;

    private static Logger logger = LogManager.getLogger(CommonBankServiceImpl.class);

    @Override
    @Scheduled(cron = "0 30 * * * ?")
    public void updateCurrencyRates() throws JAXBException, IOException {
        absolutbankServiceImpl.updateCurrencyRate();
        alfabankServiceImpl.updateCurrencyRate();
        belarusbankServiceImpl.updateCurrencyRate();
        belgazprombankServiceImpl.updateCurrencyRate();
        mtBankServiceImpl.updateCurrencyRate();
        vtBbankServiceImpl.updateCurrencyRate();
        logger.info("Rates was updated");
        System.out.println("Rates was updated");
    }

    @Override
    public List<Bank> getCurrencyRates(String city) {
        List<Bank> bankList = daoRates.getBankList(city);
        return bankList;
    }

    @Override
    @Scheduled(cron = "0 35 * * * ?")
    public void setBestCurrencyForCurrentBank() {
        for (String name : banksNames) {
            Bank bank = new Bank();
            bank.setNameBank(name);
            daoRates.setBestCurrencyForCurrentBank(bank);
        }
        logger.info("Setted best and worst rates");
        System.out.println("Setted best and worst rates");
    }

    @Override
    public TreeSet<BestAndWorstCurrency> getBestAndWorstRatesPerWeekForBank(Bank bank) {
        List<BestAndWorstCurrency> bestAndWorstCurrencyList = daoRates.getBestAndWorstRatesForThePeriod(bank, Instant.now().minus(7, ChronoUnit.DAYS), Instant.now());
        TreeSet<BestAndWorstCurrency> bestAndWorstCurrencyTreeSet = new TreeSet<>(bestAndWorstCurrencyList);
        return bestAndWorstCurrencyTreeSet;
    }
}
