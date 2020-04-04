package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.mtbank.CurrencyType;
import com.karnaukh.currency.bankAPI.mtbank.DepartmentType;
import com.karnaukh.currency.bankAPI.mtbank.ObjectFactory;
import com.karnaukh.currency.bankAPI.mtbank.RatesType;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class MTBankService extends ServiceUtil implements IBankCurrency {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private Logger logger;

	@Override
	public void getCurrencyRate(String city) {
		try {
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
								Double.parseDouble(currencyType.getSale()),
								Double.parseDouble(currencyType.getPurchase()));
						currencyList.add(currency);
					}
					departmentList.add(new Department(departmentType.getLabel(), new ArrayList<>(currencyList)));
					currencyList.clear();
				}

			}
			bankRepository.addToBankList(new Bank("MTBank", departmentList));
		} catch (JAXBException e) {
			System.out.println("--Error with MTbank--");
			logger.error("Jaxb exc", e);
		} catch (MalformedURLException e) {
			System.out.println("--Error with MTbank(check URL)--");
			logger.error("Malf URL exc", e);
		} catch (NumberFormatException e) {
			System.out.println("--Error with MTbank--");
			logger.error("Num format exc", e);
		} catch (Exception e) {
			System.out.println("--Error with MTbank--");
			logger.error("Other exc", e);
		}
	}
}
