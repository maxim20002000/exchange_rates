package com.karnaukh.currency.service;

import com.karnaukh.currency.service.bank.AbsolutbankServiceImpl;
import com.karnaukh.currency.service.bank.AlfabankServiceImpl;
import com.karnaukh.currency.service.bank.BelarusbankServiceImpl;
import com.karnaukh.currency.service.bank.BelgazprombankServiceImpl;
import com.karnaukh.currency.service.bank.MTBankServiceImpl;
import com.karnaukh.currency.service.bank.VTBbankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

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

    @Override
    //@Scheduled(fixedDelay = 5000)
    public void updateCurrencyRates() throws JAXBException, IOException {
        absolutbankServiceImpl.updateCurrencyRate();
        alfabankServiceImpl.updateCurrencyRate();
        belarusbankServiceImpl.updateCurrencyRate();
        belgazprombankServiceImpl.updateCurrencyRate();
        mtBankServiceImpl.updateCurrencyRate();
        vtBbankServiceImpl.updateCurrencyRate();
    }
}
