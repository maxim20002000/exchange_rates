package com.karnaukh.currency.service.bank;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface IBankCurrency {
	void getCurrencyRate(String city) throws JAXBException, IOException;
}
