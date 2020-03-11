package controller;

import XMLHandler.AbsolutBankXMLHandler;
import XMLHandler.MTBankXMLHandler;
import XMLHandler.VTBBankXMLHandler;
import com.google.gson.Gson;
import com.karnaukh.currency.bankAPI.absolutBank.BranchType;
import com.karnaukh.currency.bankAPI.absolutBank.BranchesType;
import com.karnaukh.currency.bankAPI.absolutBank.RateType;
import com.karnaukh.currency.bankAPI.belgazpromBank.RangeType;
import com.karnaukh.currency.bankAPI.mtbank.CurrencyType;
import com.karnaukh.currency.bankAPI.mtbank.DepartmentType;
import com.karnaukh.currency.bankAPI.mtbank.ObjectFactory;
import com.karnaukh.currency.bankAPI.mtbank.RatesType;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.entity.secondary.Alfabank;
import com.karnaukh.currency.entity.secondary.AlfabankRoot;
import com.karnaukh.currency.entity.secondary.Belarusbank;
import com.karnaukh.currency.repository.BankRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller {

	@Override
	public void getMTBankCurrency(String city) throws IOException, JAXBException {

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
	@Deprecated
	public void getAlfabankCurrencyOLD() throws IOException {
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
	public void getAbsolutbankCurrency(String city) throws IOException, JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.absolutBank.ObjectFactory.class);
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
					List<JAXBElement> listRate = new ArrayList<>((List) rateType.getContent());
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
	public void getVTBbankCurrency() throws ParserConfigurationException, SAXException, IOException, JAXBException {
		/*SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		URL url = new URL("https://www.vtb-bank.by/sites/default/files/rates.xml");

		InputStream inputStream = url.openStream();

		VTBBankXMLHandler vtbBankXMLHandler = new VTBBankXMLHandler();
		parser.parse(inputStream, vtbBankXMLHandler);
		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(new Department("All departments", vtbBankXMLHandler.getCurrencyList()));
		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("VTBBank", departmentList));
		inputStream.close();*/

		JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.vtbBank.ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		URL url = new URL("https://www.vtb-bank.by/sites/default/files/rates.xml");
		JAXBElement<com.karnaukh.currency.bankAPI.vtbBank.RatesType> unmarshalledObject =
				(JAXBElement<com.karnaukh.currency.bankAPI.vtbBank.RatesType>) unmarshaller.unmarshal(url);

		com.karnaukh.currency.bankAPI.vtbBank.RatesType ratesType = unmarshalledObject.getValue();
		List<com.karnaukh.currency.bankAPI.vtbBank.RateType> rateTypeList = ratesType.getMain().getRate();
		List<Department> departmentList = new ArrayList<>();
		List<Currency> currencyList = new ArrayList<>();
		for (com.karnaukh.currency.bankAPI.vtbBank.RateType rateType : rateTypeList) {
			Currency currency = new Currency(rateType.getCode().toUpperCase(),
					"BYN", Double.parseDouble(rateType.getBuy()),
					Double.parseDouble(rateType.getSell()));
			currencyList.add(currency);
		}
		departmentList.add(new Department("ALL departments", currencyList));
		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("VTBbank", departmentList));
	}

	@Override
	public void getBelgazpromCurrency() throws JAXBException, MalformedURLException {
		JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.belgazpromBank.ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		URL url = new URL("https://belgazprombank.by/upload/courses.xml");
		JAXBElement<com.karnaukh.currency.bankAPI.belgazpromBank.BranchesType> unmarshalledObject =
				(JAXBElement<com.karnaukh.currency.bankAPI.belgazpromBank.BranchesType>) unmarshaller.unmarshal(url);
		com.karnaukh.currency.bankAPI.belgazpromBank.BranchesType branchesType = unmarshalledObject.getValue();
		List<Department> departmentList = new ArrayList<>();
		for (com.karnaukh.currency.bankAPI.belgazpromBank.BranchType branch : branchesType.getBranch()) {
			List<com.karnaukh.currency.bankAPI.belgazpromBank.RateType> rateList = branch.getRate();
			List<Currency> currencyList = new ArrayList<>();
			for (com.karnaukh.currency.bankAPI.belgazpromBank.RateType rate : rateList) {
				List<JAXBElement> jaxbElements = new ArrayList<>((List) rate.getContent());
				if (jaxbElements.size() == 2) {
					Currency currency = new Currency(rate.getCurrency().toUpperCase(), "BYN",
							Double.parseDouble((String) jaxbElements.get(0).getValue()),
							Double.parseDouble((String) jaxbElements.get(1).getValue()));
					currencyList.add(currency);

				} else {
					if (jaxbElements.size() > 1) {
						JAXBElement jaxbElement = jaxbElements.get(0);
						RangeType rangeType = (RangeType) jaxbElement.getValue();
						Currency currency = new Currency(rate.getCurrency().toUpperCase(),
								"BYN",
								Double.parseDouble(rangeType.getBuy()),
								Double.parseDouble(rangeType.getSell()));
						currencyList.add(currency);
					}
				}
			}
			departmentList.add(new Department(branch.getName(), new ArrayList<>(currencyList)));
			currencyList.clear();
		}

		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Belgazprom", departmentList));

	}

	@Override
	public void getAlfabankCurrency() throws IOException {
		Document document = null;
		try {
			document = Jsoup.connect("https://www.alfabank.by/currencys/")
					.maxBodySize(0)
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements jsTabs = document.select("[class=js-tabs]");
		List<Element> listElement = jsTabs.subList(0, jsTabs.size());
		List<Department> departmentList = new ArrayList<>();
		for (Element element : listElement) {
			Elements typesCurrency = element.select("[class=offices-table_currency]");
			List<Currency> currencyList = new ArrayList<>();
			for (Element typeCurrency : typesCurrency.subList(0, 3)) {
				Element nameElement = typeCurrency.select("[class=informer-currencies_name informer-currencies_name__normal]").first();
				String name = "";
				Elements buySellElement = typeCurrency.select("[class=informer-currencies_value-txt]");
				Double buy = Double.parseDouble(buySellElement.get(0).text().replace(',', '.'));
				Double sell = Double.parseDouble(buySellElement.get(1).text().replace(',', '.'));
				if (nameElement.text().equals("1 Доллар США")) {
					name = "USD";
				}
				if (nameElement.text().equals("1 Евро")) {
					name = "EUR";
				}
				if (nameElement.text().equals("100 Российских рублей")) {
					name = "RUB";
				}
				if (!name.equals("")) {
					Currency currency = new Currency(name, "BYN", buy, sell);
					currencyList.add(currency);
				}


			}
			String departmentName = element.select("[class=offices-table_link underlined-link]").text();
			departmentList.add(new Department(departmentName, new ArrayList<>(currencyList)));
			currencyList.clear();
		}
		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Alfabank", departmentList));

	}

	private String getJsonTextFromURI(URL url) throws IOException {
		InputStream inputStream = url.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
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

