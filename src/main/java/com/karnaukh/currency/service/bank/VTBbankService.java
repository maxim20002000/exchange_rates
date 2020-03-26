package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.vtbBank.RateType;
import com.karnaukh.currency.bankAPI.vtbBank.RatesType;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class VTBbankService extends ServiceUtil implements IBankCurrency {

	@Autowired
	private BankRepository bankRepository;

	@Override
	public void getCurrencyRate(String city) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.vtbBank.ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		URL url = new URL("https://www.vtb-bank.by/sites/default/files/rates.xml");
		JAXBElement<RatesType> unmarshalledObject =
				(JAXBElement<com.karnaukh.currency.bankAPI.vtbBank.RatesType>) unmarshaller.unmarshal(url);

		com.karnaukh.currency.bankAPI.vtbBank.RatesType ratesType = unmarshalledObject.getValue();
		List<RateType> rateTypeList = ratesType.getMain().getRate();
		List<Department> departmentList = new ArrayList<>();
		List<Currency> currencyList = new ArrayList<>();
		for (com.karnaukh.currency.bankAPI.vtbBank.RateType rateType : rateTypeList) {
			Currency currency = new Currency("BYN",
					rateType.getCode().toUpperCase(),
					Double.parseDouble(rateType.getBuy()),
					Double.parseDouble(rateType.getSell()));
			currencyList.add(currency);
		}
		departmentList.add(new Department("ALL departments", currencyList));
		bankRepository.addToBankList(new Bank("VTBbank", departmentList));
	}
}
