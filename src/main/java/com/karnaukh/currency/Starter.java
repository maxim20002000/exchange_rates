package com.karnaukh.currency;

import com.karnaukh.currency.config.SpringConfig;
import com.karnaukh.currency.controller.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Starter {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, JAXBException {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		Controller controller = context.getBean(Controller.class);


		controller.printCurrencyRates("Гродно");

	}
}
