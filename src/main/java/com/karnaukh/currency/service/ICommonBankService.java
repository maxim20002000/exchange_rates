package com.karnaukh.currency.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ICommonBankService {
	void updateCurrency(String city) throws IOException, JAXBException;

	void printCurrencyRates();
}
