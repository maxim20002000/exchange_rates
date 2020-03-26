package com.karnaukh.currency.controller;


import com.karnaukh.currency.service.CommonBankService;
import com.karnaukh.currency.service.bank.AbsolutbankService;
import com.karnaukh.currency.service.bank.AlfabankService;
import com.karnaukh.currency.service.bank.BelarusbankService;
import com.karnaukh.currency.service.bank.BelgazprombankService;
import com.karnaukh.currency.service.bank.MTBankService;
import com.karnaukh.currency.service.bank.VTBbankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component
public class Controller {

	@Autowired
	private AbsolutbankService absolutbankService;

	@Autowired
	private AlfabankService alfabankService;

	@Autowired
	private BelarusbankService belarusbankService;

	@Autowired
	private BelgazprombankService belgazprombankService;

	@Autowired
	private MTBankService mtBankService;

	@Autowired
	private VTBbankService vtBbankService;

	@Autowired
	private CommonBankService commonBankService;

	public void updateCurrency(String city) throws IOException, JAXBException {
		absolutbankService.getCurrencyRate(city);
		alfabankService.getCurrencyRate(city);
		belarusbankService.getCurrencyRate(city);
		belgazprombankService.getCurrencyRate(city);
		mtBankService.getCurrencyRate(city);
		vtBbankService.getCurrencyRate(city);
	}

	public void printCurrencyRates(String city) throws IOException, JAXBException {
		updateCurrency(city);
		commonBankService.printCurrencyRates();
	}
}

