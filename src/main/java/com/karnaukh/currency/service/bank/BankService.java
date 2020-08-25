package com.karnaukh.currency.service.bank;

import org.springframework.scheduling.annotation.Async;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface BankService {

	@Async
	void updateCurrencyRate() throws JAXBException, IOException;
}
