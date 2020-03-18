package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.belgazpromBank.BranchesType;
import com.karnaukh.currency.bankAPI.belgazpromBank.RangeType;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BelgazprombankService extends ServiceUtil implements IBankCurrency {

	@Override
	public void getCurrencyRate(String city) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.belgazpromBank.ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		URL url = new URL("https://belgazprombank.by/upload/courses.xml");
		JAXBElement<BranchesType> unmarshalledObject =
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
						Currency currency = new Currency("BYN",
								rate.getCurrency().toUpperCase(),
								Double.parseDouble(rangeType.getBuy()),
								Double.parseDouble(rangeType.getSell()));
						currencyList.add(currency);
					}
				}
			}
			departmentList.add(new Department(branch.getName(), new ArrayList<>(currencyList)));
			currencyList.clear();
		}

		BankRepository.getBankRepositoryInstance().addToBankList(new Bank("Belgazprombank", departmentList));
	}
}
