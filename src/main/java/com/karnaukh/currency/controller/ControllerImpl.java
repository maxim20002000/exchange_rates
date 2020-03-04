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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
    public void getMTBankCurrency(String city) throws ParserConfigurationException, SAXException, IOException, JAXBException {
        /*SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        URL url = new URL("https://www.mtbank.by/currxml.php?ver=2.xml");
        InputStream inputStream = url.openStream();

        MTBankXMLHandler mtBankXMLHandler = new MTBankXMLHandler();
        mtBankXMLHandler.setCity(city);
        parser.parse(inputStream, mtBankXMLHandler);

        List<Department> departmentList = mtBankXMLHandler.getList();
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("MTBank", departmentList));
        inputStream.close();*/

        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        URL url = new URL("https://www.mtbank.by/currxml.php?ver=2.xml");
        JAXBElement<RatesType> unmarshalledObject = (JAXBElement<RatesType>) unmarshaller.unmarshal(url);

        RatesType ratesType = unmarshalledObject.getValue();

        List<Department> departmentList = new ArrayList<>();
        for (DepartmentType departmentType : ratesType.getDepartment()) {
            if (departmentType.getCity().equals(city)) {
                List<Currency> currencyList = new ArrayList<>();
                for (CurrencyType currencyType : departmentType.getCurrency()) {
                    Currency currency = new Currency(currencyType.getCode(),
                            currencyType.getCodeTo(),
                            Double.parseDouble(currencyType.getPurchase()),
                            Double.parseDouble(currencyType.getSale()));
                    currencyList.add(currency);
                }
                departmentList.add(new Department(departmentType.getLabel(), new ArrayList<>(currencyList)));
                currencyList.clear();
            }

        }
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("MTBank", departmentList));
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
    public void getAbsolutbankCurrency(String city) throws IOException, ParserConfigurationException, SAXException, JAXBException {
        /*SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        URL url = new URL("https://absolutbank.by/exchange-rates.xml");

        InputStream inputStream = url.openStream();

        AbsolutBankXMLHandler absolutBankXMLHandler = new AbsolutBankXMLHandler();
        parser.parse(inputStream, absolutBankXMLHandler);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department("All departments", absolutBankXMLHandler.getCurrencyList()));
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("AbsolutBank", departmentList));
        inputStream.close();*/


        JAXBContext jaxbContext = JAXBContext.newInstance(bankClassGenerated.absolutBank.ObjectFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        URL url = new URL("https://absolutbank.by/exchange-rates.xml");
        JAXBElement<BranchesType> unmarshalledObject = (JAXBElement<BranchesType>) unmarshaller.unmarshal(url);

        BranchesType branchType = unmarshalledObject.getValue();
        List<Department> departmentList = new ArrayList<>();
        for (BranchType branch : branchType.getBranch()) {
            if (foundString(branch.getName(), city)) {
                List<Currency> currencyList = new ArrayList<>();
                List<RateType> newRateType = new ArrayList<>();
                for (RateType rateType : branch.getRate()) {
                    if (rateType.getCurrency().length() < 4) {
                        newRateType.add(rateType);
                    }
                }
                for (RateType rateType : newRateType) {
                    List<JAXBElement> listRate = rateType.getContent();
                    Double sell = null;
                    Double buy = null;

                    listRate.remove(0);
                    listRate.remove(1);
                    listRate.remove(2);

                    for (JAXBElement rate : listRate) {
                        if (rate.getName().toString().equals("sell")) {
                            sell = Double.parseDouble(rate.getValue().toString());
                        }
                        if (rate.getName().toString().equals("buy")) {
                            buy = Double.parseDouble(rate.getValue().toString());
                        }
                        if (buy != null && sell != null) {
                            Currency currency = new Currency(rateType.getCurrency().toUpperCase(),
                                    "BYN", buy, sell);
                            currencyList.add(currency);
                            buy = null;
                            sell = null;
                        }
                    }
                }
                departmentList.add(new Department(branch.getName(), new ArrayList<>(currencyList)));
                currencyList.clear();
            }
        }
        BankRepository.getBankRepositoryInstance().addToBankList(new Bank("AbsolutBank", departmentList));
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

    private boolean foundString(String fullString, String necessaryString) {
        String[] strings = fullString.split(" ");
        for (String string : strings) {
            if (string.equals(necessaryString)) {
                return true;
            }
        }
        return false;
    }

}

