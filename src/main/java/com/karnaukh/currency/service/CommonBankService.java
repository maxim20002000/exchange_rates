package com.karnaukh.currency.service;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;
import com.karnaukh.currency.service.bank.AbsolutbankService;
import com.karnaukh.currency.service.bank.AlfabankService;
import com.karnaukh.currency.service.bank.BelarusbankService;
import com.karnaukh.currency.service.bank.BelgazprombankService;
import com.karnaukh.currency.service.bank.MTBankService;
import com.karnaukh.currency.service.bank.VTBbankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public class CommonBankService implements ICommonBankService {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AbsolutbankService absolutbankService;

	@Autowired
	private AlfabankService alfabankService;

	@Autowired
	private BelarusbankService belarusbankService;

	@Autowired
	private BelgazprombankService belgazprombankService;

	@Autowired
	private MTBankService mtBankService;

	@Autowired
	private VTBbankService vtBbankService;

	@Override
	public void updateCurrency(String city) throws JAXBException, IOException {
		bankRepository.clearBankList();

		absolutbankService.getCurrencyRate(city);
		alfabankService.getCurrencyRate(city);
		belarusbankService.getCurrencyRate(city);
		belgazprombankService.getCurrencyRate(city);
		mtBankService.getCurrencyRate(city);
		vtBbankService.getCurrencyRate(city);
	}

	@Override
	public void printCurrencyRates() {
		List<Bank> bankList = bankRepository.getBankList();
		for (Bank bank : bankList) {
			System.out.println(bank.toString());
			List<Department> departmentList = bank.getDepartmentList();
			for (Department department : departmentList) {
				System.out.println(department.toString());
				List<Currency> currencyList = department.getCurrencyList();
				for (Currency currency : currencyList) {
					System.out.println(currency.toString());
				}
				System.out.println("");
			}
		}
	}
}
