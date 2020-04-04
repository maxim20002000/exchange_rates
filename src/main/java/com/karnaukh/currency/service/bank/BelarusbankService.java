package com.karnaukh.currency.service.bank;

import com.google.gson.Gson;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.entity.secondary.Belarusbank;
import com.karnaukh.currency.repository.BankRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class BelarusbankService extends ServiceUtil implements IBankCurrency {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private Logger logger;

	@Override
	public void getCurrencyRate(String city) {
		try {
			URL url = new URL("https://belarusbank.by/api/kursExchange?city=" + city);
			String jsonText = getJsonTextFromURI(url);

			Gson gson = new Gson();
			Belarusbank[] belarusbankList = gson.fromJson(jsonText, Belarusbank[].class);
			List<Department> departmentList = new ArrayList<>();
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
				departmentList.add(new Department(belarusbank.getFilial_text(), new ArrayList<>(currencyList)));
				currencyList.clear();
			}
			bankRepository.addToBankList(new Bank("Belarusbank", departmentList));
		} catch (IOException e) {
			System.out.println("--Error with Belarusbank--");
			logger.error("IO exc", e);
		} catch (Exception e) {
			System.out.println("--Error with Belarusbank--");
			logger.error("Other exc", e);
		}
	}
}
