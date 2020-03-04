package com.karnaukh.currency;

import bankClassGenerated.absolutBank.BranchType;
import com.karnaukh.currency.controller.Controller;
import com.karnaukh.currency.controller.ControllerImpl;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Starter {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, JAXBException {
        Controller controller = new ControllerImpl();
        controller.getMTBankCurrency("Гомель");
        controller.getBelarusBankCurrency("Гродно");
        controller.getAlfabankCurrency();
        controller.getAbsolutbankCurrency();
        controller.getVTBbankCurrency();


    }
}
