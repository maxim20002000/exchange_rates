package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.absolutBank.BranchType;
import com.karnaukh.currency.bankAPI.absolutBank.BranchesType;
import com.karnaukh.currency.bankAPI.absolutBank.RateType;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AbsolutbankService extends ServiceUtil implements IBankCurrency {

	@Override
	public void getCurrencyRate(String city) throws JAXBException, MalformedURLException {
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
		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Absolutbank", departmentList));
	}
}
