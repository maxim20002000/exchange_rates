package controller;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface Controller {
    void getMTBankCurrency(String city) throws ParserConfigurationException, SAXException, IOException;

    void getBelarusBankCurrency(String city) throws IOException;

    void getAlfabankCurrency() throws IOException;

    void getAbsolutbankCurrency() throws IOException, ParserConfigurationException, SAXException;

    void getVTBbankCurrency() throws ParserConfigurationException, SAXException, IOException;
}
