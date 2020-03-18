package com.karnaukh.currency;

import com.karnaukh.currency.controller.Controller;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Starter {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		Controller controller = new Controller();
		controller.printCurrencyRates("Гродно");


	}
}
