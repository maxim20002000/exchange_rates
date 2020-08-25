package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.dao.DaoRates;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AlfabankServiceImpl implements BankService {

    @Autowired
    private Logger logger;

    @Autowired
    private DaoRates daoRates;

    @Autowired
    @Qualifier("alfabankIndexCities")
    private Map<String, String> indexCities;


    @Override
    public void updateCurrencyRate() throws JAXBException, IOException {
        Iterator<Map.Entry<String, String>> iterator = indexCities.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String city = entry.getKey();
            String indexCity = entry.getValue();
            if (!city.equals("") && !indexCity.equals("")) {

                Connection.Response response =
                        Jsoup.connect("https://www.alfabank.by/currencys/")
                                .userAgent("Mozilla/5.0")
                                .timeout(10 * 1000)
                                .method(Connection.Method.POST)
                                .data("SETPROPERTYFILTER", "Y")
                                .data("FORM_ACTION", "/currencys/")
                                .data("bxajaxid", "3a3acf041fd6513023d55d4946b018e5")
                                .data("AJAX_CALLjQuery", "Y")
                                .data("TYPE_SECTION", "office_currency")
                                .data("COORD_X", "")
                                .data("COORD_Y", "")
                                .data("COORD_TYPE", "")
                                .data("OF[8][]", indexCity)
                                .followRedirects(true)
                                .execute();

                Document document = response.parse();
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
                logger.info("Alfabank rates updated in DB");
                Bank alfaBank = new Bank("Alfabank", city, departmentList);
                daoRates.saveBank(alfaBank);
                departmentList.clear();
            }
        }
    }
}
