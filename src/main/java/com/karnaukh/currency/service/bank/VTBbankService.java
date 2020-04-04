package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.vtbBank.RateType;
import com.karnaukh.currency.bankAPI.vtbBank.RatesType;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class VTBbankService extends ServiceUtil implements IBankCurrency {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private Logger logger;

	@Override
	public void getCurrencyRate(String city) throws JAXBException, IOException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.vtbBank.ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			URL url = new URL("https://www.vtb-bank.by/sites/default/files/rates.xml");
			JAXBElement<RatesType> unmarshalledObject =
					(JAXBElement<RatesType>) unmarshaller.unmarshal(url);

			RatesType ratesType = unmarshalledObject.getValue();
			List<RateType> rateTypeList = ratesType.getMain().getRate();
			List<Department> departmentList = new ArrayList<>();
			List<Currency> currencyList = new ArrayList<>();
			for (RateType rateType : rateTypeList) {
				Currency currency = new Currency("BYN",
						rateType.getCode().toUpperCase(),
						Double.parseDouble(rateType.getBuy()),
						Double.parseDouble(rateType.getSell()));
				currencyList.add(currency);
			}
			departmentList.add(new Department("ALL departments", currencyList));
			bankRepository.addToBankList(new Bank("VTBbank", departmentList));
		} catch (JAXBException e) {
			System.out.println("--Error with VTBbank--");
			logger.error("Jaxb exc", e);
		} catch (MalformedURLException e) {
			System.out.println("--Error with VTBbank(check URL)--");
			logger.error("Malf URL exc", e);
		} catch (NumberFormatException e) {
			System.out.println("--Error with VTBbank--");
			logger.error("Num format exc", e);
		} catch (Exception e) {
			System.out.println("--Error with VTBbank--");
			logger.error("Other exc", e);
		}
	}
}
