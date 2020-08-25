package com.karnaukh.currency.service.bank;

import com.google.gson.Gson;
import com.karnaukh.currency.dao.DaoRates;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.entity.secondary.Belarusbank;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BelarusbankServiceImpl implements BankService {

    @Autowired
    private DaoRates daoRates;

    @Autowired
    private Logger logger;

    @Autowired
    @Qualifier("cities")
    private List<String> cities;

    @Override
    public void updateCurrencyRate() {
        for (String city : cities) {
            try {
                String jsonText = Jsoup.connect("https://belarusbank.by/api/kursExchange?city=" + city).ignoreContentType(true).execute().body();

                Gson gson = new Gson();
                Belarusbank[] belarusbankList = gson.fromJson(jsonText, Belarusbank[].class);
                List<Department> departmentList = new ArrayList<>();
                List<Currency> currencyList = new ArrayList<>();
                for (Belarusbank belarusbank : belarusbankList) {
                    if (belarusbank.getUSD_in() != null && belarusbank.getUSD_out() != null
                            && !belarusbank.getUSD_in().equals("0.0000") && !belarusbank.getUSD_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "USD",
                                Double.parseDouble(belarusbank.getUSD_in()),
                                Double.parseDouble(belarusbank.getUSD_out())));
                    }
                    if (belarusbank.getEUR_in() != null && belarusbank.getEUR_out() != null
                            && !belarusbank.getEUR_in().equals("0.0000") && !belarusbank.getEUR_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "EUR",
                                Double.parseDouble(belarusbank.getEUR_in()),
                                Double.parseDouble(belarusbank.getEUR_out())));
                    }
                    if (belarusbank.getRUB_in() != null && belarusbank.getRUB_out() != null
                            && !belarusbank.getRUB_in().equals("0.0000") && !belarusbank.getRUB_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "RUB",
                                Double.parseDouble(belarusbank.getRUB_in()),
                                Double.parseDouble(belarusbank.getRUB_out())));
                    }
                    if (belarusbank.getPLN_in() != null && belarusbank.getPLN_out() != null
                            && !belarusbank.getPLN_in().equals("0.0000") && !belarusbank.getPLN_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "PLN",
                                Double.parseDouble(belarusbank.getPLN_in()),
                                Double.parseDouble(belarusbank.getPLN_out())));
                    }
                    if (belarusbank.getGBP_in() != null && belarusbank.getGBP_out() != null
                            && !belarusbank.getGBP_in().equals("0.0000") && !belarusbank.getGBP_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "GBP",
                                Double.parseDouble(belarusbank.getGBP_in()),
                                Double.parseDouble(belarusbank.getGBP_out())));
                    }
                    if (belarusbank.getCAD_in() != null && belarusbank.getCAD_out() != null
                            && !belarusbank.getCAD_in().equals("0.0000") && !belarusbank.getCAD_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "CAD",
                                Double.parseDouble(belarusbank.getCAD_in()),
                                Double.parseDouble(belarusbank.getCAD_out())));
                    }
                    if (belarusbank.getUAN_in() != null && belarusbank.getUAN_out() != null
                            && !belarusbank.getUAN_in().equals("0.0000") && !belarusbank.getUAN_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "UAN",
                                Double.parseDouble(belarusbank.getUAN_in()),
                                Double.parseDouble(belarusbank.getUAN_out())));
                    }
                    if (belarusbank.getSEK_in() != null && belarusbank.getSEK_out() != null
                            && !belarusbank.getSEK_in().equals("0.0000") && !belarusbank.getSEK_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "SEK",
                                Double.parseDouble(belarusbank.getSEK_in()),
                                Double.parseDouble(belarusbank.getSEK_out())));
                    }
                    if (belarusbank.getCHF_in() != null && belarusbank.getCHF_out() != null
                            && !belarusbank.getCHF_in().equals("0.0000") && !belarusbank.getCHF_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "CHF",
                                Double.parseDouble(belarusbank.getCHF_in()),
                                Double.parseDouble(belarusbank.getCHF_out())));
                    }
                    if (belarusbank.getJPY_in() != null && belarusbank.getJPY_out() != null
                            && !belarusbank.getJPY_in().equals("0.0000") && !belarusbank.getJPY_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "JPY",
                                Double.parseDouble(belarusbank.getJPY_in()),
                                Double.parseDouble(belarusbank.getJPY_out())));
                    }
                    if (belarusbank.getCNY_in() != null && belarusbank.getCNY_out() != null
                            && !belarusbank.getCNY_in().equals("0.0000") && !belarusbank.getCNY_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "CNY",
                                Double.parseDouble(belarusbank.getCNY_in()),
                                Double.parseDouble(belarusbank.getCNY_out())));
                    }
                    if (belarusbank.getCZK_in() != null && belarusbank.getCZK_out() != null
                            && !belarusbank.getCZK_in().equals("0.0000") && !belarusbank.getCZK_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "CZK",
                                Double.parseDouble(belarusbank.getCZK_in()),
                                Double.parseDouble(belarusbank.getCZK_out())));
                    }
                    if (belarusbank.getNOK_in() != null && belarusbank.getNOK_out() != null
                            && !belarusbank.getNOK_in().equals("0.0000") && !belarusbank.getNOK_out().equals("0.0000")) {
                        currencyList.add(new Currency("BYN", "NOK",
                                Double.parseDouble(belarusbank.getNOK_in()),
                                Double.parseDouble(belarusbank.getNOK_out())));
                    }
                    departmentList.add(new Department(belarusbank.getFilial_text(), new ArrayList<>(currencyList)));
                    currencyList.clear();
                }
                logger.info("Belarusbank rates updated in DB");
                Bank belarusBank = new Bank("Belarusbank", city, departmentList);
                if (departmentList.size() > 0) {
                    daoRates.saveBank(belarusBank);
                }
            } catch (IOException e) {
                System.out.println("--Error with Belarusbank--");
                logger.error("IO exc", e);
            } catch (Exception e) {
                System.out.println("--Error with Belarusbank--");
                logger.error("Other exc", e);
            }
        }
    }
}
