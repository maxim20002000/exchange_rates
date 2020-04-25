package com.karnaukh.currency.controller;


import com.karnaukh.currency.service.CommonBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component
public class Controller {

	@Autowired
	private CommonBankService commonBankService;

	public void updateCurrency(String city) throws IOException, JAXBException {
		commonBankService.updateCurrency(city);
	}

	public void printCurrencyRates(String city) throws IOException, JAXBException {
		updateCurrency(city);
		commonBankService.printCurrencyRates();
		commonBankService.saveBanksCurrencyRatesToDB();
	}
}

