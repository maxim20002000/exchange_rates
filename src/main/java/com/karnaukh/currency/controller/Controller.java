package com.karnaukh.currency.controller;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface Controller {
	void getMTBankCurrency(String city) throws ParserConfigurationException, SAXException, IOException, JAXBException;

	void getBelarusBankCurrency(String city) throws IOException;

	void getAlfabankCurrencyOLD() throws IOException;

	void getAbsolutbankCurrency(String city) throws IOException, ParserConfigurationException, SAXException, JAXBException;

	void getVTBbankCurrency() throws ParserConfigurationException, SAXException, IOException, JAXBException;

	void getBelgazpromCurrency() throws JAXBException, MalformedURLException;

	void getAlfabankCurrency() throws IOException;
}
