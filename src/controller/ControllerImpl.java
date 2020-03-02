package controller;

import XMLHandler.AbsolutBankXMLHandler;
import XMLHandler.MTBankXMLHandler;
import XMLHandler.VTBBankXMLHandler;
import com.google.gson.Gson;
import entity.*;
import entity.secondary.Alfabank;
import entity.secondary.AlfabankRoot;
import entity.secondary.Belarusbank;
import org.xml.sax.SAXException;
import repository.BankRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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
        inputStream.close();
    }

    @Override
    public void getBelarusBankCurrency(String city) throws IOException {
        URL url = new URL("https://belarusbank.by/api/kursExchange?city=" + city);
        String jsonText = getJsonTextFromURI(url);

        Gson gson = new Gson();
        Belarusbank[] myArray = gson.fromJson(jsonText, Belarusbank[].class);
        List<Belarusbank> belarusbankList = Arrays.asList(myArray);
        List<Department> departments = new ArrayList<>();
        List<Currency> currencyList = new ArrayList<>();
        for (Belarusbank belarusbank : belarusbankList) {
            currencyList.add(new Currency("BYN", "USD",
                    Double.parseDouble(belarusbank.getUSD_in()),
                    Double.parseDouble(belarusbank.getUSD_out())));
            currencyList.add(new Currency("BYN", "EUR",
                    Double.parseDouble(belarusbank.getEUR_in()),
                    Double.parseDouble(belarusbank.getEUR_out())));
            currencyList.add(new Currency("BYN", "RUB",
                    Double.parseDouble(belarusbank.getRUB_in()),
                    Double.parseDouble(belarusbank.getRUB_out())));
            currencyList.add(new Currency("BYN", "PLN",
                    Double.parseDouble(belarusbank.getPLN_in()),
                    Double.parseDouble(belarusbank.getPLN_out())));
            departments.add(new Department(belarusbank.getFilial_text(), new ArrayList<>(currencyList)));
            currencyList.clear();
        }
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Belarusbank", departments));
    }

    @Override
    public void getAlfabankCurrency() throws IOException {
        URL url = new URL("https://developerhub.alfabank.by:8273/partner/1.0.0/public/rates");
        String jsonText = getJsonTextFromURI(url);
        Gson gson = new Gson();

        AlfabankRoot currencyAlfa = gson.fromJson(jsonText, AlfabankRoot.class);
        List<Alfabank> alfaList = currencyAlfa.getList();
        List<Currency> currencyList = new ArrayList<>();
        List<Department> departments = new ArrayList<>();
        for (Alfabank alfa : alfaList) {
            currencyList.add(new Currency(alfa.getBuyIso(), alfa.getSellIso(),
                    Double.parseDouble(alfa.getSellRate()),
                    Double.parseDouble(alfa.getBuyRate())));

        }
        departments.add(new Department("All departments", currencyList));
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Alfabank", departments));
    }

    @Override
    public void getAbsolutbankCurrency() throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        URL url = new URL("https://absolutbank.by/exchange-rates.xml");

        InputStream inputStream = url.openStream();

        AbsolutBankXMLHandler absolutBankXMLHandler = new AbsolutBankXMLHandler();
        parser.parse(inputStream, absolutBankXMLHandler);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department("All departments", absolutBankXMLHandler.getCurrencyList()));
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("AbsolutBank", departmentList));
        inputStream.close();
    }

    @Override
    public void getVTBbankCurrency() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        URL url = new URL("https://www.vtb-bank.by/sites/default/files/rates.xml");

        InputStream inputStream = url.openStream();

        VTBBankXMLHandler vtbBankXMLHandler = new VTBBankXMLHandler();
        parser.parse(inputStream, vtbBankXMLHandler);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department("All departments", vtbBankXMLHandler.getCurrencyList()));
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("VTBBank", departmentList));
        inputStream.close();
    }

    private String getJsonTextFromURI(URL url) throws IOException {
        InputStream inputStream = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            sb.append((char) cp);
        }
        inputStream.close();
        return sb.toString();
    }

}

