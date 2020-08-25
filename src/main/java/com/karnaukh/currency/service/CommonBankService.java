package com.karnaukh.currency.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CommonBankService {

    void updateCurrencyRates() throws IOException, JAXBException;
}
