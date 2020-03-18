package com.karnaukh.currency.controller;


import com.karnaukh.currency.service.CommonBankService;
import com.karnaukh.currency.service.bank.AbsolutbankService;
import com.karnaukh.currency.service.bank.AlfabankService;
import com.karnaukh.currency.service.bank.BelarusbankService;
import com.karnaukh.currency.service.bank.BelgazprombankService;
import com.karnaukh.currency.service.bank.MTBankService;
import com.karnaukh.currency.service.bank.VTBbankService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Controller {

	private AbsolutbankService absolutbankService = new AbsolutbankService();
	private AlfabankService alfabankService = new AlfabankService();
	private BelarusbankService belarusbankService = new BelarusbankService();
	private BelgazprombankService belgazprombankService = new BelgazprombankService();
	private MTBankService mtBankService = new MTBankService();
	private VTBbankService vtBbankService = new VTBbankService();
	private CommonBankService commonBankService = new CommonBankService();

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

