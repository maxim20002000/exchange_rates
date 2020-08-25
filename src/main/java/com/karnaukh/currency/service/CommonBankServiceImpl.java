package com.karnaukh.currency.service;

import com.karnaukh.currency.service.bank.AbsolutbankServiceImpl;
import com.karnaukh.currency.service.bank.AlfabankServiceImpl;
import com.karnaukh.currency.service.bank.BelarusbankServiceImpl;
import com.karnaukh.currency.service.bank.BelgazprombankServiceImpl;
import com.karnaukh.currency.service.bank.MTBankServiceImpl;
import com.karnaukh.currency.service.bank.VTBbankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
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



    @Override
    //@Scheduled(fixedDelay = 5000)
    public void updateCurrencyRates() throws JAXBException, IOException {
     /*   String city1 = "Гродно";

        absolutbankServiceImpl.getCurrencyRate(city1);*/
        alfabankServiceImpl.updateCurrencyRate();

       // absolutbankServiceImpl.updateCurrencyRate();
          //  belarusbankServiceImpl.updateCurrencyRate();



   /*
        belgazprombankServiceImpl.getCurrencyRate(city1);
        mtBankServiceImpl.getCurrencyRate(city1);
        vtBbankServiceImpl.getCurrencyRate(city1);*/
    }

}
