package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;
import com.karnaukh.currency.utils.MultipartUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlfabankService extends ServiceUtil implements IBankCurrency {

	@Override
	public void getCurrencyRate(String city) throws JAXBException, IOException {
		byte indexCity = 4;

		switch (city) {
			case ("Гродно"):
				indexCity = 8;
				break;
			case ("Барановичи"):
				indexCity = 17;
				break;
			case ("Бобруйск"):
				indexCity = 13;
				break;
			case ("Борисов"):
				indexCity = 15;
				break;
			case ("Брест"):
				indexCity = 5;
				break;
			case ("Витебск"):
				indexCity = 6;
				break;
			case ("Гомель"):
				indexCity = 7;
				break;
			case ("Жлобин"):
				indexCity = 19;
				break;
			case ("Лида"):
				indexCity = 18;
				break;
			case ("Могилев"):
				indexCity = 9;
				break;
			case ("Мозырь"):
				indexCity = 12;
				break;
			case ("Новополоцк"):
				indexCity = 14;
				break;
			case ("Пинск"):
				indexCity = 11;
				break;
			case ("Рогачев"):
				indexCity = 10;
				break;
			case ("Солигорск"):
				indexCity = 16;
				break;
		}

		String requestURL = "https://www.alfabank.by/currencys/";
		String charset = "UTF-8";
		MultipartUtil multipart = new MultipartUtil(requestURL, charset);

		multipart.addFormField("SETPROPERTYFILTER", "Y");
		multipart.addFormField("FORM_ACTION", "/currencys/");
		multipart.addFormField("bxajaxid", "3a3acf041fd6513023d55d4946b018e5");
		multipart.addFormField("AJAX_CALLjQuery", "Y");
		multipart.addFormField("TYPE_SECTION", "office_currency");
		//multipart.addFormField("DATE", "11.03.2020");
		multipart.addFormField("COORD_X", "");
		multipart.addFormField("COORD_Y", "");
		multipart.addFormField("COORD_TYPE", "");
		multipart.addFormField("OF[8][]", String.valueOf(indexCity));

		List<String> response = multipart.finish();

		String joined = String.join("", response);
		Document document = Jsoup.parse(joined);

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
					Currency currency = new Currency("BYN", name, buy, sell);
					currencyList.add(currency);
				}


			}
			String departmentName = element.select("[class=offices-table_link underlined-link]").text();
			departmentList.add(new Department(departmentName, new ArrayList<>(currencyList)));
			currencyList.clear();
		}
		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Alfabank", departmentList));
	}
}
