package controller;

import XMLHandler.MTBankXMLHandler;
import entity.Bank;
import entity.Currency;
import entity.Department;
import org.xml.sax.SAXException;
import repository.BankRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ControllerImpl implements Controller {
    @Override
    public void getMTBankCurrency(String city) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        URL url = new URL("https://www.mtbank.by/currxml.php?ver=2.xml");
        InputStream inputStream = url.openStream();

        MTBankXMLHandler mtBankXMLHandler = new MTBankXMLHandler();
        mtBankXMLHandler.setCity(city);
        parser.parse(inputStream, mtBankXMLHandler);

        List<Department> departmentList = mtBankXMLHandler.getList();
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("MTBank", departmentList));
    }
}
