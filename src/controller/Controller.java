package controller;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Controller {
    void getMTBankCurrency(String city) throws ParserConfigurationException, SAXException, IOException;
}
